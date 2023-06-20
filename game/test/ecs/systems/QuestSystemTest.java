package ecs.systems;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.GdxNativesLoader;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.StringBuilder;
import controller.ScreenController;
import controller.SystemController;
import ecs.components.xp.ILevelUp;
import ecs.components.xp.XPComponent;
import ecs.entities.Hero;
import ecs.quests.GraveQuest;
import ecs.quests.KillQuest;
import ecs.quests.Quest;
import graphic.hud.FontBuilder;
import graphic.hud.LabelStyleBuilder;
import graphic.hud.QuestMenu;
import graphic.hud.ScreenText;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import starter.Game;
import starter.LibgdxSetup;
import tools.Point;

import java.awt.*;
import java.util.Set;

import static org.junit.Assert.*;
import static org.junit.Assume.assumeTrue;
import static org.powermock.api.mockito.PowerMockito.*;

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
    public void testUpdateRefresh(){
        killquest.countKilledMonsters();
        system.refreshQuestMenu(killquest);

        assertTrue("KillQuest 1/10".equals(screenText));
    }

    @Test
    public void testUpdateFinishQuest(){
        ILevelUp levelUp = Mockito.mock(ILevelUp.class);
        XPComponent xpComponent = new XPComponent(hero, levelUp);
        when(hero.getXP()).thenReturn(xpComponent);

        system.setReward(killquest);
        assertEquals(100, hero.getXP().getCurrentXP());
        assertFalse(killquest.isActive());
        assertTrue(killquest.isFinished());
    }
}

class QuestSystemStud extends QuestSystem{

    public void refreshQuestMenu(Quest quest) {
        QuestSystemTest.screenText = quest.getName() + " " + quest.getStatus();
    }



}
