package ecs.quests;

import graphic.hud.QuestMenu;

public class GraveQuest extends Quest {

    private int activatedGraves;
    private int activatedGravesGoal;

    public GraveQuest() {
        super("GraveQuest", "Activate 10 Graves", 10, "inactive");
        activatedGraves = 0;
        activatedGravesGoal = 2;
    }

    @Override
    public void activateQuest() {
    if (!isActive()) {
      QuestMenu qm = starter.Game.getQuestMenu();
      qm.addActiveQuest(super.getName(), activatedGraves, activatedGravesGoal);
      setActive(true);
        }
    }

    public int getActivatedGraves() {
        return activatedGraves;
    }

    public void setActivatedGraves(int activatedGraves) {
        this.activatedGraves = activatedGraves;
    }

    @Override
    public boolean checkIfDone(){
        System.out.println("Done?");
        System.out.println(activatedGraves == activatedGravesGoal);
        return activatedGraves == activatedGravesGoal;
    }
}
