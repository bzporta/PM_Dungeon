import ecs.entities.monster.MonsterTest;
import ecs.entities.trap.TrapTest;
import ecs.quests.QuestTest;
import ecs.systems.QuestSystemTest;
import graphic.QuestMenuTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
        // Hier kommen alle Testklassen rein
        MonsterTest.class,
        TrapTest.class,
        QuestTest.class,
        QuestMenuTest.class,
        QuestSystemTest.class
})

public class AufgabeTestSuit {
    // bleibt leer!!!
}