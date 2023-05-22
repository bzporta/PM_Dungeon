package ecs.components.skill;

import dslToGame.AnimationBuilder;
// import ecs.components.;
import ecs.components.*;
import ecs.components.collision.ICollide;
import ecs.damage.Damage;
import ecs.entities.Entity;
import ecs.entities.Hero;
import graphic.Animation;
import starter.Game;
import tools.Point;

public class BoomerangSkill extends Entity implements ISkillFunction {
    private final String pathToTexturesOfProjectile;
    private final float projectileSpeed;

    private final float projectileRange;
    private final Damage projectileDamage;
    private final Point projectileHitboxSize;
    private final ITargetSelection selectionFunction;
    public String name = "Boomerang";

    /**
     * Konstruktor für BoomerangSkill
     *
     * @param selectionFunction
     * @param projectileDamage
     */
    public BoomerangSkill(ITargetSelection selectionFunction, Damage projectileDamage) {
        this.pathToTexturesOfProjectile = "skills.boomerang";
        this.projectileDamage = projectileDamage;
        this.projectileSpeed = 0.3f;
        this.projectileRange = 5f;
        this.projectileHitboxSize = new Point(10, 10);
        this.selectionFunction = selectionFunction;
    }

    @Override
    public void execute(Entity entity) {
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
                    if (b != entity) {
                        b.getComponent(HealthComponent.class)
                                .ifPresent(
                                        hc -> {
                                            Game.removeEntity(projectile);
                                            Entity projectile_new = new Entity();
                                            PositionComponent epc_new =
                                                    (PositionComponent)
                                                            b.getComponent(PositionComponent.class)
                                                                    .orElseThrow(
                                                                            () ->
                                                                                    new MissingComponentException(
                                                                                            "PositionComponent"));
                                            new PositionComponent(
                                                    projectile_new, epc_new.getPosition());

                                            Animation animation_new =
                                                    AnimationBuilder.buildAnimation(
                                                            pathToTexturesOfProjectile);
                                            new AnimationComponent(projectile_new, animation_new);
                                            ((HealthComponent) hc).receiveHit(projectileDamage);
                                            Hero hero = (Hero) Game.getHero().orElseThrow();
                                            Point targetPoint_new = hero.getPosition();

                                            Point velocity_new =
                                                    SkillTools.calculateVelocity(
                                                            epc_new.getPosition(),
                                                            targetPoint_new,
                                                            projectileSpeed);
                                            VelocityComponent vc_new =
                                                    new VelocityComponent(
                                                            projectile_new,
                                                            velocity_new.x,
                                                            velocity_new.y,
                                                            animation_new,
                                                            animation_new);
                                            new ProjectileComponent(
                                                    projectile_new,
                                                    epc_new.getPosition(),
                                                    targetPoint_new);
                                        });
                    }
                };

        new HitboxComponent(projectile, collide, null);
    }
}
