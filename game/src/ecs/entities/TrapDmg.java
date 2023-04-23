package ecs.entities;

public class TrapDmg extends Trap{

    String pathToSkin = "dungeon/trap/Pit_Trap_Spikes.png";

    public TrapDmg(){
        super();
        setupAnimation(pathToSkin);
    }


}
