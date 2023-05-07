package ecs.entities.monster;

import dslToGame.AnimationBuilder;
import ecs.components.AnimationComponent;
import ecs.components.VelocityComponent;
import ecs.components.ai.AIComponent;
import ecs.components.ai.idle.PatrouilleWalk;
import graphic.Animation;

public class Imp extends Monster{

    AIComponent ai;

    public Imp(){
        super(0.15F,
            0.15F,
            "character/monster/imp/idleLeft",
            "character/monster/imp/idleRight",
            "character/monster/imp/runLeft",
            "character/monster/imp/runRight");
        setupAiComponent();
    }

    private void setupAiComponent() {
        ai = new AIComponent(this);
        PatrouilleWalk pw = new PatrouilleWalk(1f, 3, 5, PatrouilleWalk.MODE.RANDOM);
    }


}
