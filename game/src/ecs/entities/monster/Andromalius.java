package ecs.entities.monster;

import ecs.components.ai.AIComponent;
import ecs.components.ai.idle.PatrouilleWalk;
import ecs.components.ai.idle.ProtectTileRadiusWalk;
import ecs.damage.Damage;
import ecs.damage.DamageType;
import starter.Game;

public class Andromalius extends Monster{
    AIComponent ai;

    public Andromalius(){
        super(0.15F,
            0.15F,
            new Damage(5, DamageType.FIRE, null),
            "game/assets/character/monster/andromalius/idleLeft.png",
            "game/assets/character/monster/andromalius/idleRight.png",
            "game/assets/character/monster/andromalius/idleLeft.png",
            "game/assets/character/monster/andromalius/idleRight.png");
        setupAiComponent();
    }

    private void setupAiComponent() {
        ai = new AIComponent(this);
        ai.setIdleAI(new PatrouilleWalk(3f, 5, 1, PatrouilleWalk.MODE.RANDOM));
    }

}
