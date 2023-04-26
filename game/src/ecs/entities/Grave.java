package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.AnimationComponent;
import ecs.components.InteractionComponent;
import ecs.components.PositionComponent;
import ecs.damage.Damage;
import ecs.damage.DamageType;

import java.util.Random;

public class Grave extends Entity{

    private PositionComponent pc;
    private String pathToSkin = "dungeon/grave/grave.png";
    private Hero hero;

    public Grave(Hero hero){
        new InteractionComponent(this, 1f, false, this::event);
        pc = new PositionComponent(this);
        setupAnimation(pathToSkin);
        this.hero = hero;
    }

    private void setupAnimation(String path){
        new AnimationComponent(this, AnimationBuilder.buildAnimation(path));
    }

    public void setGrave(tools.Point pt){
        pc.setPosition(pt);
        setupAnimation(pathToSkin);
    }

    private void event(Entity entity){
        Random random = new Random();
        if (random.nextDouble() <= 0.7){
            hero.getHC().setCurrentHealthpoints(
                hero.getHC().getCurrentHealthpoints() + 20
            );
        } else {
            hero.getHC().receiveHit(new Damage(20, DamageType.MAGIC,null));
        }
        setupAnimation("dungeon/grave/graveused.png");
    }
}
