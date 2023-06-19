package ecs.entities.trap;
import ecs.entities.Entity;
import level.elements.ILevel;
import level.elements.tile.FloorTile;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import starter.Game;
import tools.Point;

import java.util.Set;

import static org.junit.Assert.*;
import static org.junit.Assume.assumeFalse;
import static org.junit.Assume.assumeTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

public class TrapTest {

    TrapDmgCreator trapDmgCreator;
    TrapTeleportCreator trapTeleportCreator;
    Set<Entity> entities;
    ILevel level = Mockito.spy(ILevel.class);

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
        doReturn(new Point(0,0)).when(Game.currentLevel.getRandomFloorTile().getCoordinateAsPoint());
        entities.clear();
        trapDmgCreator.creator(1,entities, level);
        trapTeleportCreator.creator(1,entities, level);
        assertFalse(entities.isEmpty());
    }
    @Test
    public void testCreatorHighAmount(){

    }
    @Test
    public void testCheckPositionList(){

    }
    @Test
    public void testPullLeverDmgTrap(){
        entities.clear();
        Trap trap = new TrapDmg();
        entities.add(trap);
        assumeFalse(entities.isEmpty());
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
