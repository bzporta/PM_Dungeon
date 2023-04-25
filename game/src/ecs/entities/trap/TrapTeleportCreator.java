package ecs.entities.trap;

import ecs.entities.Entity;
import level.elements.ILevel;
import level.elements.tile.FloorTile;
import level.elements.tile.Tile;

import java.util.Set;

public class TrapTeleportCreator implements TrapFactory{



    @Override
    public void creator(int anzahl, Set<Entity> entity, ILevel currentLevel) {

        Tile tile;

        positionList.add(currentLevel.getStartTile());
        for (int i = 0; i < anzahl; i++) {
            Trap falle = new TrapTeleport(currentLevel.getRandomFloorTile().getCoordinate().toPoint());
            entity.add(falle);
            do{
                tile = currentLevel.getRandomFloorTile();
                falle.setTrapTile(tile.getCoordinate().toPoint());
            }while(positionList.contains(tile));
            positionList.add(tile);

            if (falle.getLever() != null){
                entity.add(falle.getLever());
                do{
                    tile = currentLevel.getRandomFloorTile();
                    falle.getLever().setLever(tile.getCoordinate().toPoint());
                }while(positionList.contains(tile));
                positionList.add(tile);
            }
        }
    }
    @Override
    public void clearList() {
        positionList.removeAll(positionList);
    }
}
