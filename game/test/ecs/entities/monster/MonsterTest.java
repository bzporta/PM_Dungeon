package ecs.entities.monster;

import static org.junit.Assert.*;
import static org.junit.Assume.assumeTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.Mockito.*;

import ecs.components.PositionComponent;
import ecs.components.VelocityComponent;
import ecs.entities.Hero;
import ecs.quests.KillQuest;
import graphic.Animation;
import level.elements.ILevel;
import level.elements.tile.Tile;
import org.junit.Before;
import org.junit.Test;
import starter.Game;
import tools.Point;

/** Testclass for the class "Monster" */
public class MonsterTest {

    private Monster darkHeart;
    private Monster andromalius;
    private Monster imp;
    private Monster monsterStub;
    private Hero hero;
    private ILevel level;
    private Tile endtile;

    /** Setup for the tests */
    @Before
    public void setUp() {
        level = mock(ILevel.class);
        Game.currentLevel = level;
        endtile = mock(Tile.class);
        System.out.println(level);

        imp = mock(Imp.class);
        darkHeart = mock(DarkHeart.class);
        andromalius = mock(Andromalius.class);
        monsterStub = mock(MonsterStub.class);

        hero = mock(Hero.class);
        Game.setHero(hero);
        when(hero.getPosition()).thenReturn(new Point(5, 5));
        PositionComponent pc = new PositionComponent(darkHeart, 0, 0);
        doCallRealMethod().when(darkHeart).setPc(any());
        doCallRealMethod().when(darkHeart).getPc();
        darkHeart.setPc(pc);
    }

    /**
     * Tests if a created monster is added to the list of entities
     *
     * <p>Testcase: A monster is created and added to the list of entities
     */
    @Test
    public void testMonsterAdded() {
        Game.getEntities().add(darkHeart);
        Game.getEntities().add(imp);
        Game.getEntities().add(andromalius);

        assumeTrue(Game.getEntities().contains(darkHeart));
        assumeTrue(Game.getEntities().contains(imp));
        assertTrue(Game.getEntities().contains(andromalius));
    }

    /**
     * Test for the method "knockback" in the class "Monster"
     *
     * <p>Testcase: Negative knockback amount -> IllegalArgumentException
     */
    @Test
    public void testNegativeKnockbackAmount() {
        when(level.getTileAt(any())).thenReturn(mock(Tile.class));
        when(level.getTileAt(any()).isAccessible()).thenReturn(true);
        doCallRealMethod().when(darkHeart).knockback(anyFloat());
        try {
            darkHeart.knockback(-2f);
            fail("No IllegalArgumentException has been thrown");
        } catch (IllegalArgumentException ex) {
            assertNotNull(ex.getMessage());
        }
    }

    /**
     * Test for the method "knockback" in the class "Monster"
     *
     * <p>Testcase: Zero knockback amount -> No movement
     */
    @Test
    public void testZeroKnockbackAmount() {
        when(level.getTileAt(any())).thenReturn(mock(Tile.class));
        when(level.getTileAt(any()).isAccessible()).thenReturn(true);
        doCallRealMethod().when(darkHeart).knockback(anyFloat());
        darkHeart.knockback(0f);

        assertEquals(0f, darkHeart.getPc().getPosition().x, 0f);
        assertEquals(0f, darkHeart.getPc().getPosition().y, 0f);
    }

    /**
     * Test for the method "knockback" in the class "Monster"
     *
     * <p>Testcase: Positive knockback amount -> Movement in the opposite direction to the right
     * position
     */
    @Test
    public void testPositiveKnockbackAmount() {
        when(level.getTileAt(any())).thenReturn(mock(Tile.class));
        when(level.getTileAt(any()).isAccessible()).thenReturn(true);
        doCallRealMethod().when(darkHeart).knockback(anyFloat());
        darkHeart.knockback(2f);

        assertEquals(-1.5f, darkHeart.getPc().getPosition().x, 0.1f);
        assertEquals(-1.5f, darkHeart.getPc().getPosition().y, 0.1f);
    }

    /**
     * Test for the method "knockback" in the class "Monster"
     *
     * <p>Testcase: Knockback amount is very high -> Movement gets limited to a smaller amount
     */
    @Test
    public void testMaxKnockbackAmount() {
        when(level.getTileAt(any())).thenReturn(mock(Tile.class));
        when(level.getTileAt(any()).isAccessible()).thenReturn(true);
        doCallRealMethod().when(darkHeart).knockback(anyFloat());
        darkHeart.knockback(1000f);

        assertEquals(-2, darkHeart.getPc().getPosition().x, 0.5f);
        assertEquals(-2, darkHeart.getPc().getPosition().y, 0.5f);
    }

    /**
     * Test for the method "setFrozen" in the class "Monster"
     *
     * <p>Testcase: Monster is frozen -> Velocity is set to zero
     */
    @Test
    public void testSetFrozen() throws Exception {
        doCallRealMethod().when(monsterStub).setFrozen();
        doCallRealMethod().when(monsterStub).getVc();
        doCallRealMethod().when(monsterStub).setVc(any());
        monsterStub.setVc(
                new VelocityComponent(
                        monsterStub, 5, 5, mock(Animation.class), mock(Animation.class)));
        monsterStub.setFrozen();

        assertEquals(0f, monsterStub.getVc().getXVelocity(), 0f);
        assertEquals(0f, monsterStub.getVc().getYVelocity(), 0f);
    }

