package ecs.entities.trap;

import ecs.entities.Entity;
import level.elements.ILevel;
import level.elements.tile.FloorTile;
import level.elements.tile.Tile;
import tools.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/** An interface for Trap Factories
 */
public interface TrapFactory {

    /** List of positions
     */
    ArrayList<Tile> positionList = new ArrayList<>();

    /** Creates a Trap
     * @param anzahl number of traps to be created
     * @param entity set of entities
     * @param currentLevel current level
     */
    void creator(int anzahl, Set<Entity> entity, ILevel currentLevel);

    /** Clears the list of positions
     */
    void clearList();
}
