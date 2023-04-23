package ecs.entities;

import ecs.damage.Damage;
import ecs.damage.DamageType;

public class TrapDmg extends Trap{

    private Damage dmg = new Damage(20, DamageType.PHYSICAL, this);
    String pathToSkin = "dungeon/trap/Pit_Trap_Spikes.png";

    public TrapDmg(){
        super();
        setupAnimation(pathToSkin);
    }


}
