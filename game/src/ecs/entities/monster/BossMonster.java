package ecs.entities.monster;

import ecs.components.HealthComponent;
import ecs.components.ai.AIComponent;
import ecs.components.ai.fight.MeleeAI;
import ecs.components.ai.fight.RangeAI;
import ecs.components.ai.idle.BossWalk;
import ecs.components.ai.transition.RangeTransition;
import ecs.components.skill.*;
import ecs.damage.Damage;
import ecs.damage.DamageType;
import starter.Game;

public class BossMonster extends Monster {

    private HealthComponent hp;
    private AIComponent ai;
    private SkillComponent skillComponent;
    private FireballSkill fireballSkill;
    private SwordSkill swordSkill;
    private Skill firstSkill;
    private Skill secondSkill;
    private boolean phase2activated = false;

    /** Creates a new BossMonster. */
    public BossMonster() {
        super(
                0.10f,
                0.10F,
                20,
                "game/assets/character/monster/boss/idleLeft.png",
                "game/assets/character/monster/boss/idleRight.png",
                "game/assets/character/monster/boss/idleLeft.png",
                "game/assets/character/monster/boss/idleRight.png");
        setupHealthComponent();
        setupAiComponent();
        setupSkillComponent();
        setPosition(Game.currentLevel.getEndTile().getCoordinateAsPoint());
        phase1();
    }

    /** Sets up the health component of the monster. */
    @Override
    public void setupHealthComponent() {
        hp = new HealthComponent(this);
        hp.setMaximalHealthpoints(250);
        hp.setCurrentHealthpoints(250);
    }

    private void setupAiComponent() {
        ai = new AIComponent(this);
        ai.setTransitionAI(new RangeTransition(5.0f));
        ai.setIdleAI(new BossWalk(2));
    }

    private void setupSkillComponent() {
        skillComponent = new SkillComponent(this);
    }

    private void phase1() {
        swordSkill = new SwordSkill(new Damage(10, DamageType.PHYSICAL, null));
        firstSkill = new Skill(swordSkill, 1);
        skillComponent.addSkill(firstSkill);
        ai.setFightAI(new MeleeAI(1.0f, firstSkill));
    }

    /** Activates the second phase of the boss fight. */
    public void phase2() {
        if (!phase2activated) {

            fireballSkill = new FireballSkill(SkillTools::getHeroPositionAsPoint);
            secondSkill = new Skill(fireballSkill, 1);
            skillComponent.addSkill(secondSkill);
            ai.setFightAI(new RangeAI(5.0f, secondSkill));
        }
        phase2activated = true;
    }

    /**
     * Applies knockback to the monster
     *
     * @param knockbackamount
     */
    @Override
    public void knockback(float knockbackamount) {}
}
