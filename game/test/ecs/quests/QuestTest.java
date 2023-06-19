package ecs.quests;

import org.junit.Before;
import org.junit.Test;
import starter.Game;

import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;

public class QuestTest {
    KillQuest killQuest;
    GraveQuest graveQuest;
    @Before
    public void setUp() throws Exception {
        killQuest = new KillQuest();
        graveQuest = new GraveQuest();
    }

    @Test
    public void testCountKilledMonsters(){
        int count = killQuest.getKilledMonsters();
        killQuest.countKilledMonsters();
        assertTrue(killQuest.getKilledMonsters() == count + 1);
    }

    @Test
    public void testActivatedGraves(){
        int count = graveQuest.getActivatedGraves();
        graveQuest.countActivatedGraves();
        assertTrue(graveQuest.getActivatedGraves() == count + 1);
    }

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

    @Test
    public void testQuestInList(){
        Game.getQuestList().add(killQuest);
        Game.getQuestList().add(graveQuest);

        assumeTrue(Game.getQuestList().contains(killQuest));
        assumeTrue(Game.getQuestList().contains(graveQuest));
    }


}
