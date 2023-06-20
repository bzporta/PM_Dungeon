package ecs.entities.trap;
import dslToGame.AnimationBuilder;
import ecs.components.AnimationComponent;
import ecs.components.PositionComponent;
import ecs.entities.Entity;
import level.elements.ILevel;
import level.elements.tile.Tile;
import level.tools.Coordinate;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import starter.Game;
import tools.Point;

import java.util.Set;

import static org.junit.Assert.*;
import static org.junit.Assume.assumeFalse;
import static org.junit.Assume.assumeTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class TrapTest {

    TrapDmgCreator trapDmgCreator;
    TrapTeleportCreator trapTeleportCreator;
    Set<Entity> entities;
    ILevel level = Mockito.mock(ILevel.class);
    Trap trap = mock(TrapDmg.class);
    Trap trap2 = mock(TrapTeleport.class);
    Tile tile = Mockito.mock(Tile.class);
    Lever lever;

    @Before
    public void setup(){
        trapDmgCreator = new TrapDmgCreator();
        trapTeleportCreator = new TrapTeleportCreator();
        entities = Game.getEntities();
        Game.currentLevel = level;
    }

    @Test
    public void testCreatorNegativeAmount(){
        try{
            trapDmgCreator.creator(-1,entities,level);
            trapTeleportCreator.creator(-1,entities, level);
            fail("No IllegalArgumentException has been thrown");
        }catch (IllegalArgumentException ex){
            assertNotNull(ex.getMessage());
        }
    }
    @Test
    public void testCreatorZeroAmount(){
        entities.clear();
        trapDmgCreator.creator(0,entities, level);
        trapTeleportCreator.creator(0,entities, level);
        assertTrue(entities.isEmpty());

    }
    @Test
    public void testCreatorPositiveAmount(){
        when(level.getRandomFloorTile()).thenReturn(tile);
        Coordinate cords = new Coordinate(0,0);
        when(tile.getCoordinate()).thenReturn(cords);
        trapDmgCreator.setFalle(trap);
        entities.clear();
        Game.positionList.clear();
        trapDmgCreator.creator(1,entities, level);
        assumeTrue("Trap1 added",entities.contains(trap));

        trapTeleportCreator.setFalle(trap2);
        entities.clear();
        Game.positionList.clear();
        trapTeleportCreator.creator(1,entities, level);
        assertTrue("Trap2 added",entities.contains(trap2));

    }
    @Test
    public void testCreatorHighAmount(){
        //Siehe TrapDmgCreator und TeleportCreator
        when(level.getRandomFloorTile()).thenReturn(tile);
        Coordinate cords = new Coordinate(0,0);
        when(tile.getCoordinate()).thenReturn(cords);
        trapDmgCreator.setFalle(trap);
        entities.clear();
        Game.positionList.clear();
        try{
            trapDmgCreator.creator(1000,entities,level);
            //trapTeleportCreator.creator(1000,entities, level);
            fail("No IllegalArgumentException has been thrown");
        }catch (IllegalArgumentException ex){
            assertNotNull(ex.getMessage());
        }
    }
    @Test
    public void testCheckPositionList(){
        when(level.getRandomFloorTile()).thenReturn(tile);
        Coordinate cords = new Coordinate(0,0);
        when(tile.getCoordinate()).thenReturn(cords);
        trapDmgCreator.setFalle(trap);
        entities.clear();
        Game.positionList.clear();
        trapDmgCreator.creator(1,entities, level);
        assertTrue(Game.positionList.contains(tile));

        trapTeleportCreator.setFalle(trap2);
        entities.clear();
        Game.positionList.clear();
        trapTeleportCreator.creator(1,entities, level);
        assertTrue(Game.positionList.contains(tile));
    }
    @Test
    public void testPullLeverDmgTrap(){
        when(level.getRandomFloorTile()).thenReturn(tile);
        Coordinate cords = new Coordinate(0,0);
        when(tile.getCoordinate()).thenReturn(cords);
        trapDmgCreator.setFalle(trap);
        trapDmgCreator.creator(1,entities,level);

    }
    @Test
    public void testPullLeverTeleportTrap(){

    }
    @Test
    public void testTeleport(){

    }
    @Test
    public void testSetTrapTile(){

    }

}
