package ecs.entities.monster;

import ecs.components.ai.AIComponent;
import ecs.components.ai.idle.PatrouilleWalk;
import ecs.damage.Damage;
import ecs.damage.DamageType;

public class DarkHeart extends Monster{

    private AIComponent ai;

    public DarkHeart(){
        super(0.1F,
            0.1F,
            new Damage(5, DamageType.MAGIC, null),
            "game/assets/character/monster/darkheart/idleLeft.png",
            "game/assets/character/monster/darkheart/idleRight.png",
            "game/assets/character/monster/darkheart/idleLeft.png",
            "game/assets/character/monster/darkheart/idleRight.png");
        setupAiComponent();
    }
    private void setupAiComponent() {
        ai = new AIComponent(this);
        ai.setIdleAI(new PatrouilleWalk(3f, 5, 1, PatrouilleWalk.MODE.RANDOM));
    }
}
