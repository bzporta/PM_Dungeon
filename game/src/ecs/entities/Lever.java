package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.*;

public class Lever extends Entity {

    private PositionComponent pc;
    private InteractionComponent ic;


    private String pathToSkin = "dungeon/lever/e2063a6ece3a560.png";
    public Lever(){

        ic = new InteractionComponent(this, 5f, false, entity -> System.out.println("121212"));
        pc = new PositionComponent(this);
        setupAnimation(pathToSkin);

    }
    public void setupAnimation(String path){
        new AnimationComponent(this, AnimationBuilder.buildAnimation(path));
    }

    public void setLever(tools.Point pt){
        pc.setPosition(pt);
    }

}
