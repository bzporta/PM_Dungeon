package ecs.entities;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import dslToGame.AnimationBuilder;
import ecs.components.*;
import ecs.components.AnimationComponent;
import ecs.components.PositionComponent;
import ecs.components.VelocityComponent;
import ecs.components.skill.*;
import ecs.components.xp.ILevelUp;
import ecs.components.xp.XPComponent;
import ecs.entities.trap.TrapDmg;
import ecs.systems.ECS_System;
import graphic.Animation;
import graphic.hud.GameOver;
import graphic.hud.SkillMenu;
import tools.Point;

import java.util.function.Consumer;


/**
 * The Hero is the player character. It's entity in the ECS. This class helps to setup the hero with
 * all its components and attributes .
 */
public class Hero extends Entity implements IOnDeathFunction, ILevelUp {

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

    private IceballSkill iceballSkill;

    private Skill secondSkill;
    private HealSkill healSkill;
    private final int healCooldown = 15;

    private Skill thirdSkill;
    private final int iceballCooldown= 1;

    private PositionComponent pc;
    private SkillComponent sc;

    private SkillMenu<Actor> skillMenu;
    private XPComponent xp;
    private PlayableComponent pac;

    /**
     * Amount of how often the hero has upgraded the IceBallSkill.
     */
    public static int upgradeIceBallSkill = 0;
    /**
     * Amount of how often the hero has upgraded the HealSkill.
     */
    public static int upgradeHealSkill = 0;

    /** Constructor Entity with Components */
    public Hero() {
        super();
        pc = new PositionComponent(this);
        healSkill = new HealSkill(25);
        iceballSkill = new IceballSkill(SkillTools::getCursorPositionAsPoint, 0.05f, 10);
        setupHealthComponent();
        setupVelocityComponent();
        setupAnimationComponent();
        setupHitboxComponent();
        pac = new PlayableComponent(this);
        setupFireballSkill();
        setupHealSkill();
        setupIceballSkill();
        pac.setSkillSlot1(firstSkill);
        setupSkillComponent();
        setupXPComponent();
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

    private void setupHealSkill(){
        secondSkill =
            new Skill(
                healSkill, healCooldown);
    }

    private void setupIceballSkill() {
        thirdSkill =
            new Skill(
                iceballSkill, iceballCooldown);
    }

    private void setupSkillComponent(){
        sc = new SkillComponent(this);
        sc.addSkill(firstSkill);
        sc.addSkill(secondSkill);
        sc.addSkill(thirdSkill);
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

    private void setupXPComponent(){
        xp = new XPComponent(this, this::onLevelUp );
        xp.setCurrentLevel(1);
        xp.setCurrentXP(-20);
    }

    private void setupHealthComponent(){
        hp = new HealthComponent(this);
        hp.setMaximalHealthpoints(1000);
        hp.setCurrentHealthpoints(1000);
        hp.setOnDeath(this::onDeath);
    }

    /** Is called when the hero levels up
     * @param nextLevel is the new level of the hero
     */
    public void onLevelUp(long nextLevel){
        starter.Game.getGame().toggleSkillMenu();
    }

    /** Is called when the hero dies
     * @param entity which is looked for
     */
    @Override
    public void onDeath(Entity entity){
        starter.Game.getGame().toggleGameOver();
    }

    /** Returns the position of the hero
     * @return position of the hero
     */
    public Point getPosition(){
        return pc.getPosition();
    }

    /** Returns the health component of the hero
     * @return health component of the hero
     */
    public HealthComponent getHC(){
        return hp;
    }

    public XPComponent getXP(){
        return xp;
    }

    /** Returns the playable component of the hero
     * @return playable component of the hero
     */
    public PlayableComponent getPAC(){
        return pac;
    }
    public SkillComponent getSC(){
        return sc;
    }

    /**
     * Sets the HealSkill to the second skill slot
     */
    public void setHealSkill(){
        upgradeHealSkill++;
        if (pac.getSkillSlot2().isEmpty()){
            pac.setSkillSlot2(secondSkill);
        }
        else{
            healSkill.setHealAmount(healSkill.getHealAmount() + 5);
        }
    }

    /**
     * Sets the FreezeSkill to the third skill slot
     */
    public void setIceBallSkill(){
        upgradeIceBallSkill++;
        if (pac.getSkillSlot3().isEmpty()){
            pac.setSkillSlot3(thirdSkill);
        }
        else{
            iceballSkill.setSpeedpenalty(iceballSkill.getSpeedpenalty() + 0.01f);
            iceballSkill.setSpellCost(iceballSkill.getSpellCost() + 2);
        }
    }
}
