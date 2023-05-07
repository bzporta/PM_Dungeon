package ecs.entities.monster;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.damage.Damage;
import ecs.entities.Entity;
import ecs.entities.trap.TrapDmg;
import graphic.Animation;
import tools.Point;

public abstract class Monster extends Entity {

    private Damage dmg;
    private float xSpeed;
    private float ySpeed;
    private PositionComponent pc;
    private HealthComponent hp;

    private String pathToIdleLeft;
    private String pathToIdleRight;
    private String pathToRunLeft;
    private String pathToRunRight;

    public Monster(float xSpeed, float ySpeed, String idleLeft, String idleRight, String runLeft, String runRight){
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.pathToIdleLeft = idleLeft;
        this.pathToIdleRight = idleRight;
        this.pathToRunLeft = runLeft;
        this.pathToRunRight = runRight;
        pc = new PositionComponent(this);
        setupHealthComponent();
        setupHitboxComponent();
        setupVelocityComponent();
        setupAnimationComponent();
    }

    public void setupVelocityComponent() {
        Animation moveRight = AnimationBuilder.buildAnimation(pathToRunRight);
        Animation moveLeft = AnimationBuilder.buildAnimation(pathToRunLeft);
        new VelocityComponent(this, xSpeed, ySpeed, moveLeft, moveRight);
    }

    public void setupAnimationComponent() {
        Animation idleRight = AnimationBuilder.buildAnimation(pathToIdleRight);
        Animation idleLeft = AnimationBuilder.buildAnimation(pathToIdleLeft);
        new AnimationComponent(this, idleLeft, idleRight);
    }

    private void setupHealthComponent(){
        hp = new HealthComponent(this);
        hp.setMaximalHealthpoints(100);
        hp.setCurrentHealthpoints(100);
        hp.setOnDeath(this::onDeath);
    }

    private void setupHitboxComponent() {
        new HitboxComponent(this);
    }


    public void onDeath(Entity entity){
        System.out.println("Monster toooot");
    }

    public void setPosition(Point p){
        pc.setPosition(p);
    }


}
