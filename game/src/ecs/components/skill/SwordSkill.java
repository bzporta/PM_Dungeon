package ecs.components.skill;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.collision.ICollide;
import ecs.damage.Damage;
import ecs.entities.Entity;
import graphic.Animation;
import starter.Game;
import tools.Point;

public class SwordSkill implements ISkillFunction {

    private final float projectileRange;
    private final Damage projectileDamage;
    private final ITargetSelection selectionFunction;
    public SwordSkill(Damage projectileDamage, ITargetSelection selectionFunction) {
        this.projectileRange = 1f;
        this.projectileDamage = projectileDamage;
        this.selectionFunction = selectionFunction;
    }
    @Override
    public void execute(Entity entity) {
    System.out.println("executed");
        Entity projectile = new Entity();
        PositionComponent epc =
                (PositionComponent)
                        entity.getComponent(PositionComponent.class)
                                .orElseThrow(
                                        () -> new MissingComponentException("PositionComponent"));
        new PositionComponent(projectile, epc.getPosition());
    ICollide collide =
        (a, b, from) -> {
          if (b != entity) {
            b.getComponent(HealthComponent.class)
                .ifPresent(
                    hc -> {
                      ((HealthComponent) hc).receiveHit(projectileDamage);
                      System.out.println("hit");
                      Game.removeEntity(projectile);
                    });
          }
        };
        new HitboxComponent(projectile, collide, null);
    }
}
