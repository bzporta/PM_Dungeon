package ecs.components.skill;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.collision.ICollide;
import ecs.entities.Entity;
import ecs.entities.Hero;
import ecs.entities.monster.Monster;
import graphic.Animation;
import starter.Game;
import tools.Point;

/**
 * IceballSkill is a skill that shoots an iceball.
 */
public class IceballSkill implements ISkillFunction {

    private String pathToTexturesOfIceball;
    private float projectileSpeed;

    private float projectileRange;
    private float speedpenalty;
    private Point projectileHitboxSize;
    private int spellCost;

    private ITargetSelection selectionFunction;

    /**
     * Constructor of IceballSkill.
     * @param selectionFunction Function that selects the target point.
     */
    public IceballSkill(ITargetSelection selectionFunction, float speedpenalty, int spellCost) {
        this.pathToTexturesOfIceball = "skills.iceball";
        this.speedpenalty = speedpenalty;
        this.projectileSpeed = 0.3f;
        this.projectileRange = 5f;
        this.projectileHitboxSize = new Point(10, 10);
        this.selectionFunction = selectionFunction;
        this.spellCost = spellCost;
    }

    /**
     * Executes the skill.
     * @param entity Entity that executes the skill.
     */
    @Override
    public void execute(Entity entity) {
        Hero hero = (Hero) Game.getHero().orElseThrow();
        hero.getHC().setCurrentHealthpoints(
            hero.getHC().getCurrentHealthpoints() - spellCost
        );

        Entity projectile = new Entity();
        PositionComponent epc =
            (PositionComponent)
                entity.getComponent(PositionComponent.class)
                    .orElseThrow(
                        () -> new MissingComponentException("PositionComponent"));
        new PositionComponent(projectile, epc.getPosition());

        Animation animation = AnimationBuilder.buildAnimation(pathToTexturesOfIceball);
        new AnimationComponent(projectile, animation);

        Point aimedOn = selectionFunction.selectTargetPoint();
        Point targetPoint =
            SkillTools.calculateLastPositionInRange(
                epc.getPosition(), aimedOn, projectileRange);
        Point velocity =
            SkillTools.calculateVelocity(epc.getPosition(), targetPoint, projectileSpeed);
        VelocityComponent vc =
            new VelocityComponent(projectile, velocity.x, velocity.y, animation, animation);
        new ProjectileComponent(projectile, epc.getPosition(), targetPoint);
        ICollide collide =
            (a, b, from) -> {
                if (b instanceof Monster ) {
                    b.getComponent(VelocityComponent.class)
                        .ifPresent(
                            vec -> {
                                if ((((VelocityComponent) vec).getXVelocity() - speedpenalty) > 0.0f){
                                    ((VelocityComponent) vec).setXVelocity(
                                        (((VelocityComponent) vec).getXVelocity() - speedpenalty)
                                    );
                                    ((VelocityComponent) vec).setYVelocity(
                                        (((VelocityComponent) vec).getYVelocity() - speedpenalty)
                                    );
                                } else {
                                    ((Monster) b).setFrozen();
                                }

                                Game.removeEntity(projectile);
                            });
                }
            };

        new HitboxComponent(
            projectile, new Point(0.1f, 0.1f), projectileHitboxSize, collide, null);
    }

    /**
     * Gets the spell cost.
     * @return spellCost
     */
    public int getSpellCost(){
        return spellCost;
    }

    /**
     * Sets the spell cost.
     * @param spellCost
     */
    public void setSpellCost(int spellCost){
        this.spellCost = spellCost;
    }

    /**
     * Gets the speed penalty.
     * @return speedpenalty
     */
    public float getSpeedpenalty() {
        return speedpenalty;
    }

    /**
     * Sets the speed penalty.
     * @param speedpenalty
     */
    public void setSpeedpenalty(float speedpenalty) {
        this.speedpenalty = speedpenalty;
    }

}
