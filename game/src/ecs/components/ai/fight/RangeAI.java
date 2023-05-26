package ecs.components.ai.fight;

import com.badlogic.gdx.ai.pfa.GraphPath;
import ecs.components.PositionComponent;
import ecs.components.ai.AITools;
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
    private Point p;

    /**
     * RangeAI constructor
     *
     * @param distance distance from which the entity will attack
     * @param fightSkill skill to use when attacking
     */
    public RangeAI(float distance, Skill fightSkill) {
        this.distance = distance;
        this.fightSkill = fightSkill;
    }

    @Override
    public void fight(Entity entity) {
        PositionComponent poc =
                (PositionComponent) entity.getComponent(PositionComponent.class).orElseThrow();
        Hero hero = (Hero) Game.getHero().orElseThrow();

        if (AITools.playerInRange(entity, distance)) {
            fightSkill.execute(entity);

            if (distance - 1
                    < Math.abs(Point.calculateDistance(poc.getPosition(), hero.getPosition()))) {
                do {
                    p =
                            AITools.getRandomAccessibleTileCoordinateInRange(
                                            poc.getPosition(), distance)
                                    .toPoint();
                    path = AITools.calculatePath(poc.getPosition(), p);

                } while (Math.abs(Point.calculateDistance(hero.getPosition(), p)) < distance - 1
                        && AITools.pathFinished(entity, path));
                if (!AITools.pathFinished(entity, path)) {
                    AITools.move(entity, path);
                }
                AITools.move(entity, path);
            }

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
