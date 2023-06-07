package saveGame;

import ecs.entities.Entity;
import ecs.quests.Quest;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import level.elements.ILevel;
import level.elements.tile.FloorTile;

/** This class is responsible for saving and loading the game. */
public class SaveData implements Serializable {

    private List<FloorTile> floorTileList;
    private Set<Entity> entities;
    private ILevel currentLevel;
    private Set<Quest> questList;

    private int levelCounter;
    private int spawnRate;
    private int dmgBuff;
    private int hpBuff;

    /** Returns a set of all entities in the game.
     * @return set of all entities in the game
     */
    public Set<Entity> getEntities() {
        return entities;
    }

    /** Sets the set of all entities in the game.
     * @param entities set of all entities in the game
     */
    public void setEntities(Set<Entity> entities) {
        this.entities = entities;
    }

    /** Returns the current level.
     * @return current level
     */
    public ILevel getCurrentLevel() {
        return currentLevel;
    }

    /** Sets the current level.
     * @param currentLevel current level
     */
    public void setCurrentLevel(ILevel currentLevel) {
        this.currentLevel = currentLevel;
        this.floorTileList = currentLevel.getFloorTiles();
    }

    /** Sets the list of all quests in the game.
     * @param questList list of all quests in the game
     */
    public void setQuestList(Set<Quest> questList) {
        this.questList = questList;
    }

    /** Returns the list of all quests in the game.
     * @return list of all quests in the game
     */
    public Set<Quest> getQuestList() {
        return questList;
    }

    /** Returns the level counter.
     * @return level counter
     */
    public int getLevelCounter() {
        return levelCounter;
    }

    /** Sets the level counter.
     * @param levelCounter level counter
     */
    public void setLevelCounter(int levelCounter) {
        this.levelCounter = levelCounter;
    }

    /** Returns the spawn rate.
     * @return spawn rate
     */
    public int getSpawnRate() {
        return spawnRate;
    }

    /** Sets the spawn rate.
     * @param spawnRate spawn rate
     */
    public void setSpawnRate(int spawnRate) {
        this.spawnRate = spawnRate;
    }

    /** Returns the dmg buff.
     * @return dmg buff
     */
    public int getDmgBuff() {
        return dmgBuff;
    }

    /** Sets the dmg buff.
     * @param dmgBuff dmg buff
     */
    public void setDmgBuff(int dmgBuff) {
        this.dmgBuff = dmgBuff;
    }

    /** Returns the hp buff.
     * @return hp buff
     */
    public int getHpBuff() {
        return hpBuff;
    }

    /** Sets the hp buff.
     * @param hpBuff hp buff
     */
    public void setHpBuff(int hpBuff) {
        this.hpBuff = hpBuff;
    }
}
