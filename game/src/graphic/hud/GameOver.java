package graphic.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.Align;
import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static logging.LoggerConfig.initBaseLogger;
import java.io.IOException;
import java.util.Optional;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import controller.ScreenController;
import ecs.entities.Entity;
import ecs.entities.Hero;
import starter.Game;
import tools.Constants;
import tools.Point;

public class GameOver<T extends Actor> extends ScreenController<T> {

    /** Creates a new PauseMenu with a new Spritebatch */
    public GameOver() {
        this(new SpriteBatch());
    }

    /** Creates a new GameOverMenu with a given Spritebatch */
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
        ScreenButton screenButton_neustart =
            new ScreenButton(
                "Neustart",
                new Point(0, 0),
                new TextButtonListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        //Lade neues Level
                        //Game.getInstance().setScreen(new GameScreen());
                        Game game = Game.getGame();
                        Hero hero = new Hero();
                        game.setHero(hero);
                        game.onLevelLoad();
                        starter.Game.getGameOverMenu().hideMenu();
                    }
                },
                new TextButtonStyleBuilder(FontBuilder.DEFAULT_FONT)
                    .setFontColor(Color.WHITE)
                    .build()

            );
        screenButton_neustart.setPosition(
                (Constants.WINDOW_WIDTH) / 2f - screenButton_neustart.getWidth(),
                (Constants.WINDOW_HEIGHT) / 2f + screenButton_neustart.getHeight(),
                Align.center | Align.bottom);


        add((T) screenButton_neustart);

        ScreenButton screenButton_beenden =
            new ScreenButton(
                "Beenden",
                new Point(0, 0),
                new TextButtonListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {

                        System.exit(0);
                    }
                },
                new TextButtonStyleBuilder(FontBuilder.DEFAULT_FONT)
                    .setFontColor(Color.WHITE)
                    .build()

            );
        screenButton_beenden.setPosition(
                (Constants.WINDOW_WIDTH) / 2f + screenButton_beenden.getWidth(),
                (Constants.WINDOW_HEIGHT) / 2f + screenButton_beenden.getHeight(),
                Align.center | Align.bottom);


        add((T) screenButton_beenden);
        //hideMenu();
    }

    /** shows the Menu */
    public void showMenu() {
        System.out.println("showMenu");
        this.forEach((Actor s) -> s.setVisible(true));

    }

    /** hides the Menu */
    public void hideMenu() {
        this.forEach((Actor s) -> s.setVisible(false));
    }
}
