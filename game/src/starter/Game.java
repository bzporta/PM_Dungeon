package starter;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static logging.LoggerConfig.initBaseLogger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import configuration.Configuration;
import configuration.KeyboardConfig;
import controller.AbstractController;
import controller.SystemController;
import ecs.components.MissingComponentException;
import ecs.components.PositionComponent;
import ecs.entities.*;
import ecs.entities.monster.*;
import ecs.entities.trap.*;
import ecs.quests.GraveQuest;
import ecs.quests.KillQuest;
import ecs.quests.Quest;
import ecs.systems.*;
import graphic.DungeonCamera;
import graphic.Painter;
import graphic.hud.*;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;
import level.IOnLevelLoader;
import level.LevelAPI;
import level.elements.ILevel;
import level.elements.tile.Tile;
import level.generator.IGenerator;
import level.generator.postGeneration.WallGenerator;
import level.generator.randomwalk.RandomWalkGenerator;
import level.tools.LevelSize;
import saveGame.SaveData;
import saveGame.SaveGame;
import tools.Constants;
import tools.Point;

/** The heart of the framework. From here all strings are pulled. */
public class Game extends ScreenAdapter implements IOnLevelLoader {

    private final LevelSize LEVELSIZE = LevelSize.SMALL;

    /**
     * The batch is necessary to draw ALL the stuff. Every object that uses draw need to know the
     * batch.
     */
    protected SpriteBatch batch;

    private static Game game;
    /** Contains all Controller of the Dungeon */
    protected List<AbstractController<?>> controller;

    public static DungeonCamera camera;
    /** Draws objects */
    protected Painter painter;

    private boolean isSkillMenuOpen = false;
    private boolean isGameOverMenueOpen = false;

    /** Generates the level */
    protected static LevelAPI levelAPI;
    /** Generates the level */
    protected IGenerator generator;

    public static InputMultiplexer inputMultiplexer = new InputMultiplexer();

    private static TrapDmgCreator trapDmgCreator;
    private static TrapTeleportCreator trapTeleportCreator;
    private boolean doSetup = true;
    private static boolean paused = false;
    private static boolean toggleSkillMenue = false;
    private static boolean toggleGameOverMenue = false;

    /** All entities that are currently active in the dungeon */
    private static final Set<Entity> entities = new HashSet<>();

    private static Entity hero;
    private Ghost ghost;
    private Grave grave;
    private static Monster imp;
    private static Monster darkheart;
    private static Monster andromalius;
    private static Monster boss;
    private static KillQuest killQuest;
    private static GraveQuest graveQuest;

    private static Set<Quest> questList = new HashSet<>();

    /** All entities to be removed from the dungeon in the next frame */
    private static final Set<Entity> entitiesToRemove = new HashSet<>();
    /** All entities to be added from the dungeon in the next frame */
    private static final Set<Entity> entitiesToAdd = new HashSet<>();

    /** List of all Systems in the ECS */
    public static SystemController systems;

    public static ILevel currentLevel;
    private static PauseMenu<Actor> pauseMenu;

    private static GameOver<Actor> gameOverMenu;
    private static DialogMenu<Actor> dialogMenu;
    private static SkillMenu<Actor> skillMenu;
    private static QuestMenu<Actor> questMenu;
    private transient Logger gameLogger;

    /** List of Tile positions for entities */
    public static ArrayList<Tile> positionList = new ArrayList<>();

    public static int levelCounter;
    private static int spawnRate;
    private static int dmgBuff;
    private static int hpBuff;

