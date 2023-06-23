package ecs.systems;

import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.*;

import com.badlogic.gdx.scenes.scene2d.Actor;
import controller.SystemController;
import ecs.components.xp.ILevelUp;
import ecs.components.xp.XPComponent;
import ecs.entities.Hero;
import ecs.quests.GraveQuest;
import ecs.quests.KillQuest;
import ecs.quests.Quest;
import graphic.hud.QuestMenu;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import starter.Game;

/** Testclass for the class "QuestSystem" */
public class QuestSystemTest {
    private Game game;
    private Hero hero;
    private Set<Quest> questList;
    private KillQuest killquest;
    private GraveQuest gravequest;
    private QuestSystem system;
    private QuestMenu<Actor> questMenu;
    private static String screenText;

    /**
     * Setup for the tests
     *
     * <p>A Questmenu is mocked and set as the Questmenu of the game A new killquest is created A
     * Hero is mocked and set as the Hero of the game
     */
    @Before
    public void setUp() {
        questMenu = Mockito.mock(QuestMenu.class);
        Game.setQuestMenu(questMenu);
        Game.systems = new SystemController();
        killquest = new KillQuest();
        system = new QuestSystemStud();
        hero = mock(Hero.class);
        Game.setHero(hero);
    }

    /**
     * Test for the method "UpdateRefresh" in the class "QuestSystem"
     *
     * <p>The countKilledMonsters method of the killquest is called and the refreshQuestMenu method
     * of the system is called to update the screenText. The test is passed if the screenText is
     * equal to the name and status of the quest
     */
    @Test
    public void testUpdateRefresh() {
        killquest.countKilledMonsters();
        system.refreshQuestMenu(killquest);

        assertTrue("KillQuest 1/10".equals(screenText));
    }

    /**
     * Test for the method "UpdateFinishQuest" in the class "QuestSystem"
     *
     * <p>The reward of the killquest is set to 100 The test is passed if the currentXP of the hero
     * is 100, the killquest is not active and the killquest is finished
     */
    @Test
    public void testUpdateFinishQuest() {
        ILevelUp levelUp = Mockito.mock(ILevelUp.class);
        XPComponent xpComponent = new XPComponent(hero, levelUp);
        when(hero.getXP()).thenReturn(xpComponent);

        system.setReward(killquest);
        assertEquals(100, hero.getXP().getCurrentXP());
        assertFalse(killquest.isActive());
        assertTrue(killquest.isFinished());
    }

    /** Class to implement the QuestSystem for the tests */
    public class QuestSystemStud extends QuestSystem {

        /** Method to mock the refreshQuestMenu method */
        public void refreshQuestMenu(Quest quest) {
            QuestSystemTest.screenText = quest.getName() + " " + quest.getStatus();
        }
    }
}
