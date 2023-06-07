package ecs.quests;

import graphic.hud.QuestMenu;
import java.util.logging.Logger;

/** A KillQuest is a Quest that requires the player to kill a certain amount of Monsters */
public class KillQuest extends Quest {
    private int killedMonsters;
    private int killedMonstersGoal;
    private static transient Logger logger;

    /**
     * Constructor for a KillQuest
     *
     * <p>The Player has to kill 10 Monsters to complete the quest
     */
    public KillQuest() {
        super("KillQuest", "Kill 10 Monsters", 10, "0/10");
        this.killedMonsters = 0;
        this.killedMonstersGoal = 10;
    }

    /**
     * Activates the Quest
     *
     * <p>Adds the Quest to the QuestMenu
     */
    @Override
    public void activateQuest() {
        logger = Logger.getLogger(KillQuest.class.getName());
        QuestMenu qm = starter.Game.getQuestMenu();
        if (!isActive() && !isFinished()) {
            screenText = qm.addActiveQuest(super.getName(), getStatus());
            setActive(true);
            logger.info("GraveQuest activated");
        }
        logger.info("GraveQuest already active or finished");
    }

    /**
     * Increases the amount of killed Monsters by 1
     *
     * <p>Sets the status of the Quest to the current amount of killed Monsters, if the Quest is
     * active
     */
    public void countKilledMonsters() {
        this.killedMonsters++;
        setStatus(killedMonsters, killedMonstersGoal);
    }

    /**
     * Checks if the quest is done
     *
     * @return true if the amount of killed Monsters is equal to the goal of killed Monsters
     */
    @Override
    public boolean checkIfDone() {
        return killedMonsters == killedMonstersGoal;
    }
}
