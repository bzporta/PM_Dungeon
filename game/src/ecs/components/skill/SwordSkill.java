package ecs.components.skill;

import ecs.components.HealthComponent;
import ecs.components.HitboxComponent;
import ecs.components.MissingComponentException;
import ecs.components.PositionComponent;
import ecs.components.collision.ICollide;
import ecs.damage.Damage;
import ecs.entities.Entity;
import ecs.entities.monster.Monster;
import starter.Game;
import tools.Point;

/** The SwordSkill class */
public class SwordSkill implements ISkillFunction {

    private final Damage projectileDamage;

    /**
     * Konstruktor für SwordSkill
     *
     * @param projectileDamage
     */
    public SwordSkill(Damage projectileDamage) {

        this.projectileDamage = projectileDamage;
    }

    /**
     * Führt die SwordSkill aus
     *
     * @param entity
     */
    @Override
    public void execute(Entity entity) {
        System.out.println("Sword_executed");
        Entity projectile = new Entity();
        PositionComponent epc =
                (PositionComponent)
                        entity.getComponent(PositionComponent.class)
                                .orElseThrow(
                                        () -> new MissingComponentException("PositionComponent"));
        new PositionComponent(projectile, epc.getPosition());
        ICollide collide =
                (a, b, from) -> {
                    if (b instanceof Monster) {
                        b.getComponent(HealthComponent.class)
                                .ifPresent(
                                        hc -> {
                                            ((Monster) b).knockback(1.1f);
                                            ((HealthComponent) hc).receiveHit(projectileDamage);
                                            System.out.println("hit");
                                            Game.removeEntity(projectile);
                                        });
                    }
                };

        new HitboxComponent(
                projectile, new Point(0.25f, 0.25f), new Point(0.34f, 0.75f), collide, null);
    }
}
