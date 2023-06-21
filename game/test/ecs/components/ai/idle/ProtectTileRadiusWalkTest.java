package ecs.components.ai.idle;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import ecs.components.PositionComponent;
import ecs.entities.Entity;
import ecs.entities.Hero;
import level.elements.ILevel;
import level.elements.tile.Tile;
import org.junit.Before;
import org.junit.Test;
import starter.Game;
import tools.Point;

public class ProtectTileRadiusWalkTest {

    Hero hero;
    ProtectTileRadiusWalk ai;
    Entity entity;
    Tile tile;
    static int frames;

    @Before
    public void setUp() throws Exception {
        ILevel level = mock(ILevel.class);
        Game.currentLevel = level;
        hero = mock(Hero.class);
        tile = mock(Tile.class);
        when(tile.getCoordinateAsPoint()).thenReturn(new Point(5, 5));
        entity = new Entity();
        PositionComponent pc = new PositionComponent(entity, new Point(1, 1));
        frames = 0;
    }

    @Test
    public void testNegativeBreakTime() {
        ai = new ProtectTileRadiusWalkStub(tile, -2);
        ai.idle(entity);

        assertEquals(0, frames);
    }

    @Test
    public void testZeroBreakTime() {
        ai = new ProtectTileRadiusWalkStub(tile, 0);
        ai.idle(entity);

        assertEquals(0, frames);
    }

    @Test
    public void testPositiveBreakTime() {
        ai = new ProtectTileRadiusWalkStub(tile, 2);
        ai.idle(entity);

        assertEquals(60, frames);
    }

    @Test
    public void testMaxBreakTime() {
        ai = new ProtectTileRadiusWalkStub(tile, 20);
        ai.idle(entity);

        assertEquals(600, frames);
    }

    public class ProtectTileRadiusWalkStub extends ProtectTileRadiusWalk {

        ProtectTileRadiusWalkStub(Tile tile, int breakTimeInSeconds) {
            super(tile, breakTimeInSeconds);
        }

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
