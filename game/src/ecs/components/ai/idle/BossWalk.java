package ecs.components.ai.idle;

import com.badlogic.gdx.ai.pfa.GraphPath;
import ecs.components.ai.AITools;
import ecs.entities.Entity;
import level.elements.tile.Tile;
import starter.Game;
import tools.Point;

public class BossWalk implements IIdleAI {

    private Tile tile;
    private float radius;
    private transient GraphPath<Tile> path;
    private Point center;
    private Point currentPosition;
    private Point newEndTile;
    private final int breakTime;
    private int currentBreak = 0;

    /**
     * BossWalk constructor
     *
     * @param breakTime time to wait before changing direction
     */
    public BossWalk(int breakTime) {
        this.breakTime = breakTime;
    }

    /**
     * Implements the idle behavior of an AI controlled entity
     *
     * @param entity associated entity
     */
    @Override
    public void idle(Entity entity) {
        if (path == null || AITools.pathFinishedOrLeft(entity, path)) {
            if (center == null) {
                center = Game.currentLevel.getEndTile().getCoordinateAsPoint();
            }

            if (currentBreak >= breakTime) {
                currentBreak = 0;
                newEndTile = Game.currentLevel.getStartTile().getCoordinateAsPoint();
                path = AITools.calculatePath(center, newEndTile);
                idle(entity);
            }
            currentBreak++;

            if (currentBreak >= breakTime) {
                currentBreak = 0;
                newEndTile = Game.currentLevel.getStartTile().getCoordinateAsPoint();
                path = AITools.calculatePath(newEndTile, center);
                idle(entity);
            }
            currentBreak++;

        } else AITools.move(entity, path);
    }
}
