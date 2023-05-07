package ecs.entities.monster;

import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.damage.Damage;
import ecs.entities.Entity;
import ecs.entities.trap.TrapDmg;
import graphic.Animation;

public abstract class Monster extends Entity {

    private Damage dmg;
    private float xSpeed;
    private float ySpeed;
    private PositionComponent pc;
    private HealthComponent hp;

    private final String pathToIdleLeft = "ghost/idleleft.png";
    private final String pathToIdleRight = "ghost/idleright.png";
    private final String pathToRunLeft = "ghost/runleft.png";
    private final String pathToRunRight = "ghost/runright.png";

    public Monster(){
        pc = new PositionComponent(this);
        setupVelocityComponent();
        setupAnimationComponent();
        setupHealthComponent();
        setupHitboxComponent();
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
}
