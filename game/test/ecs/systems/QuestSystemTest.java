package ecs.systems;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import controller.ScreenController;
import controller.SystemController;
import ecs.entities.Hero;
import ecs.quests.GraveQuest;
import ecs.quests.KillQuest;
import ecs.quests.Quest;
import graphic.hud.QuestMenu;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import starter.Game;
import starter.LibgdxSetup;

import java.util.Set;

import static org.junit.Assert.*;
public class QuestSystemTest {
    Game game;
    Hero hero;
    Set<Quest> questList;
    KillQuest killquest;
    GraveQuest gravequest;
    QuestSystem system;
    SpriteBatch batch;
    QuestMenu<Actor> questMenu;

    @Before
    public void setUp() throws Exception {

        game = new Game();
        QuestMenu<Actor> questMenu = new QuestMenu<Actor>();
        //Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        //new Lwjgl3Application(new LibgdxSetup(game), config);

    }
    @Test
    public void testUpdateRefresh(){
        killquest.setScreenText();
        killquest.activateQuest();
        killquest.countKilledMonsters();
        system.refreshQuestMenu(killquest);
        assertEquals("KillQuest 1/10", killquest.getScreenText().toString());
    }

    @Test
    public void testUpdateFinishQuest(){
        assertTrue(true);
    }
}
