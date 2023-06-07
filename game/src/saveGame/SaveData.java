package saveGame;

import ecs.entities.Entity;
import ecs.quests.Quest;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import level.elements.ILevel;
import level.elements.tile.FloorTile;

public class SaveData implements Serializable {

    private List<FloorTile> floorTileList;
    private Set<Entity> entities;
    private ILevel currentLevel;
    private Set<Quest> questList;

    private int levelCounter;
    private int spawnRate;
    private int dmgBuff;
    private int hpBuff;

    public Set<Entity> getEntities() {
        return entities;
    }

    public void setEntities(Set<Entity> entities) {
        this.entities = entities;
    }

    public ILevel getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(ILevel currentLevel) {
        this.currentLevel = currentLevel;
        this.floorTileList = currentLevel.getFloorTiles();
    }

    public void setQuestList(Set<Quest> questList) {
        this.questList = questList;
    }

    public Set<Quest> getQuestList() {
        return questList;
    }

    public int getLevelCounter() {
        return levelCounter;
    }

    public void setLevelCounter(int levelCounter) {
        this.levelCounter = levelCounter;
    }

    public int getSpawnRate() {
        return spawnRate;
    }

    public void setSpawnRate(int spawnRate) {
        this.spawnRate = spawnRate;
    }

    public int getDmgBuff() {
        return dmgBuff;
    }

    public void setDmgBuff(int dmgBuff) {
        this.dmgBuff = dmgBuff;
    }

    public int getHpBuff() {
        return hpBuff;
    }

    public void setHpBuff(int hpBuff) {
        this.hpBuff = hpBuff;
    }
}
