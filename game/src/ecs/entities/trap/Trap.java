package ecs.entities.trap;

import dslToGame.AnimationBuilder;
import ecs.components.AnimationComponent;
import ecs.components.HitboxComponent;
import ecs.components.PositionComponent;
import ecs.entities.Entity;
import ecs.entities.trap.Lever;

import java.awt.*;
import java.util.Random;


public abstract class Trap extends Entity {


    private Lever lever;
    private PositionComponent pc;
    private HitboxComponent hb;


    public Trap(){
        hb = new HitboxComponent(this);
        pc = new PositionComponent(this);
        Random rand = new Random();
        if (rand.nextDouble() < 0.5){
            lever = new Lever(this);
        }
    }

    public void setupAnimation(String path){
        new AnimationComponent(this,AnimationBuilder.buildAnimation(path));
    }

    public void setTrapTile(tools.Point pt){
        pc.setPosition(pt);
    }

    public Lever getLever(){
        return lever;
    }

    public tools.Point getPoint(){
        return pc.getPosition();
    }

    public abstract void deactivateTrap();
}
