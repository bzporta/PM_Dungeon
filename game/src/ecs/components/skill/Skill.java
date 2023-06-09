package ecs.components.skill;

import ecs.entities.Entity;
import java.io.Serializable;
import tools.Constants;

/** Skill class */
public class Skill implements Serializable {

    private ISkillFunction skillFunction;
    private int coolDownInFrames;
    private int currentCoolDownInFrames;

    /**
     * @param skillFunction Function of this skill
     */
    public Skill(ISkillFunction skillFunction, float coolDownInSeconds) {
        this.skillFunction = skillFunction;
        this.coolDownInFrames = (int) (coolDownInSeconds * Constants.FRAME_RATE);
        this.currentCoolDownInFrames = 0;
    }

    /**
     * Execute the method of this skill
     *
     * @param entity entity which uses the skill
     */
    public void execute(Entity entity) {
        if (!isOnCoolDown()) {
            System.out.println("Execute");
            skillFunction.execute(entity);
            activateCoolDown();
        }
        reduceCoolDown();
    }

    /**
     * @return true if cool down is not 0, else false
     */
    public boolean isOnCoolDown() {
        return currentCoolDownInFrames > 0;
    }

    /** activate cool down */
    public void activateCoolDown() {
        currentCoolDownInFrames = coolDownInFrames;
    }

    /** reduces the current cool down by frame */
    public void reduceCoolDown() {
        currentCoolDownInFrames = Math.max(0, --currentCoolDownInFrames);
    }

    /**
     * @return the skill function of this skill
     */
    public ISkillFunction getSkillFunction() {
        return skillFunction;
    }
}
