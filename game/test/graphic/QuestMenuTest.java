package graphic;

import com.badlogic.gdx.scenes.scene2d.Actor;
import ecs.quests.KillQuest;
import ecs.quests.Quest;
import graphic.hud.QuestMenu;
import graphic.hud.ScreenText;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class QuestMenuTest {

    QuestMenu<Actor> questMenu;
    static String screenText;
    static boolean visible;
    @Before
    public void setup(){
        questMenu = mock(QuestMenuStub.class);
        visible = false;
    }

    @Test
    public void testShowMenu(){
        doCallRealMethod().when(questMenu).showMenu();
        questMenu.showMenu();

        assertTrue(visible);
    }

    @Test
    public void testHideMenu(){
        doCallRealMethod().when(questMenu).hideMenu();
        doCallRealMethod().when(questMenu).showMenu();
        questMenu.showMenu();
        questMenu.hideMenu();

        assertFalse(visible);
    }

    @Test
    public void testAddActiveQuest(){
        doCallRealMethod().when(questMenu).addActiveQuest(anyString(), anyString());
        Quest quest = new KillQuest();
        questMenu.addActiveQuest(quest.getName(), quest.getStatus());

        assertEquals("KillQuest 0/10", screenText);
    }

    @Test
    public void testDecreaseQuestCounter(){
        doCallRealMethod().when(questMenu).decreaseQuestcounter();
        doCallRealMethod().when(questMenu).addActiveQuest(anyString(), anyString());
        questMenu.addActiveQuest("ab", "cd");
        questMenu.decreaseQuestcounter();
        assertEquals(0 ,questMenu.getQuestcounter());
    }

    public class QuestMenuStub<T extends Actor> extends QuestMenu{

        @Override
        public void hideMenu(){
            QuestMenuTest.visible = false;
        }

        @Override
        public void showMenu(){
            QuestMenuTest.visible = true;
        }

        @Override
        public ScreenText addActiveQuest(String name, String status){
            questcounter++;
            QuestMenuTest.screenText = name + " " + status;
            return null;
        }
    }

}


