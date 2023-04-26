package ecs.entities.trap;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.entities.Entity;

/** Lever class
 * A lever that can be pulled to deactivate a trap
 */
public class Lever extends Entity {

    private PositionComponent pc;
    private Trap trap;

    private String pathToSkin = "dungeon/lever/e2063a6ece3a560.png";

    /** Constructor for Lever
     * @param trap the trap that the lever is connected to
     */
    public Lever(Trap trap){
        this.trap = trap;
        new InteractionComponent(this, 1f, false, this::pullLever);
        pc = new PositionComponent(this);
        setupAnimation(pathToSkin);

    }
    private void setupAnimation(String path){
        new AnimationComponent(this, AnimationBuilder.buildAnimation(path));
    }

    /** Sets the position of the lever
     * @param pt the position to set the lever to
     */
    public void setLever(tools.Point pt){
        pc.setPosition(pt);
    }

    //????????????????????????????????????
    public tools.Point getPoint(){
        return pc.getPosition();
    }

    private void pullLever(Entity entity){
        trap.deactivateTrap();
        setupAnimation("dungeon/lever/flippedlever.png");
    }
}
