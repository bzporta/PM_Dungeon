package ecs.systems;

import ecs.entities.Hero;
import ecs.quests.Quest;
import starter.Game;

public class QuestSystem extends ECS_System {

    @Override
    public void update() {
        Game.getQuestList().stream().filter(q -> q.isActive()).forEach(this::refreshQuestMenu);

        Game.getQuestList().stream()
                .filter(q -> q.isActive())
                .filter(q -> q.checkIfDone())
                .forEach(this::setReward);
    }

    public void setReward(Quest quest) {
        Hero hero = (Hero) Game.getHero().orElseThrow();
        hero.getXP().addXP(100);
        quest.setActive(false);
        quest.setFinished(true);
        System.out.println("Got Rewarded");
    }

    public void refreshQuestMenu(Quest quest) {
        quest.getScreenText().setText(quest.getName() + " " + quest.getStatus());
    }
}
