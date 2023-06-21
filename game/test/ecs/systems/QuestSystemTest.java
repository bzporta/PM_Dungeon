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

public class QuestSystemTest {
    Game game;
    Hero hero;
    Set<Quest> questList;
    KillQuest killquest;
    GraveQuest gravequest;
    QuestSystem system;
    QuestMenu<Actor> questMenu;
    static String screenText;

    @Before
    public void setUp() throws Exception {
        questMenu = Mockito.mock(QuestMenu.class);
        Game.setQuestMenu(questMenu);
        Game.systems = new SystemController();
        killquest = new KillQuest();
        system = new QuestSystemStud();
        hero = mock(Hero.class);
        Game.setHero(hero);
    }

    @Test
    public void testUpdateRefresh() {
        killquest.countKilledMonsters();
        system.refreshQuestMenu(killquest);

        assertTrue("KillQuest 1/10".equals(screenText));
    }

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
}

class QuestSystemStud extends QuestSystem {

    public void refreshQuestMenu(Quest quest) {
        QuestSystemTest.screenText = quest.getName() + " " + quest.getStatus();
    }
}
