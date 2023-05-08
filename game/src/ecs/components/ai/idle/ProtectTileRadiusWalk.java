package ecs.components.ai.idle;
import static ecs.components.ai.AITools.getRandomAccessibleTileCoordinateInRange;

import com.badlogic.gdx.ai.pfa.GraphPath;
import ecs.components.PositionComponent;
import ecs.components.ai.AITools;
import ecs.entities.Entity;
import level.elements.tile.Tile;
import starter.Game;
import tools.Constants;
import tools.Point;


import static ecs.components.ai.AITools.getRandomAccessibleTileCoordinateInRange;

public class ProtectTileRadiusWalk implements IIdleAI {

    private Tile tile;
    private float radius;
    private GraphPath<Tile> path;
    private Point center;
    private Point currentPosition;
    private Point newEndTile;
    private final int breakTime;
    private int currentBreak = 0;

    public ProtectTileRadiusWalk(Tile tile, int breakTimeInSeconds) {
        this.tile = tile;
        this.breakTime = breakTimeInSeconds * Constants.FRAME_RATE;
        radius = 2;
    }

    public ProtectTileRadiusWalk(Tile tile, float radius, int breakTimeInSeconds) {
        this.tile = tile;
        this.breakTime = breakTimeInSeconds * Constants.FRAME_RATE;
        this.radius = radius;
    }

    @Override
    public void idle(Entity entity) {
        if (path == null || AITools.pathFinishedOrLeft(entity, path)) {
            if (center == null) {
                center = tile.getCoordinateAsPoint();
            }

            if (currentBreak >= breakTime) {
                currentBreak = 0;
                PositionComponent pc2 =
                    (PositionComponent)
                        entity.getComponent(PositionComponent.class).orElseThrow();
                currentPosition = pc2.getPosition();
                newEndTile = getRandomAccessibleTileCoordinateInRange(center, radius).toPoint();
                path = AITools.calculatePath(currentPosition, newEndTile);
                idle(entity);
            }
            currentBreak++;

        } else AITools.move(entity, path);

    }



}
