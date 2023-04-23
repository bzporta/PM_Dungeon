package graphic.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.Align;
import controller.ScreenController;
import tools.Constants;
import tools.Point;

public class GameOver<T extends Actor> extends ScreenController<T> {

    /** Creates a new PauseMenu with a new Spritebatch */
    public GameOver() {
        this(new SpriteBatch());
    }

    /** Creates a new PauseMenu with a given Spritebatch */
    public GameOver(SpriteBatch batch) {
        super(batch);
        ScreenText screenText =
                new ScreenText(
                        "Game Over",
                        new Point(0, 0),
                        3,
                        new LabelStyleBuilder(FontBuilder.DEFAULT_FONT)
                                .setFontcolor(Color.RED)
                                .build());
        screenText.setFontScale(3);
        screenText.setPosition(
                (Constants.WINDOW_WIDTH) / 2f - screenText.getWidth(),
                (Constants.WINDOW_HEIGHT) / 1.5f + screenText.getHeight(),
                Align.center | Align.bottom);
        add((T) screenText);
        ScreenButton screenButton =
            new ScreenButton(
                "Neustart",
                new Point(0, 0),
                new TextButtonListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {

                    }
                }
            );
        screenButton.setScale(2.0f);

        screenButton.setColor(new Color(3.0f, 2.0f, 2.0f, 1.0f));
        add((T) screenButton);

        //hideMenu();
    }

    /** shows the Menu */
    public void showMenu() {
        this.forEach((Actor s) -> s.setVisible(true));
    }

    /** hides the Menu */
    public void hideMenu() {
        this.forEach((Actor s) -> s.setVisible(false));
    }
}
