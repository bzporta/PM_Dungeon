package saveGame;

import ecs.entities.Entity;
import ecs.entities.Hero;
import ecs.entities.monster.Andromalius;
import ecs.entities.monster.BossMonster;
import ecs.entities.monster.DarkHeart;
import ecs.entities.monster.Imp;
import ecs.entities.trap.Trap;
import ecs.entities.trap.TrapDmg;
import ecs.entities.trap.TrapDmgCreator;
import ecs.entities.trap.TrapTeleport;
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
            Game.setHero(data.getH());
            Game.getLevelAPI().setCurrentLevel(data.getCurrentLevel());

      System.out.println("GELADEN");
        } catch (IOException | ClassNotFoundException ex){
      System.out.println("EXCEPTION");
      ex.printStackTrace();
            // Logger
        }
    }

}
