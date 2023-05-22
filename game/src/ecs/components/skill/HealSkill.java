package ecs.components.skill;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.entities.Entity;
import ecs.entities.Hero;
import graphic.Animation;
import starter.Game;
import tools.Point;

/** HealSkill is a skill that heals the hero. */
public class HealSkill implements ISkillFunction {

    private String pathToTexturesOfHeal;
    private int healAmount;

    /**
     * Constructor of HealSkill.
     *
     * @param healAmount Amount of healthpoints that the hero gets healed.
     */
    public HealSkill(int healAmount) {
        pathToTexturesOfHeal = "skills.heal";
        this.healAmount = healAmount;
    }

    /**
     * Executes the skill.
     *
     * @param entity Entity that executes the skill.
     */
    @Override
    public void execute(Entity entity) {
        Entity healshield = new Entity();
        Game.addEntity(healshield);

        PositionComponent epc =
                (PositionComponent)
                        entity.getComponent(PositionComponent.class)
                                .orElseThrow(
                                        () -> new MissingComponentException("PositionComponent"));
        new PositionComponent(healshield, epc.getPosition());

        Animation animation = AnimationBuilder.buildAnimation(pathToTexturesOfHeal);
        new AnimationComponent(healshield, animation);

        Hero hero = (Hero) Game.getHero().orElseThrow();
        hero.getHC().setCurrentHealthpoints(hero.getHC().getCurrentHealthpoints() + healAmount);

        Point velocity = SkillTools.calculateVelocity(epc.getPosition(), hero.getPosition(), 0f);
        VelocityComponent vc =
                new VelocityComponent(healshield, velocity.x, velocity.y, animation, animation);
        new ProjectileComponent(healshield, epc.getPosition(), hero.getPosition());

        /*
        ICollide collide =
            (a, b, from) -> {
                if (b == entity) {

                    Game.removeEntity(healshield);
                }
            };

        new HitboxComponent(healshield, collide, null);

         */

    }

    /**
     * Getter for the amount of healthpoints that the hero gets healed.
     *
     * @return Amount of healthpoints that the hero gets healed.
     */
    public int getHealAmount() {
        return healAmount;
    }

    /**
     * Setter for the amount of healthpoints that the hero gets healed.
     *
     * @param healAmount Amount of healthpoints that the hero gets healed.
     */
    public void setHealAmount(int healAmount) {
        this.healAmount = healAmount;
    }
}
