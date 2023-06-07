package ecs.systems;

import ecs.entities.Hero;
import ecs.quests.Quest;
import starter.Game;

/** QuestSystem */
public class QuestSystem extends ECS_System {

    /** Checks if a quest is done and sets the reward */
    @Override
    public void update() {
        Game.getQuestList().stream().filter(q -> q.isActive()).forEach(this::refreshQuestMenu);

        Game.getQuestList().stream()
                .filter(q -> q.isActive())
                .filter(q -> q.checkIfDone())
                .forEach(this::setReward);
    }

    /** Sets the reward for a quest
     *
     *  @param quest the quest to set the reward for
     *
     *  Adds 100Xp to the hero and sets the quest to inactive and finished
     * */
    public void setReward(Quest quest) {
        Hero hero = (Hero) Game.getHero().orElseThrow();
        hero.getXP().addXP(100);
        quest.setActive(false);
        quest.setFinished(true);
    }

    /** Refreshes the quest menu
     *
     *  @param quest the quest to refresh the menu for
     *
     *  Refreshes the quest menu with the name and status of the quest
     * */
    public void refreshQuestMenu(Quest quest) {
        quest.getScreenText().setText(quest.getName() + " " + quest.getStatus());
    }
}
