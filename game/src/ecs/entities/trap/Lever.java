package ecs.entities.trap;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.entities.Entity;

public class Lever extends Entity {

    private PositionComponent pc;
    private Trap trap;

    private String pathToSkin = "dungeon/lever/e2063a6ece3a560.png";

    public Lever(Trap trap){
        this.trap = trap;
        new InteractionComponent(this, 1f, false, this::pullLever);
        pc = new PositionComponent(this);
        setupAnimation(pathToSkin);

    }
    private void setupAnimation(String path){
        new AnimationComponent(this, AnimationBuilder.buildAnimation(path));
    }

    public void setLever(tools.Point pt){
        pc.setPosition(pt);
    }

    public tools.Point getPoint(){
        return pc.getPosition();
    }

    private void pullLever(Entity entity){
        trap.deactivateTrap();
        setupAnimation("dungeon/lever/flippedlever.png");
    }
}
