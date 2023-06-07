package saveGame;

import ecs.entities.Entity;
import ecs.entities.Hero;
import ecs.entities.monster.*;
import ecs.quests.GraveQuest;
import ecs.quests.KillQuest;
import ecs.quests.Quest;
import java.io.*;
import java.util.logging.Logger;
import level.elements.astar.TileConnection;
import level.elements.tile.FloorTile;
import starter.Game;

/** This class is responsible for saving and loading the game. */
public class SaveGame {

    private static Logger logger = Logger.getLogger(SaveGame.class.getName());
    private static boolean isLoaded = false;
    /**
     * This method saves the current state of the game.
     *
     * @param saveData The SaveData object that contains all the data that needs to be saved.
     * @param filename The name of the file to save the game to.
     *     <p>Writes a SaveData object to a file.
     */
    public static void writeObject(SaveData saveData, String filename) {
        try (FileOutputStream fos =
                        new FileOutputStream("game/src/saveGame/savedGames/" + filename);
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(saveData);
            logger.info("Game saved successfully");
        } catch (IOException ex) {
            logger.warning("SaveGame.writeObject() failed - Could not save the game");
        }
    }

    /**
     * This method loads a saved game.
     *
     * @param filename The name of the file to load the game from.
     *     <p>Reads a SaveData object from a file and restores the game state by adding all
     *     important attributes to the game.
     */
    public static void readObject(String filename) {
        try (FileInputStream fis = new FileInputStream("game/src/saveGame/savedGames/" + filename);
                ObjectInputStream ois = new ObjectInputStream(fis)) {
            SaveData data = (SaveData) ois.readObject();
            // Lade das gespeicherte Level in das Game.
            Game.getLevelAPI().setCurrentLevel(data.getCurrentLevel());
            Game.currentLevel = data.getCurrentLevel();
            // Entferne alle Quest, Questtexte und Entities vom derzeitigen Level.
            for (Quest qu : Game.getQuestList()) {
                if (qu.isActive()) {
                    qu.getScreenText().remove();
                    Game.getQuestMenu().decreaseQuestcounter();
                }
            }
            Game.getEntities().clear();
            Game.getQuestList().clear();

            // Die Liste der Connections von den Tiles wurde nicht gespeichert, deswegen müssen diese wiederhergestellt werden.
            for (FloorTile fl : Game.currentLevel.getFloorTiles()) {
                for (TileConnection tlc : fl.getSerializedConnections()) {
                    fl.restoreConnection(tlc);
                }
            }

            for (TileConnection tlc :
                    Game.currentLevel.getExitTiles().get(0).getSerializedConnections()) {
                Game.currentLevel.getExitTiles().get(0).restoreConnection(tlc);
            }
            // Die QuestListe wird wiederhergestellt.
            Game.setQuestList(data.getQuestList());
            for (Quest qu : Game.getQuestList()) {
                if (qu instanceof GraveQuest) {
                    Game.setGraveQuest((GraveQuest) qu);
                } else if (qu instanceof KillQuest) {
                    Game.setKillQuest((KillQuest) qu);
                }
                if (qu.isActive()) {
                    qu.setScreenText();
                }
            }
            // Wiederherstellen der Entitylist.
            for (Entity entity : data.getEntities()) {
                if (entity instanceof Monster) ((Monster) entity).setupAiComponent();
                Game.getEntities().add(entity);
                if (entity instanceof Hero) Game.setHero(entity);
            }
            // Zurücksetzen der Buffs, Spawnrate und des Levelcounters.
            Game.levelCounter = data.getLevelCounter();
            Game.setHpBuff(data.getHpBuff());
            Game.setDmgBuff(data.getDmgBuff());
            Game.setSpawnRate(data.getSpawnRate());
            logger.info("Game loaded successfully");
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
            logger.warning("SaveGame.readObject() failed - Could not load the game");
        }
    }

    /**
     * Returns whether the game is loaded on the current level or not.
     *
     * @return isLoaded
     */
    public static boolean isLoaded() {
        return isLoaded;
    }

    /**
     * Sets whether the game is loaded on current level or not.
     *
     * @param isLoaded
     */
    public static void setIsLoaded(boolean isLoaded) {
        SaveGame.isLoaded = isLoaded;
    }
}
