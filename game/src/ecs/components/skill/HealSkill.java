package ecs.components.skill;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.collision.ICollide;
import ecs.entities.Entity;
import ecs.entities.Hero;
import graphic.Animation;
import starter.Game;
import tools.Point;

public class HealSkill implements ISkillFunction{

    private String pathToTexturesOfHeal;
    private int healAmount;

    public HealSkill(int healAmount){
        pathToTexturesOfHeal = "skills.heal";
        this.healAmount = healAmount;
    }

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
        new AnimationComponent(healshield,animation);


        Hero hero = (Hero) Game.getHero().orElseThrow();
        hero.getHC().setCurrentHealthpoints(
            hero.getHC().getCurrentHealthpoints() + healAmount
        );

        Point velocity =
            SkillTools.calculateVelocity(epc.getPosition(), hero.getPosition(), 0f);
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
}
