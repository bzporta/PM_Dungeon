package ecs.quests;

import graphic.hud.QuestMenu;

public class KillQuest extends Quest {
    private int killedMonsters;
    private int killedMonstersGoal;

    public KillQuest() {
        super("KillQuest", "Kill 10 Monsters", 10, "inactive");
        this.killedMonsters = 0;
        this.killedMonstersGoal = 10;
    }

    @Override
    public void activateQuest() {
        if (!super.isActive()) {
            QuestMenu qm = starter.Game.getQuestMenu();
            qm.addActiveQuest(super.getName(), killedMonsters, killedMonstersGoal);
            super.setActive(true);
        }
    }

    public int getKilledMonsters() {
        return killedMonsters;
    }

    public void setKilledMonsters(int killedMonsters) {
        this.killedMonsters = killedMonsters;
    }

    @Override
    public boolean checkIfDone(){
        return killedMonsters == killedMonstersGoal;
    }
}
