package ecs.components.ai.idle;

import ecs.components.ai.AIComponent;
import ecs.entities.Entity;
import ecs.entities.Hero;
import level.elements.ILevel;
import level.elements.tile.Tile;
import level.elements.tile.WallTile;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import starter.Game;

public class ProtectTileRadiusWalkTest {

    Hero hero;
    @Before
    public void setUp() throws Exception {
        ILevel level = Mockito.mock(ILevel.class);
        Game.currentLevel = level;
        hero = new Hero();

    }

    @Test
    public void testNegativeBreakTime() {
        AIComponent aiComponent = new AIComponent(hero, );
        ProtectTileRadiusWalk protectTileRadiusWalk = new ProtectTileRadiusWalk(Game.currentLevel.getEndTile(), -1);

    }

    @Test
    public void testZeroBreakTime() {

    }

    @Test
    public void testPositiveBreakTime() {

    }

    @Test
    public void testMaxBreakTime() {

    }

    @Test
    public void testNegativeRadius() {

    }

    @Test
    public void testZeroRadius() {

    }

    @Test
    public void testPositiveRadius() {

    }

    @Test
    public void testMaxRadius() {

    }

    @Test
    public void testNotAccessibleTile() {

    }

    @Test
    public void testAccessibleTile() {

    }
}
