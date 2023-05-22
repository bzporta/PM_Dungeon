package ecs.entities.trap;

import ecs.entities.Entity;
import java.util.Set;
import level.elements.ILevel;

/** An interface for Trap Factories */
public interface TrapFactory {

    /**
     * Creates a Trap
     *
     * @param anzahl number of traps to be created
     * @param entity set of entities
     * @param currentLevel current level
     */
    void creator(int anzahl, Set<Entity> entity, ILevel currentLevel);
}
