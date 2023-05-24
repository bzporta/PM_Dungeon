package ecs.components.skill;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.collision.ICollide;
import ecs.damage.Damage;
import ecs.entities.Entity;
import ecs.entities.Hero;
import ecs.entities.monster.Monster;
import graphic.Animation;
import starter.Game;
import tools.Point;

/** The SwordSkill class */
public class SwordSkill implements ISkillFunction {

    private final Damage projectileDamage;
    private String pathToAnimation;

    /**
     * Konstruktor für SwordSkill
     *
     * @param projectileDamage
     */
    public SwordSkill(Damage projectileDamage) {

        this.projectileDamage = projectileDamage;
        this.pathToAnimation = "skills.sword";
    }

    /**
     * Führt die SwordSkill aus
     *
     * @param entity
     */
    @Override
    public void execute(Entity entity) {
        Hero hero = (Hero) Game.getHero().orElseThrow();
        System.out.println("Sword_executed");
        Entity projectile = new Entity();
        Animation animation = AnimationBuilder.buildAnimation(pathToAnimation);
        new AnimationComponent(projectile, animation);
        PositionComponent epc =
                (PositionComponent)
                        entity.getComponent(PositionComponent.class)
                                .orElseThrow(
                                        () -> new MissingComponentException("PositionComponent"));


        new PositionComponent(projectile, new Point(epc.getPosition().x, epc.getPosition().y + 0.4f));

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
                    if (b instanceof Hero) {
                        Game.removeEntity(projectile);
                    }
                };

        new HitboxComponent(
                projectile, new Point(0.25f, 0.25f), new Point(0.34f, 0.75f), collide, null);


    }
}
