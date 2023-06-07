package ecs.components.skill;

import ecs.components.*;
import ecs.damage.Damage;
import ecs.entities.Entity;
import ecs.entities.Hero;
import ecs.entities.monster.Monster;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import starter.Game;

/** The SwordSkill class */
public class SwordSkill implements ISkillFunction {

    private final Damage projectileDamage;
    private String pathToAnimation;
    private float range = 1.5f;
    private transient Logger logger;

    /**
     * Konstruktor für SwordSkill
     *
     * @param projectileDamage
     */
    public SwordSkill(Damage projectileDamage) {
        logger = Logger.getLogger(this.getClass().getName());
        this.projectileDamage = projectileDamage;
        this.pathToAnimation = "skills.sword";
    }

    /**
     * Führt die SwordSkill aus
     *
     * @param entity
     */
    @Override
    public void execute(Entity entity) {
        checkHit(entity);
    }

    private List<Entity> getEnemyOnLVL(Entity caster) {
        List<Entity> entities = new ArrayList<>();
        if (caster instanceof Monster) {
            entities =
                    Game.getEntities().stream()
                            .filter(mo -> mo instanceof Hero)
                            .collect(Collectors.toList());

        } else if (caster instanceof Hero) {
            entities =
                    Game.getEntities().stream()
                            .filter(mo -> mo instanceof Monster)
                            .collect(Collectors.toList());
        }
        return entities;
    }

    private void checkHit(Entity caster) {
        List<Entity> entities = getEnemyOnLVL(caster);
        entities.stream().filter(e -> checkCord(e, caster)).forEach(entity -> attack(entity));
    }

    private Boolean checkCord(Entity entities, Entity caster) {
        PositionComponent pcE =
                (PositionComponent) entities.getComponent(PositionComponent.class).orElseThrow();
        PositionComponent pcC =
                (PositionComponent) caster.getComponent(PositionComponent.class).orElseThrow();
        System.out.println("Entiti: " + entities + "PC: " + pcE.getPosition().y);
        System.out.println("Caster: " + caster + "PC: " + pcC.getPosition().y);
        boolean checkFront =
                (Math.abs(pcC.getPosition().y - pcE.getPosition().y) <= range
                        && Math.abs(pcC.getPosition().x - pcE.getPosition().x) <= 1);
        return checkFront;
    }

    private void attack(Entity entities) {
        HealthComponent hc =
                (HealthComponent) entities.getComponent(HealthComponent.class).orElseThrow();
        hc.receiveHit(projectileDamage);

        if (entities instanceof Monster) {
            ((Monster) entities).knockback(1.1f);
            logger.info("Monster: " + entities.getClass().getSimpleName() + " got hit");
        } else {
            logger.info("Hero: " + entities.getClass().getSimpleName() + " got hit");
        }
    }
}
