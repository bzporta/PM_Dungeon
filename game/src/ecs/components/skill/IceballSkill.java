package ecs.components.skill;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.collision.ICollide;
import ecs.entities.Entity;
import ecs.entities.Hero;
import graphic.Animation;
import starter.Game;
import tools.Point;

public class IceballSkill implements ISkillFunction {

    private String pathToTexturesOfIceball;
    private float projectileSpeed;

    private float projectileRange;
    private float speedpenalty;
    private Point projectileHitboxSize;
    private int spellCost;

    private ITargetSelection selectionFunction;

    public IceballSkill(ITargetSelection selectionFunction) {
        this.pathToTexturesOfIceball = "skills.iceball";
        this.speedpenalty = 0.05f;
        this.projectileSpeed = 0.3f;
        this.projectileRange = 5f;
        this.projectileHitboxSize = new Point(10, 10);
        this.selectionFunction = selectionFunction;
        this.spellCost = 10;
    }
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
                if (b != entity) {
                    b.getComponent(VelocityComponent.class)
                        .ifPresent(
                            vec -> {
                                ((VelocityComponent) vec).setXVelocity(
                                    ((VelocityComponent) vec).getXVelocity() - speedpenalty
                                );
                                ((VelocityComponent) vec).setYVelocity(
                                    ((VelocityComponent) vec).getYVelocity() - speedpenalty
                                );
                                Game.removeEntity(projectile);
                            });
                }
            };

        new HitboxComponent(
            projectile, new Point(0.25f, 0.25f), projectileHitboxSize, collide, null);
    }
}
