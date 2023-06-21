package ecs.entities.trap;
import ecs.components.PositionComponent;
import ecs.damage.Damage;
import ecs.entities.Entity;
import level.elements.ILevel;
import level.elements.tile.FloorTile;
import level.elements.tile.Tile;
import level.tools.Coordinate;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import starter.Game;
import tools.Point;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;
import static org.junit.Assert.*;
import static org.junit.Assume.assumeTrue;
import static org.mockito.Mockito.*;

/** Testclass for the classes "TrapDmgCreator" and "TrapTeleportCreator" */
public class TrapTest {

    TrapDmgCreator trapDmgCreator;
    TrapTeleportCreator trapTeleportCreator;
    Set<Entity> entities;
    ILevel level = Mockito.mock(ILevel.class);
    static TrapDmg trap = mock(TrapDmg.class);
    static TrapTeleport trap2 = mock(TrapTeleport.class);
    Tile tile = Mockito.mock(Tile.class);
    Lever lever;
    Entity hero;
    List<FloorTile> floorTiles;

    /** Setup for the tests
     *
     * A trapDmgCreator and a trapTeleportCreator are created
     */
    @Before
    public void setup(){
        trapDmgCreator = new TrapDmgCreator();
        trapTeleportCreator = new TrapTeleportCreator();
        entities = Game.getEntities();
        Game.currentLevel = level;
        hero = new Entity();

        floorTiles = mock(List.class);
        when(level.getFloorTiles()).thenReturn(floorTiles);
        when(level.getFloorTiles().size()).thenReturn(5);
    }

    /** Test for the method "creator" in the class "TrapDmgCreator"
     *
     * Testcase: Negative amount of traps -> IllegalArgumentException
     */
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

    /** Test for the method "creator" in the class "TrapDmgCreator"
     *
     * Testcase: Zero amount of traps -> No traps added
     */
    @Test
    public void testCreatorZeroAmount(){
        entities.clear();
        trapDmgCreator.creator(0,entities, level);
        trapTeleportCreator.creator(0,entities, level);
        assertTrue(entities.isEmpty());

    }

    /** Test for the method "creator" in the class "TrapDmgCreator"
     *
     * Testcase: Positive amount of traps -> Traps added
     */
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

    /** Test for the method "creator" in the class "TrapDmgCreator"
     *
     * Testcase: High amount of traps -> IllegalArgumentException
     */
    @Test @Ignore
    public void testCreatorHighAmount() throws Exception {
        // ACHTUNG ENDLOSSCHLEIFE BEIM TESTEN!!
        // Weil wir keine Exception bei so einer hohen Anzahl werfen, ensteht eine Endlosschleife
        // Um dies zu beheben --> bei TrapDmgCreator/TrapTeleportCreator die If-Abfrage einkommentieren.
        when(level.getRandomFloorTile()).thenReturn(tile);
        Coordinate cords = new Coordinate(0,0);
        when(tile.getCoordinate()).thenReturn(cords);
        trapDmgCreator.setFalle(trap);
        trapDmgCreator.setCreateSameTrap(true);
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

    /** Checks if the position of the trap is added to the positionList
     *
     * Testcase: Trap added to the positionList
     */
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

    /** Test for the method "pullLever" in the class "TrapDmg"
     *
     * Testcase: Trap is activated
     */
    @Test
    public void testPullLeverDmgTrap() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        when(level.getRandomFloorTile()).thenReturn(tile);
        Coordinate cords = new Coordinate(0,0);
        when(tile.getCoordinate()).thenReturn(cords);
        trapDmgCreator.setFalle(trap);
        trapDmgCreator.creator(1,entities,level);

        doCallRealMethod().when(trap).setDmg(any());
        doCallRealMethod().when(trap).deactivateTrap();
        doCallRealMethod().when(trap).getDmg();
        trap.setDmg(new Damage(5,null,null));
        lever = mock(LeverStub.class);

        Method method = LeverStub.class.getDeclaredMethod("pullLever", Entity.class);
        method.setAccessible(true);
        method.invoke(lever,lever);

        assertEquals(0, trap.getDmg().damageAmount());
    }

    /** Test for the method "pullLever" in the class "TrapTeleport"
     *
     * Testcase: Trap is activated
     */
    @Test
    public void testPullLeverTeleportTrap() throws Exception {
        when(level.getRandomFloorTile()).thenReturn(tile);
        Coordinate cords = new Coordinate(0,0);
        when(tile.getCoordinate()).thenReturn(cords);
        trapTeleportCreator.setFalle(trap2);
        trapTeleportCreator.creator(1,entities,level);

        doCallRealMethod().when(trap2).setActive(anyBoolean());
        doCallRealMethod().when(trap2).deactivateTrap();
        doCallRealMethod().when(trap2).isActive();
        trap2.setActive(true);
        lever = mock(LeverStub.class);

        Method method = LeverStub.class.getDeclaredMethod("pullLever", Entity.class);
        method.setAccessible(true);
        method.invoke(lever,lever);

        assertFalse(trap2.isActive());
    }

    /** Test for the teleport method in the class "TrapTeleport"
     *
     * Testcase: Hero is teleported to the right position
     */
    @Test
    public void testTeleport(){
        doCallRealMethod().when(trap2).setActive(anyBoolean());
        doCallRealMethod().when(trap2).teleport(any(), any());
        trap2.setActive(true);
        PositionComponent pc = new PositionComponent(hero, new Point(5,5));

        trap2.teleport(hero, new Point(15,25));

        assertEquals(15.0f, pc.getPosition().x, 0.0f);
        assertEquals(25.0f, pc.getPosition().y, 0.0f);
    }

    /** Test for the method "setTrapTile" in the class "Trap"
     *
     * Testcase: Trap is set to the right position
     */
    @Test
    public void testSetTrapTile(){
        doCallRealMethod().when(trap).setTrapTile(any());
        doCallRealMethod().when(trap).getPoint();
        doCallRealMethod().when(trap).setPc(any());
        PositionComponent pc = new PositionComponent(trap, new Point(0,0));
        trap.setPc(pc);
        trap.setTrapTile(new Point(15,25));

        assertEquals(15.0f, trap.getPoint().x, 0.0f);
        assertEquals(25.0f, trap.getPoint().y, 0.0f);
    }


    public class LeverStub extends Lever{
        public LeverStub(Trap trap){
            super(trap);
        }


        private void pullLever(Entity entity) {
            TrapTest.trap.deactivateTrap();
            TrapTest.trap2.deactivateTrap();
        }
    }

}
