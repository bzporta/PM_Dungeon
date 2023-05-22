package graphic.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.Align;
import controller.ControllerLayer;
import controller.ScreenController;
import starter.Game;
import tools.Constants;
import tools.Point;

/** GameOverMenu is a ScreenController that is shown when the player dies */
public class GameOver<T extends Actor> extends ScreenController<T> {

    TextButtonListener tb = new TextButtonListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {

            System.exit(0);
        }
    };

    /** Creates a new GameOverMenu with a new Spritebatch */
    public GameOver() {
        this(new SpriteBatch());
    }

    /** Creates a new GameOverMenu with a given Spritebatch
     * @param batch Spritebatch to use
     * */
    public GameOver(SpriteBatch batch) {
        super(batch);
    }

    /** shows the Menu */
    public void showMenu() {
        this.forEach((Actor s) -> s.setVisible(true));

    }
    /** hides the Menu */
    public void hideMenu() {
        this.forEach((Actor s) -> s.setVisible(false));
    }

    public void removeGameOverMenu() {
        this.forEach((Actor s) -> s.remove());
    }

    public GameOver getGameOver() {
        return this;
    }

    public void createGameOverMenue(){
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
        add((T) screenText, ControllerLayer.BOTTOM);
        ScreenButton screenButton_neustart =
            new ScreenButton(
                "Neustart",
                new Point(0, 0),
                new TextButtonListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        //Lade neues Level
                        Game.restartGame();
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


        add((T) screenButton_neustart, ControllerLayer.BOTTOM);

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


        add((T) screenButton_beenden, ControllerLayer.BOTTOM);
    }
}
