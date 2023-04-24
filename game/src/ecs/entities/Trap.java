package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.AnimationComponent;
import ecs.components.HitboxComponent;
import ecs.components.PositionComponent;


public abstract class Trap extends Entity{


    private Lever lever;
    private PositionComponent pc;
    private HitboxComponent hb;


    public Trap(){
        hb = new HitboxComponent(this);
        pc = new PositionComponent(this);
        lever = new Lever();
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
}
