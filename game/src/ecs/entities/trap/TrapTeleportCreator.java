package ecs.entities.trap;

import ecs.entities.Entity;
import java.util.Set;
import level.elements.ILevel;
import level.elements.tile.Tile;

/** A class that creates Teleportation Traps */
public class TrapTeleportCreator implements TrapFactory {

    Trap falle;

    private boolean createSameTrap = false;
    /**
     * Creates a Teleportation Trap
     *
     * @param anzahl number of traps to be created
     * @param entity set of entities
     * @param currentLevel current level
     */
    @Override
    public void creator(int anzahl, Set<Entity> entity, ILevel currentLevel) {

        /*
        if(anzahl < 0){
            throw new IllegalArgumentException("Trap amount must be positive");
        }
        if(anzahl > currentLevel.getFloorTiles().size()){
            throw new IllegalArgumentException("Trap amount to high");
        }*/
        Tile tile;
        starter.Game.positionList.add(currentLevel.getStartTile());
        for (int i = 0; i < anzahl; i++) {
            if (falle == null)
                falle =
                        new TrapTeleport(
                                currentLevel.getRandomFloorTile().getCoordinate().toPoint());
            entity.add(falle);
            do {
                tile = currentLevel.getRandomFloorTile();
                falle.setTrapTile(tile.getCoordinate().toPoint());
            } while (starter.Game.positionList.contains(tile));
            starter.Game.positionList.add(tile);

            if (falle.getLever() != null) {
                entity.add(falle.getLever());
                do {
                    tile = currentLevel.getRandomFloorTile();
                    falle.getLever().setLever(tile.getCoordinate().toPoint());
                } while (starter.Game.positionList.contains(tile));
                starter.Game.positionList.add(tile);
            }
            if (!createSameTrap) falle = null;
        }
    }

    /**
     * Sets the trap to be created
     *
     * @param falle trap to be created
     */
    public void setFalle(Trap falle) {
        this.falle = falle;
    }

    /**
     * Sets whether the same predefined trap should be created
     *
     * @param createSameTrap whether the same trap should be created
     */
    public void setCreateSameTrap(boolean createSameTrap) {
        this.createSameTrap = createSameTrap;
    }
}
