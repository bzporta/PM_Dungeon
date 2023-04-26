package ecs.entities;

import dslToGame.AnimationBuilder;
import ecs.components.AnimationComponent;
import ecs.components.PositionComponent;
import ecs.components.VelocityComponent;
import ecs.components.ai.AIComponent;
import ecs.components.ai.AITools;
import ecs.components.ai.idle.PatrouilleWalk;
import ecs.components.ai.idle.RadiusWalk;
import ecs.components.ai.idle.StaticRadiusWalk;
import graphic.Animation;
import starter.Game;
import tools.Point;

public class Ghost extends Entity{

    private final float xSpeed = 0.1f;
    private final float ySpeed = 0.1f;

    private final String pathToIdleLeft = "ghost/idleleft.png";
    private final String pathToIdleRight = "ghost/idleright.png";
    private final String pathToRunLeft = "ghost/runleft.png";
    private final String pathToRunRight = "ghost/runright.png";

    private PositionComponent pc;
    private AIComponent ai;

    private Hero hero;

    /** Constructor
     * Creates a ghost-object which the Hero can be followed by
     * @param follows Hero which the ghost follows
     * */
    public Ghost(Hero follows){
        pc = new PositionComponent(this);
        setupVelocityComponent();
        setupAnimationComponent();
        this.hero = follows;
        setupAiComponent();
    }

    private void setupAiComponent() {
        ai = new AIComponent(this);
        PatrouilleWalk pw = new PatrouilleWalk(1f, 3, 5, PatrouilleWalk.MODE.RANDOM);
    }

    private void setupVelocityComponent() {
        Animation moveRight = AnimationBuilder.buildAnimation(pathToRunRight);
        Animation moveLeft = AnimationBuilder.buildAnimation(pathToRunLeft);
        new VelocityComponent(this, xSpeed, ySpeed, moveLeft, moveRight);
    }

    private void setupAnimationComponent() {
        Animation idleRight = AnimationBuilder.buildAnimation(pathToIdleRight);
        Animation idleLeft = AnimationBuilder.buildAnimation(pathToIdleLeft);
        new AnimationComponent(this, idleLeft, idleRight);
    }

    /** Sets the position of the ghost to the position of the hero */
    public void setSpawn(){
        pc.setPosition(hero.getPosition());
    }
}
