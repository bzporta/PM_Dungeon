package ecs.entities.trap;

import ecs.entities.Entity;
import level.elements.ILevel;
import level.elements.tile.FloorTile;
import level.elements.tile.Tile;
import tools.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface TrapFactory {

    ArrayList<Tile> positionList = new ArrayList<>();
    void creator(int anzahl, Set<Entity> entity, ILevel currentLevel);
    void clearList();
}
