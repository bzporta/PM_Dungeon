package ecs.entities.monster;

import dslToGame.AnimationBuilder;
import ecs.components.AnimationComponent;
import ecs.components.VelocityComponent;
import ecs.components.ai.AIComponent;
import ecs.components.ai.idle.PatrouilleWalk;
import ecs.components.ai.idle.ProtectTileRadiusWalk;
import ecs.damage.Damage;
import ecs.damage.DamageType;
import graphic.Animation;
import starter.Game;

/**
 * The Imp monster
 */
public class Imp extends Monster{

    private AIComponent ai;

    /**
     * Constructor for the Imp monster
     */
    public Imp(){
        super(0.15F,
            0.15F,
            5,
            "character/monster/imp/idleLeft",
            "character/monster/imp/idleRight",
            "character/monster/imp/runLeft",
            "character/monster/imp/runRight");
        setupAiComponent();
    }

    private void setupAiComponent() {
        ai = new AIComponent(this);
        ProtectTileRadiusWalk ptrw = new ProtectTileRadiusWalk(Game.currentLevel.getEndTile(), 1.5f, 2);
        ai.setIdleAI(ptrw);
    }


}
