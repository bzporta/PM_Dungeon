package ecs.entities.monster;

import com.badlogic.gdx.ai.pfa.GraphPath;
import ecs.components.HealthComponent;
import ecs.components.ai.AIComponent;
import ecs.components.ai.fight.CollideAI;
import ecs.components.ai.fight.IFightAI;
import ecs.components.ai.fight.RangeAI;
import ecs.components.ai.idle.BossWalk;
import ecs.components.ai.idle.IIdleAI;
import ecs.components.ai.transition.ITransition;
import ecs.components.ai.transition.RangeTransition;
import level.elements.tile.Tile;
import starter.Game;
import tools.Point;

public class BossMonster extends Monster{

    private HealthComponent hp;
    private AIComponent ai;
    private IFightAI iFightAI;
    private IIdleAI idleAI;
    private ITransition iTransition;


    public BossMonster() {
        super(
        0.10f,
        0.10F,
        20,
        "game/assets/character/monster/boss/idleLeft.png",
        "game/assets/character/monster/boss/idleRight.png",
        "game/assets/character/monster/boss/idleLeft.png",
        "game/assets/character/monster/boss/idleRight.png");
        setupHealthComponent();
        setupAiComponent();
        setPosition(Game.currentLevel.getEndTile().getCoordinateAsPoint());
    }
    @Override
    public void setupHealthComponent(){
        hp = new HealthComponent(this);
        hp.setMaximalHealthpoints(250);
        hp.setCurrentHealthpoints(250);
    }

    public void setupAiComponent(){
    ai = new AIComponent(this, new RangeAI(10), new BossWalk(2), new RangeTransition(5));

    }




}
