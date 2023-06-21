package graphic;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.badlogic.gdx.scenes.scene2d.Actor;
import ecs.quests.KillQuest;
import ecs.quests.Quest;
import graphic.hud.QuestMenu;
import graphic.hud.ScreenText;
import org.junit.Before;
import org.junit.Test;

/** Testclass for the class "QuestMenu" */
public class QuestMenuTest {

    QuestMenu<Actor> questMenu;
    static String screenText;
    static boolean visible;

    /** Setup for the tests A Questmenu is created and its visibility is set to "false" */
    @Before
    public void setup() {
        questMenu = mock(QuestMenuStub.class);
        visible = false;
    }

    /**
     * Test for the method "showMenu" The method is called and the visibility is set to "true" The
     * test is passed if the visibility is "true"
     */
    @Test
    public void testShowMenu() {
        doCallRealMethod().when(questMenu).showMenu();
        questMenu.showMenu();

        assertTrue(visible);
    }

    /**
     * Test for the method "hideMenu" The method is called and the visibility is set to "false" The
     * test is passed if the visibility is "false"
     */
    @Test
    public void testHideMenu() {
        doCallRealMethod().when(questMenu).hideMenu();
        doCallRealMethod().when(questMenu).showMenu();
        questMenu.showMenu();
        questMenu.hideMenu();

        assertFalse(visible);
    }

    /**
     * Test for the method "addActiveQuest" The method is called and the screenText is set to the
     * name and status of the quest The test is passed if the screenText is equal to the name and
     * status of the quest
     */
    @Test
    public void testAddActiveQuest() {
        doCallRealMethod().when(questMenu).addActiveQuest(anyString(), anyString());
        Quest quest = new KillQuest();
        questMenu.addActiveQuest(quest.getName(), quest.getStatus());

        assertEquals("KillQuest 0/10", screenText);
    }

    /**
     * Test for the method "decreaseQuestcounter" The method is called and the questcounter is
     * decreased by 1 The test is passed if the questcounter is equal to 0
     */
    @Test
    public void testDecreaseQuestCounter() {
        doCallRealMethod().when(questMenu).decreaseQuestcounter();
        doCallRealMethod().when(questMenu).addActiveQuest(anyString(), anyString());
        questMenu.addActiveQuest("ab", "cd");
        questMenu.decreaseQuestcounter();
        assertEquals(0, questMenu.getQuestcounter());
    }

    /** Stub for the class "QuestMenu" */
    public class QuestMenuStub<T extends Actor> extends QuestMenu {

        /** Hides the QuestMenu */
        @Override
        public void hideMenu() {
            QuestMenuTest.visible = false;
        }

        /** Shows the QuestMenu */
        @Override
        public void showMenu() {
            QuestMenuTest.visible = true;
        }

        /**
         * Adds an active quest to the QuestMenu
         *
         * @param name name of the quest
         * @param status status of the quest
         * @return null
         */
        @Override
        public ScreenText addActiveQuest(String name, String status) {
            questcounter++;
            QuestMenuTest.screenText = name + " " + status;
            return null;
        }
    }
}
