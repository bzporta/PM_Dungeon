package ecs.entities.monster;

import ecs.components.ai.AIComponent;
import ecs.components.ai.idle.PatrouilleWalk;

/** The Andromalius monster */
public class Andromalius extends Monster {
    private AIComponent ai;

    /** Constructor for the Andromalius monster */
    public Andromalius() {
        super(
                0.1F,
                0.1F,
                5,
                "game/assets/character/monster/andromalius/idleLeft.png",
                "game/assets/character/monster/andromalius/idleRight.png",
                "game/assets/character/monster/andromalius/idleLeft.png",
                "game/assets/character/monster/andromalius/idleRight.png");
        setupAiComponent();
    }

    public void setupAiComponent() {
        ai = new AIComponent(this);
        ai.setIdleAI(new PatrouilleWalk(3f, 5, 1, PatrouilleWalk.MODE.RANDOM));
    }
}
