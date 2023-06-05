package ecs.quests;

import ecs.entities.Hero;
import graphic.hud.QuestMenu;
import starter.Game;

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
    if (!isActive()) {
      QuestMenu qm = starter.Game.getQuestMenu();
      screenText = qm.addActiveQuest(super.getName(), getStatus());
      setActive(true);
        }
    }

    public int getActivatedGraves() {
        return activatedGraves;
    }

    public void countActivatedGraves() {
        this.activatedGraves++;
        setStatus(activatedGraves,activatedGravesGoal);
    }

    @Override
    public boolean checkIfDone(){
        return activatedGraves == activatedGravesGoal;
    }

}