    /**
     * Constructor for the game. Initializes the batch and the camera.
     *
     * @throws IOException if the configuration file could not be loaded
     */
    public static void main(String[] args) {
        // start the game
        try {
            Configuration.loadAndGetConfiguration("dungeon_config.json", KeyboardConfig.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        game = new Game();
        DesktopLauncher.run(game);
    }

    /**
     * Main game loop. Redraws the dungeon and calls the own implementation (beginFrame, endFrame
     * and onLevelLoad).
     *
     * @param delta Time since last loop.
     */
    @Override
    public void render(float delta) {
        if (doSetup) setup();
        batch.setProjectionMatrix(camera.combined);
        frame();
        clearScreen();
        levelAPI.update();
        controller.forEach(AbstractController::update);
        camera.update();
    }

    /** Called once at the beginning of the game. */
    public void setup() {
        doSetup = false;
        controller = new ArrayList<>();
        setupCameras();
        painter = new Painter(batch, camera);
        generator = new RandomWalkGenerator();
        levelAPI = new LevelAPI(batch, painter, generator, this);
        initBaseLogger();
        gameLogger = Logger.getLogger(this.getClass().getName());
        systems = new SystemController();
        controller.add(systems);
        pauseMenu = new PauseMenu<>();
        controller.add(pauseMenu);
        gameOverMenu = new GameOver<>();
        skillMenu = new SkillMenu<>();
        dialogMenu = new DialogMenu<>();
        controller.add(skillMenu);
        controller.add(gameOverMenu);
        controller.add(dialogMenu);
        questMenu = new QuestMenu<>();
        controller.add(questMenu);
        questMenu.showMenu();
        paused = false;
        toggleGameOverMenue = false;
        toggleSkillMenue = false;
        isSkillMenuOpen = false;
        isGameOverMenueOpen = false;
        trapDmgCreator = new TrapDmgCreator();
        trapTeleportCreator = new TrapTeleportCreator();
        hero = new Hero();
        hpBuff = 0;
        dmgBuff = 0;
        levelCounter = 0;
        spawnRate = 1;
        levelAPI = new LevelAPI(batch, painter, new WallGenerator(new RandomWalkGenerator()), this);
        levelAPI.loadLevel(LEVELSIZE);
        Gdx.input.setInputProcessor(inputMultiplexer);
        killQuest = new KillQuest();
        graveQuest = new GraveQuest();
        questList.add(graveQuest);
        questList.add(killQuest);
        createSystems();
    }

    /** Called at the beginning of each frame. Before the controllers call <code>update</code>. */
    protected void frame() {
        setCameraFocus();
        manageEntitiesSets();
        getHero().ifPresent(this::loadNextLevelIfEntityIsOnEndTile);
        if (!isSkillMenuOpen && !isGameOverMenueOpen && !getDialogMenu().getIsOpen()) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.P)) togglePause();
        }
        if (isSkillMenuOpen) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.H) && Hero.upgradeHealSkill <= 5) {
                Hero hero = (Hero) Game.getHero().get();
                hero.setHealSkill();
                toggleSkillMenu();
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.J) && Hero.upgradeIceBallSkill <= 5) {
                Hero hero = (Hero) Game.getHero().get();
                hero.setIceBallSkill();
                toggleSkillMenu();
            }
        }
    }

    @Override
    public void onLevelLoad() {
        currentLevel = levelAPI.getCurrentLevel();
        clearPositionlist();
        entities.clear();
        getHero().ifPresent(this::placeOnLevelStart);
        trapTeleportCreator.creator(1, entities, currentLevel);
        trapDmgCreator.creator(1, entities, currentLevel);
        entities.add(grave = new Grave((Hero) hero));
        grave.setGrave(currentLevel);
        createMonster();
        entities.add(ghost = new Ghost(grave));
        ghost.setSpawn();
        SaveGame.setIsLoaded(false);
        if (((Hero) hero).getXP().getCurrentLevel() < 11) {
            ((Hero) hero).getXP().addXP(20);
        }
    }

    private void manageEntitiesSets() {
        entities.removeAll(entitiesToRemove);
        entities.addAll(entitiesToAdd);
        for (Entity entity : entitiesToRemove) {
            gameLogger.info("Entity '" + entity.getClass().getSimpleName() + "' was deleted.");
        }
        for (Entity entity : entitiesToAdd) {
            gameLogger.info("Entity '" + entity.getClass().getSimpleName() + "' was added.");
        }
        entitiesToRemove.clear();
        entitiesToAdd.clear();
    }

    private void setCameraFocus() {
        if (getHero().isPresent()) {
            PositionComponent pc =
                    (PositionComponent)
                            getHero()
                                    .get()
                                    .getComponent(PositionComponent.class)
                                    .orElseThrow(
                                            () ->
                                                    new MissingComponentException(
                                                            "PositionComponent"));
            camera.setFocusPoint(pc.getPosition());

        } else camera.setFocusPoint(new Point(0, 0));
    }

    private void loadNextLevelIfEntityIsOnEndTile(Entity hero) {
        if (isOnEndTile(hero)) levelAPI.loadLevel(LEVELSIZE);
    }

    private boolean isOnEndTile(Entity entity) {
        PositionComponent pc =
                (PositionComponent)
                        entity.getComponent(PositionComponent.class)
                                .orElseThrow(
                                        () -> new MissingComponentException("PositionComponent"));
        Tile currentTile = currentLevel.getTileAt(pc.getPosition().toCoordinate());
        return currentTile.equals(currentLevel.getEndTile());
    }

    private void placeOnLevelStart(Entity hero) {
        entities.add(hero);
        PositionComponent pc =
                (PositionComponent)
                        hero.getComponent(PositionComponent.class)
                                .orElseThrow(
                                        () -> new MissingComponentException("PositionComponent"));
        pc.setPosition(currentLevel.getStartTile().getCoordinate().toPoint());
    }

    /** Toggle between pause and run */
    public static void togglePause() {
        paused = !paused;
        if (systems != null) {
            systems.forEach(ECS_System::toggleRun);
        }
        if (pauseMenu != null) {
            if (paused) pauseMenu.showMenu();
            else pauseMenu.hideMenu();
        }
    }

    /**
     * Saves the game
     *
     * <p>Sets all important attributes of the game that are needed to load the game later to a
     * SaveData object
     */
    public void saveGame() {
        SaveData save = new SaveData();
        save.setCurrentLevel(currentLevel);
        save.setQuestList(questList);
        save.setEntities(entities);
        save.setLevelCounter(levelCounter);
        save.setHpBuff(hpBuff);
        save.setDmgBuff(dmgBuff);
        save.setSpawnRate(spawnRate);
        SaveGame.writeObject(save, "SavedGame.txt");
    }

    /** Toggles the SkillMenu */
    public void toggleSkillMenu() {
        toggleSkillMenue = !toggleSkillMenue;
        if (systems != null) {
            systems.forEach(ECS_System::toggleRun);
        }

        if (skillMenu != null) {
            if (toggleSkillMenue) {
                isSkillMenuOpen = true;
                skillMenu.showMenu();

            } else {
                isSkillMenuOpen = false;
                skillMenu.hideMenu();
            }
        }
    }

    /** Toggles the GameOverMenu */
    public void toggleGameOver() {
        toggleGameOverMenue = !toggleGameOverMenue;
        if (systems != null) {
            systems.forEach(ECS_System::toggleRun);
        }
        if (gameOverMenu != null) {
            if (toggleGameOverMenue) {
                System.out.println("CreateMenue");
                gameOverMenu.createGameOverMenue();
                gameOverMenu.showMenu();
                isGameOverMenueOpen = true;
            } else {
                System.out.println("RemoveMenue");
                gameOverMenu.removeGameOverMenu();
                gameOverMenu.hideMenu();
                isGameOverMenueOpen = false;
            }
        }
    }

    /**
     * Returns the GameOverMenuObject
     *
     * @return GameOverMenuObject
     */
    public static GameOver getGameOverMenu() {
        return gameOverMenu;
    }

    /**
     * Returns the SkillMenuObject
     *
     * @return SkillMenuObject
     */
    public static SkillMenu getSkillMenu() {
        return skillMenu;
    }

    /**
     * Returns the DialogMenu
     *
     * @return DialogMenu
     */
    public static DialogMenu getDialogMenu() {
        return dialogMenu;
    }

    /**
     * Given entity will be added to the game in the next frame
     *
     * @param entity will be added to the game next frame
     */
    public static void addEntity(Entity entity) {
        entitiesToAdd.add(entity);
    }

    /**
     * Given entity will be removed from the game in the next frame
     *
     * @param entity will be removed from the game next frame
     */
    public static void removeEntity(Entity entity) {
        entitiesToRemove.add(entity);
    }

    /**
     * @return Set with all entities currently in game
     */
    public static Set<Entity> getEntities() {
        return entities;
    }

    /**
     * @return Set with all entities that will be added to the game next frame
     */
    public static Set<Entity> getEntitiesToAdd() {
        return entitiesToAdd;
    }

    /**
     * @return Set with all entities that will be removed from the game next frame
     */
    public static Set<Entity> getEntitiesToRemove() {
        return entitiesToRemove;
    }

    /**
     * @return the player character, can be null if not initialized
     */
    public static Optional<Entity> getHero() {
        return Optional.ofNullable(hero);
    }

    /**
     * Returns the current game-Object
     *
     * @return the current game-Object
     */
    public static Game getGame() {
        return game;
    }
    /**
     * set the reference of the playable character careful: old hero will not be removed from the
     * game
     *
     * @param hero new reference of hero
     */

    /** sets the reference of the playable character */
    public static void setHero(Entity hero) {
        Game.hero = hero;
    }

    public void setSpriteBatch(SpriteBatch batch) {
        this.batch = batch;
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
    }

    private void setupCameras() {
        camera = new DungeonCamera(null, Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
        camera.zoom = Constants.DEFAULT_ZOOM_FACTOR;

        // See also:
        // https://stackoverflow.com/questions/52011592/libgdx-set-ortho-camera
    }

    public static QuestMenu getQuestMenu() {
        return questMenu;
    }

    private void createSystems() {
        new VelocitySystem();
        new DrawSystem(painter);
        new PlayerSystem();
        new AISystem();
        new CollisionSystem();
        new HealthSystem();
        new XPSystem();
        new SkillSystem();
        new ProjectileSystem();
        new QuestSystem();
    }

    /**
     * Restarts the game
     *
     * <p>Used for the "Restart"-Function of the GameOverMenu. Creates a new level and resets all
     * important parameters.
     */
    public static void restartGame() {
        getGame().setup();
    }

    /**
     * Returns the GraveQuest
     *
     * @return GraveQuest
     */
    public static GraveQuest getGraveQuest() {
        return graveQuest;
    }

    /**
     * Sets the GraveQuest
     *
     * @param gravequest GraveQuest
     */
    public static void setGraveQuest(GraveQuest gravequest) {
        graveQuest = gravequest;
    }

    /**
     * Returns the KillQuest
     *
     * @return KillQuest
     */
    public static KillQuest getKillQuest() {
        return killQuest;
    }

    /**
     * Sets the KillQuest
     *
     * @param killquest KillQuest
     */
    public static void setKillQuest(KillQuest killquest) {
        killQuest = killquest;
    }

    /**
     * Returns a Set of all Quests
     *
     * @return Set of all Quests
     */
    public static Set<Quest> getQuestList() {
        return questList;
    }

    private void clearPositionlist() {
        positionList.removeAll(positionList);
    }

    private void createMonster() {
        levelCounter++;
        if (levelCounter == 15) {
            boss = new BossMonster();
            entities.add(boss);
        }
        if (levelCounter % 3 == 0) {
            dmgBuff += 1;
            hpBuff += spawnRate++;
        }
        for (int i = 0; i < spawnRate; i++) {
            Random random = new Random();
            int rnd = random.nextInt(3) + 1;
            if (rnd == 1) {
                imp = new Imp();
                entities.add(imp);
                imp.setHitDmg(imp.getHitDmg() + dmgBuff);
                imp.getHp().setMaximalHealthpoints(100 + hpBuff);
                imp.getHp().setCurrentHealthpoints(100 + hpBuff);
                imp.setPosition(currentLevel.getRandomFloorTile().getCoordinateAsPoint());
            }
            if (rnd == 2) {
                andromalius = new Andromalius();
                andromalius.setHitDmg(andromalius.getHitDmg() + dmgBuff);
                andromalius.getHp().setMaximalHealthpoints(100 + hpBuff);
                andromalius.getHp().setCurrentHealthpoints(100 + hpBuff);
                entities.add(andromalius);
                andromalius.setPosition(currentLevel.getRandomFloorTile().getCoordinateAsPoint());
            }
            if (rnd == 3) {
                darkheart = new DarkHeart();
                darkheart.setHitDmg(darkheart.getHitDmg() + dmgBuff);
                darkheart.getHp().setMaximalHealthpoints(100 + hpBuff);
                darkheart.getHp().setCurrentHealthpoints(100 + hpBuff);
                entities.add(darkheart);
                darkheart.setPosition(currentLevel.getRandomFloorTile().getCoordinateAsPoint());
            }
        }
    }

    /**
     * Returns the TrapDmgCreator
     *
     * @return TrapDmgCreator
     */
    public static TrapDmgCreator getTrapDmgCreator() {
        return trapDmgCreator;
    }

    /**
     * Returns the TrapTeleportCreator
     *
     * @return TrapTeleportCreator
     */
    public static TrapTeleportCreator getTrapTeleportCreator() {
        return trapTeleportCreator;
    }

    /**
     * Returns the LevelAPI
     *
     * @return LevelAPI
     */
    public static LevelAPI getLevelAPI() {
        return levelAPI;
    }

    /**
     * Sets the Questlist of the Level
     *
     * @param questList Questlist
     */
    public static void setQuestList(Set<Quest> questList) {
        Game.questList = questList;
    }

    /**
     * Sets the Spawnrate of the Level
     *
     * @param spawnRate Spawnrate
     */
    public static void setSpawnRate(int spawnRate) {
        Game.spawnRate = spawnRate;
    }

    /**
     * Sets the Damagebuff of the Level
     *
     * @param dmgBuff Damagebuff
     */
    public static void setDmgBuff(int dmgBuff) {
        Game.dmgBuff = dmgBuff;
    }

    /**
     * Sets the HPbuff of the Level
     *
     * @param hpBuff HPbuff
     */
    public static void setHpBuff(int hpBuff) {
        Game.hpBuff = hpBuff;
    }

    public static void setQuestMenu(QuestMenu questMenu) {
        Game.questMenu = questMenu;
    }
}

