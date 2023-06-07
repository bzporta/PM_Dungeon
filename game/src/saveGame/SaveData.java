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

    public Hero getH() {
        return h;
    }

    public void setH(Hero h) {
        this.h = h;
    }

    private Hero h;

    public ILevel getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(ILevel currentLevel) {
        this.currentLevel = currentLevel;
    }

    private ILevel currentLevel;


}
