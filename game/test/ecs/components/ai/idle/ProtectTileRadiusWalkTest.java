package ecs.components.ai.idle;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import ecs.components.PositionComponent;
import ecs.entities.Entity;
import level.elements.ILevel;
import level.elements.tile.Tile;
import org.junit.Before;
import org.junit.Test;
import starter.Game;
import tools.Point;

/** Testclass for the class "ProtectTileRadiusWalk" */
public class ProtectTileRadiusWalkTest {

    private ProtectTileRadiusWalk ai;
    private Entity entity;
    private Tile tile;
    private static int frames;

    /**
     * Setup for the tests
     *
     * <p>Mocks the level and the tile and creates an entity with a positioncomponent
     */
    @Before
    public void setUp() throws Exception {
        ILevel level = mock(ILevel.class);
        Game.currentLevel = level;
        tile = mock(Tile.class);
        when(tile.getCoordinateAsPoint()).thenReturn(new Point(5, 5));
        entity = new Entity();
        PositionComponent pc = new PositionComponent(entity, new Point(1, 1));
        frames = 0;
    }

    /**
     * Test for the ProtectTileRadiusWalk with a breaktime of -2 seconds
     *
     * <p>Testcase: Breaktime of -2 seconds -> 0 frames -> No error expected
     */
    @Test
    public void testNegativeBreakTime() {
        ai = new ProtectTileRadiusWalkStub(tile, -2);
        ai.idle(entity);

        assertEquals(0, frames);
    }

    /**
     * Test for the ProtectTileRadiusWalk with a breaktime of 0 seconds
     *
     * <p>Testcase: Breaktime of 0 seconds -> 0 frames -> No error expected
     */
    @Test
    public void testZeroBreakTime() {
        ai = new ProtectTileRadiusWalkStub(tile, 0);
        ai.idle(entity);

        assertEquals(0, frames);
    }

    /**
     * Test for the ProtectTileRadiusWalk with a breaktime of 2 seconds
     *
     * <p>Testcase: Breaktime of 2 seconds -> 60 frames -> No error expected
     */
    @Test
    public void testPositiveBreakTime() {
        ai = new ProtectTileRadiusWalkStub(tile, 2);
        ai.idle(entity);

        assertEquals(60, frames);
    }

    /**
     * Test for the ProtectTileRadiusWalk with a breaktime of 20 seconds
     *
     * <p>Testcase: Breaktime of 20 seconds -> 600 frames -> No error expected
     */
    @Test
    public void testMaxBreakTime() {
        ai = new ProtectTileRadiusWalkStub(tile, 20);
        ai.idle(entity);

        assertEquals(600, frames);
    }

    /**
     * Class to implement class "ProtectTileRadiusWalk" with minimal logic to simplify the
     * idle-Method
     */
    public class ProtectTileRadiusWalkStub extends ProtectTileRadiusWalk {

        /**
         * Constructor for the class "ProtectTileRadiusWalkStub"
         *
         * @param tile Tile to protect
         * @param breakTimeInSeconds Breaktime in seconds
         */
        ProtectTileRadiusWalkStub(Tile tile, int breakTimeInSeconds) {
            super(tile, breakTimeInSeconds);
        }

        /**
         * Method to simplify the idle-Method
         *
         * <p>Simulates the time passed by increasing the currentBreak-Variable (One second = 30
         * frames)
         *
         * @param entity Entity to idle
         */
        @Override
        public void idle(Entity entity) {
            if (currentBreak >= breakTime) {
                return;
            }
            ProtectTileRadiusWalkTest.frames++;
            currentBreak++;
            idle(entity);
        }
    }
}
