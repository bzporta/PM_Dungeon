package ecs.entities;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.AnimationComponent;
import ecs.components.PositionComponent;
import ecs.components.VelocityComponent;
import ecs.components.skill.*;
import ecs.entities.trap.TrapDmg;
import graphic.Animation;
import graphic.hud.GameOver;
import tools.Point;


/**
 * The Hero is the player character. It's entity in the ECS. This class helps to setup the hero with
 * all its components and attributes .
 */
public class Hero extends Entity implements IOnDeathFunction {

    private final int fireballCoolDown = 5;
    private final float xSpeed = 0.3f;
    private final float ySpeed = 0.3f;

    private final String pathToIdleLeft = "knight/idleLeft";
    private final String pathToIdleRight = "knight/idleRight";
    private final String pathToRunLeft = "knight/runLeft";
    private final String pathToRunRight = "knight/runRight";
    private HealthComponent hp;
    private static GameOver<Actor> gameOverMenu2;
    private Skill firstSkill;

    private PositionComponent pc;

    /** Constructor Entity with Components */
    public Hero() {
        super();
        pc = new PositionComponent(this);
        setupHealthComponent();
        setupVelocityComponent();
        setupAnimationComponent();
        setupHitboxComponent();
        PlayableComponent pc = new PlayableComponent(this);
        setupFireballSkill();
        pc.setSkillSlot1(firstSkill);
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

    private void setupFireballSkill() {
        firstSkill =
                new Skill(
                        new FireballSkill(SkillTools::getCursorPositionAsPoint), fireballCoolDown);
    }

    private void setupHitboxComponent() {
        new HitboxComponent(
                this,
                (hero, other, direction) -> {
                    if(other instanceof TrapDmg){
                        hp.receiveHit(TrapDmg.getDmg());
                    }
                },
                null);
    }

    private void setupHealthComponent(){
        hp = new HealthComponent(this);
        hp.setMaximalHealthpoints(100);
        hp.setCurrentHealthpoints(100);
        hp.setOnDeath(this::onDeath);
    }

    /** Is called when the hero dies
     * @param entity which is looked for
     */
    @Override
    public void onDeath(Entity entity){
        gameOverMenu2 = starter.Game.getGameOverMenu();
        gameOverMenu2.showMenu();
        System.out.println("Game Over");
    }

    /** Returns the position of the hero
     * @return position of the hero
     */
    public Point getPosition(){
        return pc.getPosition();
    }

    public HealthComponent getHC(){
        return hp;
    }

}
