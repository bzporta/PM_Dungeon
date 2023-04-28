package ecs.entities.trap;

import ecs.damage.Damage;
import ecs.damage.DamageType;
import ecs.entities.trap.Trap;

/** TrapDmg class
 * A trap that deals damage
 */
public class TrapDmg extends Trap {

    private static Damage dmg;
    private String pathToSkin = "dungeon/trap/Pit_Trap_Spikes.png";

    /** Constructor for TrapDmg
     */
    public TrapDmg(){
        super();
        setupAnimation(pathToSkin);
        dmg = new Damage(20, DamageType.PHYSICAL, null);
    }

    /** Returns the damage of the trap
     * @return the damage of the trap
     */
    public static Damage getDmg(){
        return dmg;
    }

    /** Deactivates the trap
     */
    @Override
    public void deactivateTrap() {
        setupAnimation("dungeon/trap/deactivate_trap.png");
        dmg = new Damage(0,null,null);
    }
}
