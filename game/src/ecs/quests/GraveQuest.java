package ecs.quests;

import graphic.hud.QuestMenu;
import java.util.logging.Logger;

/** Quest to activate a certain amount of graves */
public class GraveQuest extends Quest {

    private int activatedGraves;
    private int activatedGravesGoal;
    private static transient Logger logger;

    /** Constructor for GraveQuest Sets the GravesGoal to 10 */
    public GraveQuest() {
        super("GraveQuest", "Activate 10 Graves", 10, "0/10");
        activatedGraves = 0;
        activatedGravesGoal = 10;
    }

    /**
     * Activates the Quest
     *
     * <p>If the quest isn't active or finished, setActive is set to true
     */
    @Override
    public void activateQuest() {
        logger = Logger.getLogger(GraveQuest.class.getName());
        QuestMenu qm = starter.Game.getQuestMenu();
        if (!isActive() && !isFinished()) {
            screenText = qm.addActiveQuest(super.getName(), getStatus());
            setActive(true);
            logger.info("GraveQuest activated");
        }
        logger.info("GraveQuest already active or finished");
    }

    /**
     * Counts the activated Graves
     *
     * <p>Increases the activatedGraves by 1 and sets the status
     */
    public void countActivatedGraves() {
        this.activatedGraves++;
        setStatus(activatedGraves, activatedGravesGoal);
    }

    /**
     * Checks if the Quest is done
     *
     * @return true if activatedGraves equals activatedGravesGoal, else false
     */
    @Override
    public boolean checkIfDone() {
        return activatedGraves == activatedGravesGoal;
    }
}
