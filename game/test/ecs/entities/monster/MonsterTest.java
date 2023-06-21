package ecs.entities.monster;

import ecs.components.AnimationComponent;
import interpreter.mockECS.Entity;
import level.elements.ILevel;
import level.elements.tile.Tile;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import starter.Game;

import static org.junit.Assume.assumeTrue;

public class MonsterTest {

    Imp imp;
    DarkHeart darkHeart;
    Andromalius andromalius;
    Entity hero;
    Game game;
    ILevel level;
    Tile endtile;


    @Before
    public void setUp(){
        level = Mockito.mock(ILevel.class);
        endtile = Mockito.mock(Tile.class);
        System.out.println(level);

        darkHeart = new DarkHeart();
        andromalius = new Andromalius();
        hero = new Entity();


    }

    @Test
    public void testMonsterParameters() {
        assumeTrue(imp.getHitDmg() == 5);
    }

    @Test
    public void testMonsterAdded() {

    }

    @Test
    public void testNegativeKnockbackAmount() {
        imp.knockback(-3);
    }

    @Test
    public void testZeroKnockbackAmount() {

    }

    @Test
    public void testPositiveKnockbackAmount() {

    }

    @Test
    public void testMaxKnockbackAmount() {

    }

    @Test
    public void testSetFrozen() {

    }

    @Test
    public void testOnDeath() {

    }

    @Test
    public void testNegativeSetHitDamage() {

    }

    @Test
    public void testZeroSetHitDamage() {

    }

    @Test
    public void testPositiveSetHitDamage() {

    }

    @Test
    public void testMaxSetHitDamage() {

    }
}
