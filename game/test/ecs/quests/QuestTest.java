package ecs.quests;

import org.junit.Before;
import org.junit.Test;
import starter.Game;

import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;

/** Testclass for the classes "KillQuest" and "GraveQuest" */
public class QuestTest {
    KillQuest killQuest;
    GraveQuest graveQuest;

    /** Setup for the tests
     *
     * A killQuest and a graveQuest are created
     */
    @Before
    public void setUp() throws Exception {
        killQuest = new KillQuest();
        graveQuest = new GraveQuest();
    }

    /** Test for the method "countKilledMonsters" in the class "KillQuest"
     *
     * Testcase: The amount of killed monsters is counted
     */
    @Test
    public void testCountKilledMonsters(){
        int count = killQuest.getKilledMonsters();
        killQuest.countKilledMonsters();
        assertTrue(killQuest.getKilledMonsters() == count + 1);
    }

    /** Test for the method "countActivatedGraves" in the class "GraveQuest"
     *
     * Testcase: The amount of activated graves is counted
     */
    @Test
    public void testActivatedGraves(){
        int count = graveQuest.getActivatedGraves();
        graveQuest.countActivatedGraves();
        assertTrue(graveQuest.getActivatedGraves() == count + 1);
    }

    /** Test for the method "checkIfDone" in the classes "KillQuest" and "GraveQuest"
     *
     * Testcase: The quest is done (The amount of killed monsters/activated graves is 10)
     */
    @Test
    public void testCheckIfDone() {
        for (int i = 0; i < 10; i++) {
            killQuest.countKilledMonsters();
        }
        assumeTrue(killQuest.checkIfDone());

        for (int i = 0; i < 10; i++) {
            graveQuest.countActivatedGraves();
        }
        assertTrue(graveQuest.checkIfDone());
    }

    /** Tests if the quests are in the questList
     *
     * Testcase: The quests are in the questList
     */
    @Test
    public void testQuestInList(){
        Game.getQuestList().add(killQuest);
        Game.getQuestList().add(graveQuest);

        assumeTrue(Game.getQuestList().contains(killQuest));
        assumeTrue(Game.getQuestList().contains(graveQuest));
    }


}
