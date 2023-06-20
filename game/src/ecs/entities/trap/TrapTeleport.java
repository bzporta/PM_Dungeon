package ecs.entities.trap;

import ecs.components.HitboxComponent;
import ecs.components.MissingComponentException;
import ecs.components.PositionComponent;
import ecs.entities.Entity;
import ecs.entities.Hero;

/** TrapTeleport class A trap that teleports the hero to a given point */
public class TrapTeleport extends Trap {

    private String pathToSkin = "dungeon/trap/TeleportTrapSkin.png";
    private HitboxComponent hb;
    private boolean active = true;

    /**
     * Constructor for TrapTeleport
     *
     * @param pt the point to teleport the hero to
     */
    public TrapTeleport(tools.Point pt) {
        super();
        setupAnimation(pathToSkin);
        hb =
                new HitboxComponent(
                        this,
                        (trap, other, direction) -> {
                            if (other instanceof Hero) {
                                teleport(other, pt);
                            }
                        },
                        null);
    }

    /**
     * Teleports the hero to the given point
     *
     * @param hero the hero to teleport
     * @param pt the point to teleport the hero to
     * @throws MissingComponentException if the hero does not have a position component
     */
    public void teleport(Entity hero, tools.Point pt) {
        if (active) {
            PositionComponent pc =
                    (PositionComponent)
                            hero.getComponent(PositionComponent.class)
                                    .orElseThrow(
                                            () ->
                                                    new MissingComponentException(
                                                            "Positioncomponent"));
            pc.setPosition(pt);
        }
    }

    /** Deactivates the trap */
    @Override
    public void deactivateTrap() {
        setupAnimation("dungeon/trap/deactivate_trap.png");
        active = false;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
