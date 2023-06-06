package saveGame;

import ecs.components.skill.Skill;
import ecs.entities.Entity;
import ecs.entities.Hero;
import ecs.quests.Quest;
import level.elements.ILevel;
import starter.Game;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class SaveData implements Serializable {

  private Set<String> entities = new HashSet<>();
    public int levelCounter;
    private int healthpoints;
    private long herolevel;
    private long heroxp;
    public Set<String> getEntities() {
        return entities;
    }

    public void setEntities() {
        for (Entity entity : Game.getEntities()) {
            this.entities.add(entity.getClass().getSimpleName());
      System.out.println(entity.getClass().getSimpleName());
        }
    }

    public int getLevelCounter() {
        return levelCounter;
    }

    public void setLevelCounter(int levelCounter) {
        this.levelCounter = levelCounter;
    }

    public int getHealthpoints() {
        return healthpoints;
    }

    public void setHealthpoints(int healthpoints) {
        this.healthpoints = healthpoints;
    }

    public long getHerolevel() {
        return herolevel;
    }

    public void setHerolevel(long herolevel) {
        this.herolevel = herolevel;
    }

    public long getHeroxp() {
        return heroxp;
    }

    public void setHeroxp(long heroxp) {
        this.heroxp = heroxp;
    }
}
