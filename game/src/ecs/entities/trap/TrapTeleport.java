package ecs.entities.trap;

import ecs.components.HitboxComponent;
import ecs.components.MissingComponentException;
import ecs.components.PositionComponent;
import ecs.entities.Entity;
import ecs.entities.Hero;
import ecs.entities.trap.Trap;

public class TrapTeleport extends Trap {

    private String pathToSkin = "dungeon/trap/TeleportTrapSkin.png";
    private HitboxComponent hb;
    private boolean active = true;
    public TrapTeleport(tools.Point pt){
        super();
        setupAnimation(pathToSkin);
        hb = new HitboxComponent(this,
            (trap, other, direction) -> {
                if(other instanceof Hero){
                    teleport(other, pt);
                }
            },
            null);
    }

    public void teleport(Entity hero, tools.Point pt){
        if(active){
            PositionComponent pc = (PositionComponent) hero.getComponent(PositionComponent.class).orElseThrow(()-> new MissingComponentException("Positioncomponent"));
            pc.setPosition(pt);
        }
    }

    @Override
    public void deactivateTrap() {
        setupAnimation("dungeon/trap/deactivate_trap.png");
        active = false;
    }
}
