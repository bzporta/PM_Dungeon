package ecs.entities.trap;

import dslToGame.AnimationBuilder;
import ecs.components.AnimationComponent;
import ecs.components.HitboxComponent;
import ecs.components.PositionComponent;
import ecs.entities.Entity;
import java.awt.*;
import java.util.Random;

/** Trap class A trap that can be deactivated by a lever */
public abstract class Trap extends Entity {

    private Lever lever;
    private PositionComponent pc;
    private HitboxComponent hb;

    /** Constructor for Trap */
    public Trap() {
        hb = new HitboxComponent(this);
        pc = new PositionComponent(this);
        Random rand = new Random();
        if (rand.nextDouble() < 0.5) {
            // lever = new Lever(this);
        }
    }

    /**
     * Sets up the animation of the trap
     *
     * @param path the path to the animation
     */
    public void setupAnimation(String path) {
        new AnimationComponent(this, AnimationBuilder.buildAnimation(path));
    }

    /**
     * Sets the position of the trap
     *
     * @param pt the position to set the trap to
     */
    public void setTrapTile(tools.Point pt) {
        pc.setPosition(pt);
    }

    /**
     * Gets the lever connected to the trap
     *
     * @return the lever connected to the trap
     */
    public Lever getLever() {
        return lever;
    }

    /**
     * Gets the position of the trap
     *
     * @return the position of the trap
     */
    public tools.Point getPoint() {
        return pc.getPosition();
    }

    /** Deactivates the trap */
    public abstract void deactivateTrap();

    public void setPc(PositionComponent pc) {
        this.pc = pc;
    }
}
