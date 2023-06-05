package ecs.quests;

import graphic.hud.QuestMenu;

public abstract class Quest {
    private String name;
    private String description;
    private int reward;
    private String status;

    private boolean active;

    public Quest(String name, String description, int reward, String status) {
        this.name = name;
        this.description = description;
        this.reward = reward;
        this.status = status;
    }

    public void activateQuest() {
        if (!active) {
            QuestMenu qm = starter.Game.getQuestMenu();
            qm.addActiveQuest(this.name, 0, 10);
            active = true;
        }
    }

    public abstract boolean checkIfDone();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
