package ecs.components.ai.fight;

import com.badlogic.gdx.ai.pfa.GraphPath;
import ecs.components.ai.AITools;
import ecs.components.skill.ITargetSelection;
import ecs.components.skill.Skill;
import ecs.entities.Entity;
import level.elements.tile.Tile;
import tools.Constants;
import tools.Point;

public class RangeAI implements IFightAI, ITargetSelection {

    private final float distance;
    private final int delay = Constants.FRAME_RATE;
    private int timeSinceLastUpdate = delay;
    private Skill fightSkill;
    private GraphPath<Tile> path;

    public RangeAI(float distance, Skill fightSkill){
        this.distance = distance;
        this.fightSkill = fightSkill;
    }
    @Override
    public void fight(Entity entity) {
        if (AITools.playerInRange(entity, distance)){
            fightSkill.execute(entity);
            path = AITools.calculatePathToHero(entity);
            AITools.move(entity, path);
            timeSinceLastUpdate = delay;
        } else {
            // check if new pathing update
            if (timeSinceLastUpdate >= delay) {
                path = AITools.calculatePathToHero(entity);
                timeSinceLastUpdate = -1;
            }
            timeSinceLastUpdate++;
            AITools.move(entity, path);
        }

    }

    @Override
    public Point selectTargetPoint() {
        return null;
    }
}
