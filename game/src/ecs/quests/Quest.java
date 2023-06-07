package ecs.quests;

import graphic.hud.QuestMenu;
import graphic.hud.ScreenText;
import java.io.Serializable;

/** Quest class */
public abstract class Quest implements Serializable {
    private String name;
    private String description;
    private int reward;
    private String status;

    private boolean finished = false;

    protected transient ScreenText screenText;

    protected boolean active;

    /**
     * Creates a new Quest
     *
     * @param name Name of the quest
     * @param description Description of the quest
     * @param reward Reward of the quest
     * @param status Status of the quest
     */
    public Quest(String name, String description, int reward, String status) {
        this.name = name;
        this.description = description;
        this.reward = reward;
        this.status = status;
    }

    public abstract void activateQuest();

    public abstract boolean checkIfDone();

    /**
     * Returns the name of the quest
     *
     * @return name of the quest
     */
    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    /**
     * Sets the status of the quest
     *
     * @param current Amount of current progress of the quest
     * @param goal Amount of goal progress of the quest
     */
    public void setStatus(int current, int goal) {
        this.status = "" + current + "/" + goal;
    }

    /**
     * Returns true if the quest is active
     *
     * @return true if the quest is active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets the quest to active
     *
     * @param active true if the quest is active
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Returns the ScreenText of the quest
     *
     * @return ScreenText of the quest
     */
    public ScreenText getScreenText() {
        return screenText;
    }

    /** Sets the ScreenText of the quest to its name and status */
    public void setScreenText() {
        QuestMenu qm = starter.Game.getQuestMenu();
        this.screenText = qm.addActiveQuest(getName(), getStatus());
        ;
    }

    /**
     * Sets finished to true or false
     *
     * @param finished True if the quest is finished, false if not
     */
    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    /**
     * Returns true if the quest is finished
     *
     * @return True if the quest is finished
     */
    public boolean isFinished() {
        return finished;
    }
}
