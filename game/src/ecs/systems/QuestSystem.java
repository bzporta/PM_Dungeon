package ecs.systems;

import ecs.entities.Hero;
import ecs.quests.Quest;
import starter.Game;

public class QuestSystem extends ECS_System {

    @Override
    public void update() {
        Game.getQuestList().stream()
                .filter(q -> q.isActive())
                .filter(q -> q.checkIfDone())
                .forEach(this::setReward);

        Game.getQuestList().stream()
                .filter(q -> q.isActive())
                .forEach(q -> refreshQuestMenu());
    }

    public void setReward(Quest quest){
        Hero hero = (Hero) Game.getHero().orElseThrow();
        hero.getXP().addXP(100);
        quest.setActive(false);
        Game.getQuestList().remove(quest);
        System.out.println("Got Rewarded");
    }

    public void refreshQuestMenu(){

    }

}
