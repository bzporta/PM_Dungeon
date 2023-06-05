package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.AnimationComponent;
import ecs.components.InteractionComponent;
import ecs.components.PositionComponent;
import ecs.damage.Damage;
import ecs.damage.DamageType;
import java.util.Random;

import ecs.quests.GraveQuest;
import ecs.quests.KillQuest;
import level.elements.ILevel;
import level.elements.tile.Tile;
import starter.Game;
import tools.Point;

public class Grave extends Entity {

    private PositionComponent pc;
    private String pathToSkin = "dungeon/grave/grave.png";
    private Hero hero;

    /**
     * Constructor for a grave.
     *
     * @param hero the hero that uses the grave
     */
    public Grave(Hero hero) {
        new InteractionComponent(this, 1f, false, this::event);
        pc = new PositionComponent(this);
        setupAnimation(pathToSkin);
        this.hero = hero;
    }

    private void setupAnimation(String path) {
        new AnimationComponent(this, AnimationBuilder.buildAnimation(path));
    }

    /**
     * Sets the grave on a random floor tile.
     *
     * @param currentlevel the current level
     */
    public void setGrave(ILevel currentlevel) {
        Tile tile;
        do {
            tile = currentlevel.getRandomFloorTile();
            pc.setPosition(tile.getCoordinateAsPoint());
        } while (Game.positionList.contains(tile));
        setupAnimation(pathToSkin);
        Game.positionList.add(tile);
    }

    private void event(Entity entity) {
        Random random = new Random();
        if (random.nextDouble() <= 0.7) {
            hero.getHC().setCurrentHealthpoints(hero.getHC().getCurrentHealthpoints() + 20);
            setupAnimation("dungeon/grave/graveheal.png");
        } else {
            hero.getHC().receiveHit(new Damage(20, DamageType.MAGIC, null));
            setupAnimation("dungeon/grave/graveused.png");
        }
        GraveQuest graveQuest = Game.getGraveQuest();
        if (starter.Game.getGraveQuest().isActive()) {
                graveQuest.countActivatedGraves();
            }
    }

    /**
     * Returns the position of the grave.
     *
     * @return the position of the grave
     */
    public Point getPosition() {
        return pc.getPosition();
    }
}
