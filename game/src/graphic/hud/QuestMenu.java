package graphic.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import controller.ScreenController;
import tools.Constants;
import tools.Point;

/** QuestMenu class */
public class QuestMenu<T extends Actor> extends ScreenController<T> {

    private ScreenText screenText_quest;
    private ScreenText screenText_description;
    private int questcounter = 0;
    /** Creates a new QuestMenu with a new Spritebatch */
    public QuestMenu() {
        this(new SpriteBatch());
    }

    /** Creates a new QuestMenu with a given Spritebatch */
    public QuestMenu(SpriteBatch batch) {

        super(batch);
        screenText_description =
                new ScreenText(
                        "Quests:",
                        new Point(
                                (Constants.WINDOW_WIDTH) / 1.45f, (Constants.WINDOW_HEIGHT) / 1.1f),
                        7,
                        new LabelStyleBuilder(FontBuilder.DEFAULT_FONT)
                                .setFontcolor(Color.RED)
                                .build());
        screenText_description.setFontScale(1.5f);
        add((T) screenText_description);
        hideMenu();
    }

    /** shows the Menu */
    public void showMenu() {
        this.forEach((Actor s) -> s.setVisible(true));
    }

    /** hides the Menu */
    public void hideMenu() {
        this.forEach((Actor s) -> s.setVisible(false));
    }

    /**
     * adds a new Quest to the QuestMenu
     *
     * @param name the name of the Quest
     * @param status the status of the Quest
     * @return the ScreenText of the Quest
     */
    public ScreenText addActiveQuest(String name, String status) {
        questcounter++;
        screenText_quest =
                new ScreenText(
                        name + " " + status,
                        new Point(
                                screenText_description.getX(),
                                (Constants.WINDOW_HEIGHT) / (1.1f + 0.08f * questcounter)),
                        7,
                        new LabelStyleBuilder(FontBuilder.DEFAULT_FONT)
                                .setFontcolor(Color.RED)
                                .build());
        screenText_quest.setFontScale(1f);

        add((T) screenText_quest);
        return screenText_quest;
    }

    /** Decreases the Questcounter when a Quest gets removed */
    public void decreaseQuestcounter(){
        questcounter--;
    }
}
