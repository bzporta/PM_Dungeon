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

            for (String entity : data.getEntities()){
                if (entity.equals("Andromalius") ) Game.getEntities().add(new Andromalius());
                if (entity.equals("BossMonster") ) Game.getEntities().add(new BossMonster());
                if (entity.equals("DarkHeart") ) Game.getEntities().add(new DarkHeart());
                if (entity.equals("Imp")) Game.getEntities().add(new Imp());
                if (entity.equals("TrapTeleport")) Game.getTrapTeleportCreator().creator(1, Game.getEntities(),Game.currentLevel);
                if (entity.equals("TrapDmg")) Game.getTrapDmgCreator().creator(1, Game.getEntities(),Game.currentLevel);
            }
            Game.levelCounter = data.getLevelCounter();

            Hero hero = (Hero) Game.getHero().orElseThrow();
            hero.getXP().setCurrentLevel(data.getHerolevel());
            long currentXP = hero.getXP().getCurrentXP() + (hero.getXP().getXPToNextLevel() - data.getHeroxp());
            hero.getXP().setCurrentXP(data.getHeroxp());
            hero.getHC().setCurrentHealthpoints(data.getHealthpoints() + 100);



      System.out.println("GELADEN");
        } catch (IOException | ClassNotFoundException ex){
      System.out.println("EXCEPTION");
      ex.printStackTrace();
            // Logger
        }
    }

}
