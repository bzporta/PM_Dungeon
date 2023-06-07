package graphic.hud;

import static starter.Game.*;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.Align;
import controller.ControllerLayer;
import controller.ScreenController;
import java.util.logging.Logger;
import saveGame.SaveGame;
import starter.Game;
import tools.Constants;
import tools.Point;

public class PauseMenu<T extends Actor> extends ScreenController<T> {

    /** Creates a new PauseMenu with a new Spritebatch */
    public PauseMenu() {
        this(new SpriteBatch());
    }

    private Logger logger;

    /** Creates a new PauseMenu with a given Spritebatch */
    public PauseMenu(SpriteBatch batch) {
        super(batch);
        ScreenText screenText =
                new ScreenText(
                        "Paused",
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
        ScreenButton screenButton_neustart =
                new ScreenButton(
                        "Speichern",
                        new Point(0, 0),
                        new TextButtonListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                starter.Game.getGame().saveGame();
                            }
                        },
                        new TextButtonStyleBuilder(FontBuilder.DEFAULT_FONT)
                                .setFontColor(Color.WHITE)
                                .build());
        screenButton_neustart.setPosition(
                (Constants.WINDOW_WIDTH) / 2f - screenText.getWidth(),
                (Constants.WINDOW_HEIGHT) / 2f + screenButton_neustart.getHeight(),
                Align.center | Align.bottom);

        add((T) screenButton_neustart, ControllerLayer.BOTTOM);
        logger = Logger.getLogger(PauseMenu.class.getName());
        ScreenButton screenButton_Laden =
                new ScreenButton(
                        "Laden",
                        new Point(0, 0),
                        new TextButtonListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                if (!SaveGame.isLoaded()) {
                                    Game.getEntities().clear();
                                    Game.getQuestList().clear();
                                    SaveGame.readObject("SavedGame.txt");
                                    SaveGame.setIsLoaded(true);
                                } else {
                                    logger.warning("Game can only be loaded once a level!");
                                }
                            }
                        },
                        new TextButtonStyleBuilder(FontBuilder.DEFAULT_FONT)
                                .setFontColor(Color.WHITE)
                                .build());
        screenButton_Laden.setPosition(
                (Constants.WINDOW_WIDTH) / 2f - screenText.getWidth(),
                ((Constants.WINDOW_HEIGHT) / 2f + screenButton_Laden.getHeight()) - 50f,
                Align.center | Align.bottom);

        add((T) screenButton_Laden, ControllerLayer.BOTTOM);

        ScreenButton screenButton_beenden =
                new ScreenButton(
                        "Beenden",
                        new Point(0, 0),
                        new TextButtonListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                starter.Game.getGame().saveGame();
                                System.exit(0);
                            }
                        },
                        new TextButtonStyleBuilder(FontBuilder.DEFAULT_FONT)
                                .setFontColor(Color.WHITE)
                                .build());
        screenButton_beenden.setPosition(
                (Constants.WINDOW_WIDTH) / 2f - screenText.getWidth(),
                ((Constants.WINDOW_HEIGHT) / 2f + screenButton_beenden.getHeight()) - 100f,
                Align.center | Align.bottom);

        add((T) screenButton_beenden, ControllerLayer.BOTTOM);

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
}
