package ecs.entities.monster;

import ecs.components.ai.AIComponent;
import ecs.components.ai.idle.ProtectTileRadiusWalk;
import starter.Game;

/** The Imp monster */
public class Imp extends Monster {

    private AIComponent ai;

    /** Constructor for the Imp monster */
    public Imp() {
        super(
                0.15F,
                0.15F,
                5,
                "character/monster/imp/idleLeft",
                "character/monster/imp/idleRight",
                "character/monster/imp/runLeft",
                "character/monster/imp/runRight");
        setupAiComponent();
    }

    /**
     * Sets up the AI component
     *
     * <p>Sets the IdleAi to ProtectTileRadiusWalk
     */
    public void setupAiComponent() {
        ai = new AIComponent(this);
        ProtectTileRadiusWalk ptrw =
                new ProtectTileRadiusWalk(Game.currentLevel.getEndTile(), 1.5f, 2);
        ai.setIdleAI(ptrw);
    }

    /**
     * Knocks the Imp back
     *
     * @param knockbackamount
     */
    @Override
    public void knockback(float knockbackamount) {
        super.knockback(knockbackamount);
        setupAiComponent();
    }
}
