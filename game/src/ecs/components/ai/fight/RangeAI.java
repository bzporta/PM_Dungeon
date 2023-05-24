package ecs.components.ai.fight;

import com.badlogic.gdx.ai.pfa.GraphPath;
import ecs.components.PositionComponent;
import ecs.components.ai.AITools;
import ecs.components.skill.ITargetSelection;
import ecs.components.skill.Skill;
import ecs.entities.Entity;
import ecs.entities.Hero;
import level.elements.tile.Tile;
import starter.Game;
import tools.Constants;
import tools.Point;

public class RangeAI implements IFightAI {

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
        PositionComponent poc = (PositionComponent) entity.getComponent(PositionComponent.class).orElseThrow();
        Hero hero = (Hero) Game.getHero().orElseThrow();
        Point p = null;
        if (AITools.playerInRange(entity, distance)){
            fightSkill.execute(entity);

            if (distance/2 < Point.calculateDistance(poc.getPosition(), hero.getPosition()))
                do{
                    p = AITools.getRandomAccessibleTileCoordinateInRange(poc.getPosition(), 5).toPoint();
                } while(Point.calculateDistance(hero.getPosition(), p) < distance/2);
            path = AITools.calculatePath(poc.getPosition(), p);
            AITools.move(entity, path);
            timeSinceLastUpdate = delay;
        } /*else {
            // check if new pathing update
            if (timeSinceLastUpdate >= delay) {
                path = AITools.calculatePathToHero(entity);
                timeSinceLastUpdate = -1;
            }
            timeSinceLastUpdate++;
            AITools.move(entity, path);
        }*/

    }
}
