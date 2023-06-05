package ecs.quests;

import ecs.entities.Hero;
import graphic.hud.QuestMenu;
import graphic.hud.ScreenText;
import starter.Game;

public abstract class Quest {
    private String name;
    private String description;
    private int reward;
    private String status;

    protected ScreenText screenText;

    private boolean active;

    public Quest(String name, String description, int reward, String status) {
        this.name = name;
        this.description = description;
        this.reward = reward;
        this.status = status;
    }

    public abstract void activateQuest();


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

    public void setStatus(int current, int goal) {
        this.status = ""+current + "/" + goal;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public ScreenText getScreenText() {
        return screenText;
    }
}
