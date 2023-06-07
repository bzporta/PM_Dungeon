package level.elements.astar;

import com.badlogic.gdx.ai.pfa.Heuristic;
import com.badlogic.gdx.math.Vector2;
import level.elements.tile.Tile;

import java.io.Serializable;

/**
 * @author Marti Stuwe
 */
public class TileHeuristic implements Heuristic<Tile>, Serializable {

    /**
     * Heuristic used by the pathfinding algorithm
     *
     * @param start From
     * @param goal To
     * @return Distance between from and to tile
     */
    @Override
    public float estimate(Tile start, Tile goal) {
        return Vector2.dst2(
                start.getCoordinate().x,
                start.getCoordinate().y,
                goal.getCoordinate().x,
                goal.getCoordinate().y);
    }
}
