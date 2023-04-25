package ecs.entities;

import ecs.damage.Damage;
import ecs.damage.DamageType;

public class TrapDmg extends Trap{

    private static Damage dmg;
    private String pathToSkin = "dungeon/trap/Pit_Trap_Spikes.png";

    public TrapDmg(){
        super();
        setupAnimation(pathToSkin);
        dmg = new Damage(20, DamageType.PHYSICAL, null);
    }

    public static Damage getDmg(){
        return dmg;
    }

    @Override
    public void deactivateTrap() {
        dmg = new Damage(0,null,null);
    }
}
