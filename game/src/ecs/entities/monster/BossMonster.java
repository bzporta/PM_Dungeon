package ecs.entities.monster;

import com.badlogic.gdx.ai.pfa.GraphPath;
import ecs.components.HealthComponent;
import ecs.components.ai.AIComponent;
import ecs.components.ai.fight.CollideAI;
import ecs.components.ai.fight.IFightAI;
import ecs.components.ai.fight.MeleeAI;
import ecs.components.ai.fight.RangeAI;
import ecs.components.ai.idle.BossWalk;
import ecs.components.ai.idle.IIdleAI;
import ecs.components.ai.transition.ITransition;
import ecs.components.ai.transition.RangeTransition;
import ecs.components.skill.FireballSkill;
import ecs.components.skill.Skill;
import ecs.components.skill.SkillTools;
import ecs.components.skill.SwordSkill;
import ecs.damage.Damage;
import ecs.damage.DamageType;
import ecs.entities.Hero;
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
        phase1();
        setPosition(Game.currentLevel.getEndTile().getCoordinateAsPoint());
    }
    @Override
    public void setupHealthComponent(){
        hp = new HealthComponent(this);
        hp.setMaximalHealthpoints(250);
        hp.setCurrentHealthpoints(250);
    }

    private void setupAiComponent(){
        ai = new AIComponent(this);
    }

    private void phase1(){
    ai.setFightAI(new MeleeAI(1, new Skill(new SwordSkill(new Damage(10, DamageType.PHYSICAL, null)) , 1f)));
    ai.setTransitionAI(new RangeTransition(5));
    ai.setIdleAI(new BossWalk(2));
    }

}