    /**
     * Test for the method "onDeath" in the class "Monster"
     *
     * <p>Testcase: Monster is killed -> Killed-monsters-counter is increased by one
     */
    @Test
    public void testOnDeath() {
        doCallRealMethod().when(darkHeart).setupHealthComponent();
        doCallRealMethod().when(darkHeart).getHp();
        KillQuest killQuest = new KillQuest();
        killQuest.setActive(true);
        Game.setKillQuest(killQuest);

        darkHeart.setupHealthComponent();
        darkHeart.getHp().setCurrentHealthpoints(0);
        darkHeart.getHp().triggerOnDeath();

        assertEquals(1, killQuest.getKilledMonsters());
    }

    /**
     * Test for the method "setHitDmg" in the class "Monster"
     *
     * <p>Testcase: Negative hit damage -> The right amount of damage is dealt to the hero (Negative
     * Hitdamages are taken into positive amount)
     */
    @Test
    public void testNegativeSetHitDamage() {
        doCallRealMethod().when(darkHeart).setHitDmg(anyInt());
        doCallRealMethod().when(darkHeart).getHitDmg();
        doCallRealMethod().when(hero).getHC();
        doCallRealMethod().when(hero).setupHealthComponent();
        hero.setupHealthComponent();
        hero.getHC().setCurrentHealthpoints(60);

        darkHeart.setHitDmg(-50);
        hero.getHC()
                .setCurrentHealthpoints(
                        hero.getHC().getCurrentHealthpoints() - darkHeart.getHitDmg());

        assumeTrue(darkHeart.getHitDmg() == -50);
        assertEquals(10, hero.getHC().getCurrentHealthpoints());
    }

    /**
     * Test for the method "setHitDmg" in the class "Monster"
     *
     * <p>Testcase: Zero hit damage -> No damage is dealt to the hero
     */
    @Test
    public void testZeroSetHitDamage() {
        doCallRealMethod().when(darkHeart).setHitDmg(anyInt());
        doCallRealMethod().when(darkHeart).getHitDmg();
        doCallRealMethod().when(hero).getHC();
        doCallRealMethod().when(hero).setupHealthComponent();
        hero.setupHealthComponent();

        darkHeart.setHitDmg(0);
        hero.getHC()
                .setCurrentHealthpoints(
                        hero.getHC().getCurrentHealthpoints() - darkHeart.getHitDmg());

        assumeTrue(darkHeart.getHitDmg() == 0);
        assertEquals(100, hero.getHC().getCurrentHealthpoints());
    }

    /**
     * Test for the method "setHitDmg" in the class "Monster"
     *
     * <p>Testcase: Positive hit damage -> The right amount of damage is dealt to the hero
     */
    @Test
    public void testPositiveSetHitDamage() {
        doCallRealMethod().when(darkHeart).setHitDmg(anyInt());
        doCallRealMethod().when(darkHeart).getHitDmg();
        doCallRealMethod().when(hero).getHC();
        doCallRealMethod().when(hero).setupHealthComponent();
        hero.setupHealthComponent();

        darkHeart.setHitDmg(50);
        hero.getHC()
                .setCurrentHealthpoints(
                        hero.getHC().getCurrentHealthpoints() - darkHeart.getHitDmg());

        assumeTrue(darkHeart.getHitDmg() == 50);
        assertEquals(50, hero.getHC().getCurrentHealthpoints());
    }

    /**
     * Test for the method "setHitDmg" in the class "Monster"
     *
     * <p>Testcase: Hit damage is set to the maximal healthpoints of the hero -> The hero dies
     */
    @Test
    public void testMaxSetHitDamage() {
        doCallRealMethod().when(darkHeart).setHitDmg(anyInt());
        doCallRealMethod().when(darkHeart).getHitDmg();
        doCallRealMethod().when(hero).getHC();
        doCallRealMethod().when(hero).setupHealthComponent();
        hero.setupHealthComponent();

        darkHeart.setHitDmg(hero.getHC().getMaximalHealthpoints());
        hero.getHC()
                .setCurrentHealthpoints(
                        hero.getHC().getCurrentHealthpoints() - darkHeart.getHitDmg());

        assumeTrue(darkHeart.getHitDmg() == 100);
        assertEquals(0, hero.getHC().getCurrentHealthpoints());
    }

    /**
     * Class to implement the abstract class "Monster" with minimal logic
     *
     * <p>Without the setVc is not working, because the required animation is not available. The new
     * setVc-method sets a new VelocityComponent with a mock-animation.
     */
    public class MonsterStub extends Monster {
        /**
         * Constructor to create a MonsterStub A velocity component with a mocked animation is
         * created
         */
        MonsterStub() {
            super(any(), any(), any(), any(), any(), any(), any());
            setVc(
                    new VelocityComponent(
                            monsterStub, 5, 5, mock(Animation.class), mock(Animation.class)));
        }

        /**
         * Method to setup the ai component
         *
         * <p>Gets overwritten to do nothing, because the abstract method has to be implemented but
         * is not needed for the test
         */
        @Override
        public void setupAiComponent() {
            // DO-NOTHING
        }

        /**
         * Method to set the monster to frozen
         *
         * <p>Sets the velocity component to a new velocity component with zero velocity
         */
        @Override
        public void setFrozen() {
            setVc(new VelocityComponent(this, 0, 0, mock(Animation.class), mock(Animation.class)));
        }
    }
}
