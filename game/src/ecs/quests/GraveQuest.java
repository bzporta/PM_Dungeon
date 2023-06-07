package ecs.quests;

import graphic.hud.QuestMenu;

/** Quest to activate a certain amount of graves */
public class GraveQuest extends Quest {

    private int activatedGraves;
    private int activatedGravesGoal;

    /** Constructor for GraveQuest
     * Sets the GravesGoal to 10 */
    public GraveQuest() {
        super("GraveQuest", "Activate 10 Graves", 10, "0/2");
        activatedGraves = 0;
        activatedGravesGoal = 10;
    }

    /** Activates the Quest
     *
     * If the quest isn't active or finished, setActive is set to true */
    @Override
    public void activateQuest() {
        QuestMenu qm = starter.Game.getQuestMenu();
        if (!isActive() && !isFinished()) {
            screenText = qm.addActiveQuest(super.getName(), getStatus());
            setActive(true);
        }
    }

    /** Counts the activated Graves
     *
     * Increases the activatedGraves by 1 and sets the status */
    public void countActivatedGraves() {
        this.activatedGraves++;
        System.out.println("Counting activated graves");
        System.out.println(activatedGraves);
        setStatus(activatedGraves, activatedGravesGoal);
    }

    /** Checks if the Quest is done
     *
     * @return true if activatedGraves equals activatedGravesGoal, else false */
    @Override
    public boolean checkIfDone() {
        return activatedGraves == activatedGravesGoal;
    }
}
