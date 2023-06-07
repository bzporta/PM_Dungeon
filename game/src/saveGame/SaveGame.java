package saveGame;

import ecs.entities.Entity;
import ecs.entities.Hero;
import ecs.entities.monster.*;
import ecs.entities.trap.Trap;
import ecs.entities.trap.TrapDmg;
import ecs.entities.trap.TrapDmgCreator;
import ecs.entities.trap.TrapTeleport;
import ecs.quests.Quest;
import graphic.hud.QuestMenu;
import level.elements.astar.TileConnection;
import level.elements.tile.ExitTile;
import level.elements.tile.FloorTile;
import level.elements.tile.Tile;
import starter.Game;

import java.io.*;

public class SaveGame {

    public static void writeObject(SaveData saveData, String filename){
    try (FileOutputStream fos = new FileOutputStream("game/src/saveGame/savedGames/" + filename);
        ObjectOutputStream oos = new ObjectOutputStream(fos)) {
      System.out.println("MOIN");
        oos.writeObject(saveData);
        oos.close();
    } catch (IOException ex){
            // Logger
        }
    }

    public static void readObject(String filename){
        try (FileInputStream fis = new FileInputStream("game/src/saveGame/savedGames/" + filename);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            SaveData data = (SaveData) ois.readObject();

            Game.getLevelAPI().setCurrentLevel(data.getCurrentLevel());
            Game.currentLevel = data.getCurrentLevel();

            for (FloorTile fl : Game.currentLevel.getFloorTiles()) {
                for (TileConnection tlc: fl.getSerializedConnections()) {
                    fl.restoreConnection(tlc);
                }
            }

            for (TileConnection tlc : Game.currentLevel.getExitTiles().get(0).getSerializedConnections()) {
                Game.currentLevel.getExitTiles().get(0).restoreConnection(tlc);
            }


            Game.setQuestList(data.getQuestList());
            for (Quest qu : Game.getQuestList()) {
                if (qu.isActive()) qu.setScreenText();
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
        } catch (IOException | ClassNotFoundException ex){
            ex.printStackTrace();
            // Logger
        }
    }

}
