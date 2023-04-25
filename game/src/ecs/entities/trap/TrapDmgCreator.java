package ecs.entities.trap;

import ecs.entities.Entity;
import level.elements.ILevel;
import java.util.Set;

public class TrapDmgCreator implements TrapFactory{


    @Override
    public void creator(int anzahl, Set<Entity> entity, ILevel currentLevel) {

        tools.Point start = currentLevel.getStartTile().getCoordinate().toPoint();
        //positionList.add(currentLevel.getStartTile().getCoordinate().toPoint());
        for(int i = 0; i < anzahl; i++) {
            Trap falle = new TrapDmg();
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
        //to delete
        System.out.println(positionList.size());
    }

    @Override
    public void clearList() {
        positionList.removeAll(positionList);
    }
}
