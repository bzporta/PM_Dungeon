package ecs.entities.monster;

import dslToGame.AnimationBuilder;
import ecs.components.AnimationComponent;
import ecs.components.HealthComponent;
import ecs.components.HitboxComponent;
import ecs.components.PositionComponent;
import ecs.components.VelocityComponent;
import ecs.damage.Damage;
import ecs.damage.DamageType;
import ecs.entities.Entity;
import ecs.entities.Hero;
import ecs.quests.KillQuest;
import graphic.Animation;
import java.util.logging.Logger;
import starter.Game;
import tools.Point;

/** The abstract Monster class */
public abstract class Monster extends Entity {

    private Damage damage;

    private float xSpeed;
    private float ySpeed;
    private PositionComponent pc;
    private HealthComponent hp;
    private VelocityComponent vc;
    private String pathToIdleLeft;
    private String pathToIdleRight;
    private String pathToRunLeft;
    private String pathToRunRight;
    private int hitDmg;
    private transient Logger logger;

    /**
     * Constructor for the Monster class
     *
     * @param xSpeed speed in x direction
     * @param ySpeed speed in y direction
     * @param hitDmg damage of the monster
     * @param idleLeft path to the idle animation in left direction
     * @param idleRight path to the idle animation in right direction
     * @param runLeft path to the run animation in left direction
     * @param runRight path to the run animation in right direction
     */
    public Monster(
            float xSpeed,
            float ySpeed,
            int hitDmg,
            String idleLeft,
            String idleRight,
            String runLeft,
            String runRight) {
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.hitDmg = hitDmg;
        this.damage = new Damage(hitDmg, DamageType.PHYSICAL, null);
        this.pathToIdleLeft = idleLeft;
        this.pathToIdleRight = idleRight;
        this.pathToRunLeft = runLeft;
        this.pathToRunRight = runRight;
        pc = new PositionComponent(this);
        setupHealthComponent();
        setupHitboxComponent();
        setupAnimationComponent();
        setupVelocityComponent();
    }

    private void setupVelocityComponent() {
        Animation moveRight = AnimationBuilder.buildAnimation(pathToRunRight);
        Animation moveLeft = AnimationBuilder.buildAnimation(pathToRunLeft);
        vc = new VelocityComponent(this, xSpeed, ySpeed, moveLeft, moveRight);
    }

    private void setupAnimationComponent() {
        Animation idleRight = AnimationBuilder.buildAnimation(pathToIdleRight);
        Animation idleLeft = AnimationBuilder.buildAnimation(pathToIdleLeft);
        new AnimationComponent(this, idleLeft, idleRight);
    }

    /**
     * Sets the Healthcomponent of the monster
     *
     * <p>The Monster get 100 Healthpoints and can reach 100 Healthpoints
     */
    public void setupHealthComponent() {
        hp = new HealthComponent(this);
        hp.setMaximalHealthpoints(100);
        hp.setCurrentHealthpoints(100);
        hp.setOnDeath(this::onDeath);
    }

    private void setupHitboxComponent() {
        new HitboxComponent(this);
    }

    private void onDeath(Entity entity) {
        KillQuest killQuest = Game.getKillQuest();
        if (killQuest.isActive()) {
            killQuest.countKilledMonsters();
        }
    }

    /**
     * Sets the position of the monster
     *
     * @param p
     */
    public void setPosition(Point p) {
        pc.setPosition(p);
    }

    /**
     * Returns the Healthcomponent of the monster
     *
     * @return the Healthcomponent of the monster
     */
    public HealthComponent getHp() {
        return hp;
    }

    /**
     * Returns the damage of the monster
     *
     * @return the damage of the monster
     */
    public int getHitDmg() {
        return hitDmg;
    }

    /**
     * Sets the damage of the monster
     *
     * @param hitDmg the damage of the monster
     */
    public void setHitDmg(int hitDmg) {
        this.hitDmg = hitDmg;
        damage = new Damage(hitDmg, DamageType.PHYSICAL, null);
    }

    /** Sets the animation of the monster to frozen and stops the movement */
    public void setFrozen() {
        String frozen = "character/monster/frozen";

        Animation idleRight = AnimationBuilder.buildAnimation(frozen);
        Animation idleLeft = AnimationBuilder.buildAnimation(frozen);
        new AnimationComponent(this, idleLeft, idleRight);

        Animation moveRight = AnimationBuilder.buildAnimation(frozen);
        Animation moveLeft = AnimationBuilder.buildAnimation(frozen);
        vc = new VelocityComponent(this, 0, 0, moveLeft, moveRight);
    }

    /** Sets the AIComponent of the monster */
    public abstract void setupAiComponent();

    /**
     * Knocks the monster back
     *
     * @param knockbackamount
     */
    public void knockback(float knockbackamount) {
        // if (knockbackamount < 0) throw new IllegalArgumentException("No negative Knockback
        // allowed");
        // if (knockbackamount > 3) knockbackamount = 3;
        logger = Logger.getLogger(getClass().getName());
        logger.info(this.getClass().getSimpleName() + " got knocked back");
        Hero hero = (Hero) starter.Game.getHero().orElseThrow();
        float x = pc.getPosition().x - hero.getPosition().x;
        float y = pc.getPosition().y - hero.getPosition().y;
        float length = (float) Math.sqrt(x * x + y * y);
        float x_normalized = x / length;
        float y_normalized = y / length;
        float newx = pc.getPosition().x + x_normalized * knockbackamount;
        float newy = pc.getPosition().y + y_normalized * knockbackamount;
        Point newPosition = new Point(newx, newy);
        if (Game.currentLevel.getTileAt(newPosition.toCoordinate()).isAccessible()) {
            pc = new PositionComponent(this, newx, newy);
        }
    }

    public void setPc(PositionComponent pc) {
        this.pc = pc;
    }

    public PositionComponent getPc() {
        return pc;
    }

    public VelocityComponent getVc() {
        return vc;
    }

    public void setVc(VelocityComponent vc) {
        this.vc = vc;
    }
}
