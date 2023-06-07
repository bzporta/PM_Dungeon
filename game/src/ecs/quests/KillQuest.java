package ecs.quests;

import graphic.hud.QuestMenu;

public class KillQuest extends Quest {
    private int killedMonsters;
    private int killedMonstersGoal;

    public KillQuest() {
        super("KillQuest", "Kill 10 Monsters", 10, "0/2");
        this.killedMonsters = 0;
        this.killedMonstersGoal = 2;
    }

    @Override
    public void activateQuest() {
        QuestMenu qm = starter.Game.getQuestMenu();
        if (!isActive() && !isFinished()) {
            screenText = qm.addActiveQuest(super.getName(), getStatus());
            setActive(true);
        }
    }

    public int getKilledMonsters() {
        return killedMonsters;
    }

    public void countKilledMonsters() {
        this.killedMonsters++;
        setStatus(killedMonsters, killedMonstersGoal);
    }

    @Override
    public boolean checkIfDone() {
        return killedMonsters == killedMonstersGoal;
    }
}
