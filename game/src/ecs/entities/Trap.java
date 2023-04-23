package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.AnimationComponent;
import ecs.components.HitboxComponent;
import ecs.components.PositionComponent;
import ecs.components.collision.ICollide;
import org.lwjgl.system.Pointer;

public abstract class Trap extends Entity{


    Lever lever;
    PositionComponent pc;
    HitboxComponent hb;
    ICollide onEntry;

    public Trap(){
        hb = new HitboxComponent(this);
        pc = new PositionComponent(this);

    }

    public void setupAnimation(String path){
        new AnimationComponent(this,AnimationBuilder.buildAnimation(path));
    }

    public void setTrapTile(tools.Point pt){
        pc.setPosition(pt);
    }

}
