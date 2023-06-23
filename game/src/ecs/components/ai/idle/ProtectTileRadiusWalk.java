package ecs.components.ai.idle;

import static ecs.components.ai.AITools.getRandomAccessibleTileCoordinateInRange;

import com.badlogic.gdx.ai.pfa.GraphPath;
import ecs.components.PositionComponent;
import ecs.components.ai.AITools;
import ecs.entities.Entity;
import level.elements.tile.Tile;
import tools.Constants;
import tools.Point;

/** This class is used to make an entity walk around a tile in a given radius. */
public class ProtectTileRadiusWalk implements IIdleAI {

    protected Tile tile;
    protected float radius;
    protected transient GraphPath<Tile> path;
    protected Point center;
    protected Point currentPosition;
    protected Point newEndTile;
    protected final int breakTime;
    protected int currentBreak = 0;

    /**
     * Konstruktor
     *
     * @param tile the tile to protect
     * @param breakTimeInSeconds the time to wait between two movements
     */
    public ProtectTileRadiusWalk(Tile tile, int breakTimeInSeconds) {
        this.tile = tile;
        this.breakTime = breakTimeInSeconds * Constants.FRAME_RATE;
        radius = 2;
    }

    /**
     * Konstruktor
     *
     * @param tile the tile to protect
     * @param radius the radius to walk around the tile
     * @param breakTimeInSeconds the time to wait between two movements
     */
    public ProtectTileRadiusWalk(Tile tile, float radius, int breakTimeInSeconds) {
        this.tile = tile;
        this.breakTime = breakTimeInSeconds * Constants.FRAME_RATE;
        this.radius = radius;
    }

    /**
     * This method is called every frame to make the entity walk around the tile.
     *
     * @param entity associated entity
     */
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
                System.out.println(path);
                idle(entity);
            }
            currentBreak++;

        } else AITools.move(entity, path);
    }
}
