package level.room;

import java.io.Serializable;
import java.util.LinkedHashSet;
import level.elements.tile.DoorTile;
import level.elements.tile.Tile;
import level.levelgraph.LevelNode;

public interface IRoom extends Serializable {
    /**
     * @return all DoorTiles in this room
     */
    LinkedHashSet<DoorTile> getDoors();

    Tile[][] getLayout();

    LevelNode getLevelNode();
}
