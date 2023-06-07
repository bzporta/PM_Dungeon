package saveGame;

import ecs.entities.Entity;
import ecs.entities.Hero;
import ecs.entities.monster.*;
import ecs.quests.GraveQuest;
import ecs.quests.KillQuest;
import ecs.quests.Quest;
import java.io.*;
import level.elements.astar.TileConnection;
import level.elements.tile.FloorTile;
import starter.Game;

/** This class is responsible for saving and loading the game. */
public class SaveGame {

    /**
     * This method saves the current state of the game.
     *
     * @param saveData The SaveData object that contains all the data that needs to be saved.
     * @param filename The name of the file to save the game to.
     *
     * Writes a SaveData object to a file.
     */
    public static void writeObject(SaveData saveData, String filename) {
        try (FileOutputStream fos =
                        new FileOutputStream("game/src/saveGame/savedGames/" + filename);
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(saveData);
            oos.close();
        } catch (IOException ex) {
            // Logger
        }
    }

    /**
     * This method loads a saved game.
     *
     * @param filename The name of the file to load the game from.
     *
     * Reads a SaveData object from a file and restores the game state by adding all important attributes to the game.
     */
    public static void readObject(String filename) {
        try (FileInputStream fis = new FileInputStream("game/src/saveGame/savedGames/" + filename);
                ObjectInputStream ois = new ObjectInputStream(fis)) {
            SaveData data = (SaveData) ois.readObject();

            Game.getLevelAPI().setCurrentLevel(data.getCurrentLevel());
            Game.currentLevel = data.getCurrentLevel();

            for (FloorTile fl : Game.currentLevel.getFloorTiles()) {
                for (TileConnection tlc : fl.getSerializedConnections()) {
                    fl.restoreConnection(tlc);
                }
            }

            for (TileConnection tlc :
                    Game.currentLevel.getExitTiles().get(0).getSerializedConnections()) {
                Game.currentLevel.getExitTiles().get(0).restoreConnection(tlc);
            }

            Game.setQuestList(data.getQuestList());

            for (Quest qu : Game.getQuestList()) {
                System.out.println(qu.getName() + " " + qu.isActive());
                if (qu.isActive()) {
                    if (qu instanceof GraveQuest) {
                        Game.setGraveQuest((GraveQuest) qu);
                    } else if (qu instanceof KillQuest) {
                        Game.setKillQuest((KillQuest) qu);
                    }
                    qu.setScreenText();
                }
            }

            for (Entity entity : data.getEntities()) {
                if (entity instanceof Monster) ((Monster) entity).setupAiComponent();
                Game.getEntities().add(entity);
                if (entity instanceof Hero) Game.setHero(entity);
            }

            Game.levelCounter = data.getLevelCounter();
            Game.setHpBuff(data.getHpBuff());
            Game.setDmgBuff(data.getDmgBuff());
            Game.setSpawnRate(data.getSpawnRate());
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
            // Logger
        }
    }
}
