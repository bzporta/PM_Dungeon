package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.AnimationComponent;
import ecs.components.HitboxComponent;
import ecs.components.PositionComponent;
import graphic.Animation;

public abstract class Trap extends Entity{

    private int damage;
    private String pathToSkin;
    Lever lever;

    public Trap(){
        new HitboxComponent(this);
        new PositionComponent(this);


    }

    public void setupAnimation(String path){
        new AnimationComponent(this,AnimationBuilder.buildAnimation(path));
    }



}
