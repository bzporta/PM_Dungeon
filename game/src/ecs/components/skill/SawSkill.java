package ecs.components.skill;

import dslToGame.AnimationBuilder;
import ecs.components.AnimationComponent;
import ecs.components.HealthComponent;
import ecs.components.HitboxComponent;
import ecs.components.MissingComponentException;
import ecs.components.PositionComponent;
import ecs.components.VelocityComponent;
import ecs.components.collision.ICollide;
import ecs.damage.Damage;
import ecs.entities.Entity;
import ecs.entities.monster.Monster;
import graphic.Animation;
import java.util.logging.Logger;
import starter.Game;
import tools.Point;

/** The SawSkill class */
public class SawSkill implements ISkillFunction {

    private final String pathToTexturesOfProjectile;
    private final float projectileSpeed;

    private final float projectileRange;
    private final Damage projectileDamage;
    private final ITargetSelection selectionFunction;
    private transient Logger logger;

    /**
     * Konstruktor für SawSkill
     *
     * @param selectionFunction
     * @param projectileDamage
     */
    public SawSkill(ITargetSelection selectionFunction, Damage projectileDamage) {
        logger = Logger.getLogger(getClass().getName());
        this.pathToTexturesOfProjectile = "skills.saw";
        this.projectileDamage = projectileDamage;
        this.projectileSpeed = 0.3f;
        this.projectileRange = 5f;
        this.selectionFunction = selectionFunction;
    }

    /**
     * Führt die SawSkill aus
     *
     * @param entity
     */
    @Override
    public void execute(Entity entity) {
        System.out.println("Saw_executed");
        Entity projectile = new Entity();
        PositionComponent epc =
                (PositionComponent)
                        entity.getComponent(PositionComponent.class)
                                .orElseThrow(
                                        () -> new MissingComponentException("PositionComponent"));
        new PositionComponent(projectile, epc.getPosition());

        Animation animation = AnimationBuilder.buildAnimation(pathToTexturesOfProjectile);
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
                    if (b instanceof Monster) {
                        b.getComponent(HealthComponent.class)
                                .ifPresent(
                                        hc -> {
                                            ((Monster) b).knockback(1.1f);
                                            ((HealthComponent) hc).receiveHit(projectileDamage);
                                            logger.info(
                                                    "Monster "
                                                            + b.getClass().getSimpleName()
                                                            + " got hit by saw");
                                            Game.removeEntity(projectile);
                                        });
                    }
                };

        new HitboxComponent(projectile, collide, null);
    }
}
