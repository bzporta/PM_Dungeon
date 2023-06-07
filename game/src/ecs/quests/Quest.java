package ecs.quests;

import graphic.hud.QuestMenu;
import graphic.hud.ScreenText;
import java.io.Serializable;

public abstract class Quest implements Serializable {
    private String name;
    private String description;
    private int reward;
    private String status;

    private boolean finished = false;

    protected transient ScreenText screenText;

    protected boolean active;

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
        this.status = "" + current + "/" + goal;
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

    public void setScreenText() {
        QuestMenu qm = starter.Game.getQuestMenu();
        this.screenText = qm.addActiveQuest(getName(), getStatus());
        ;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public boolean isFinished() {
        return finished;
    }
}
