package graphic.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import controller.ScreenController;
import tools.Constants;
import tools.Point;

public class QuestMenu<T extends Actor> extends ScreenController<T> {

    private ScreenText screenText_quest1;
    private ScreenText screenText_quest2;
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
                        "Active Quests:",
                        new Point(0, 0),
                        7,
                        new LabelStyleBuilder(FontBuilder.DEFAULT_FONT)
                                .setFontcolor(Color.RED)
                                .build());
        screenText_description.setFontScale(1.5f);
        screenText_description.setPosition(
                (Constants.WINDOW_WIDTH) / 1.1f - screenText_description.getWidth(),
                (Constants.WINDOW_HEIGHT) / 1.1f + screenText_description.getHeight(),
                Align.top);
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

    public void addActiveQuest(String name, int current, int max) {
        questcounter++;
        screenText_quest1 =
                new ScreenText(
                        name + " " + current + "/" + max,
                        new Point(0, 0),
                        7,
                        new LabelStyleBuilder(FontBuilder.DEFAULT_FONT)
                                .setFontcolor(Color.RED)
                                .build());
        screenText_quest1.setFontScale(1f);
        screenText_quest1.setPosition(
                (Constants.WINDOW_WIDTH) / 1.1f - screenText_quest1.getWidth(),
                (Constants.WINDOW_HEIGHT) / (1.1f + 0.1f * questcounter)
                        + screenText_quest1.getHeight(),
                Align.bottom);
        add((T) screenText_quest1);
    }

}
