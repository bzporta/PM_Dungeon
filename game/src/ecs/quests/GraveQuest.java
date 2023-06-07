package ecs.quests;

import graphic.hud.QuestMenu;

public class GraveQuest extends Quest {

    private int activatedGraves;
    private int activatedGravesGoal;

    public GraveQuest() {
        super("GraveQuest", "Activate 10 Graves", 10, "0/2");
        activatedGraves = 0;
        activatedGravesGoal = 2;
    }

    @Override
    public void activateQuest() {
        QuestMenu qm = starter.Game.getQuestMenu();
        if (!isActive() && !isFinished()) {
            screenText = qm.addActiveQuest(super.getName(), getStatus());
            setActive(true);
        }
    }

    public int getActivatedGraves() {
        return activatedGraves;
    }

    public void countActivatedGraves() {
        this.activatedGraves++;
        System.out.println("Counting activated graves");
        System.out.println(activatedGraves);
        setStatus(activatedGraves, activatedGravesGoal);
    }

    @Override
    public boolean checkIfDone() {
        return activatedGraves == activatedGravesGoal;
    }
}
