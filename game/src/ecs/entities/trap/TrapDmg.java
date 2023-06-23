package ecs.entities.trap;

import ecs.damage.Damage;
import ecs.damage.DamageType;

/** TrapDmg class A trap that deals damage */
public class TrapDmg extends Trap {

    private Damage dmg;
    private String pathToSkin = "dungeon/trap/Pit_Trap_Spikes.png";

    /** Constructor for TrapDmg */
    public TrapDmg() {
        super();
        setupAnimation(pathToSkin);
        dmg = new Damage(25, DamageType.PHYSICAL, null);
    }

    /**
     * Returns the damage of the trap
     *
     * @return the damage of the trap
     */
    public Damage getDmg() {
        return dmg;
    }

    /** Deactivates the trap */
    @Override
    public void deactivateTrap() {
        setupAnimation("dungeon/trap/deactivate_trap.png");
        dmg = new Damage(0, null, null);
    }

    /**
     * Sets the damage of the trap
     *
     * @param dmg the damage to set the trap to
     */
    public void setDmg(Damage dmg) {
        this.dmg = dmg;
    }
}
