package ecs.entities.trap;

import ecs.entities.Entity;
import level.elements.ILevel;

import java.util.Set;

public class TrapTeleportCreator implements TrapFactory{

    @Override
    public void creator(int anzahl, Set<Entity> entity, ILevel currentLevel) {

        //positionList.add(currentLevel.getStartTile().getCoordinate().toPoint());
        tools.Point start = currentLevel.getStartTile().getCoordinate().toPoint();
        for (int i = 0; i < anzahl; i++) {
            Trap falle = new TrapTeleport(currentLevel.getRandomFloorTile().getCoordinate().toPoint());
            entity.add(falle);
            do{
                falle.setTrapTile(currentLevel.getRandomFloorTile().getCoordinate().toPoint());

            }while(positionList.contains(falle.getPoint()) && falle.getPoint().equals(start));
            positionList.add(falle.getPoint());

            if (falle.getLever() != null){
                entity.add(falle.getLever());
                do{
                    falle.getLever().setLever(currentLevel.getRandomFloorTile().getCoordinate().toPoint());

                }while(positionList.contains(falle.getLever().getPoint()));
                positionList.add(falle.getLever().getPoint());
            }
        }
    }
    @Override
    public void clearList() {
        positionList.removeAll(positionList);
    }
}
