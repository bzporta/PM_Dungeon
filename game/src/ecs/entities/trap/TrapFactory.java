package ecs.entities.trap;

import ecs.entities.Entity;
import level.elements.ILevel;
import tools.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface TrapFactory {

    ArrayList<Point> positionList = new ArrayList<>();
    void creator(int anzahl, Set<Entity> entity, ILevel currentLevel);
    void clearList();
}
