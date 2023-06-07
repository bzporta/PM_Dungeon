package graphic.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.Align;
import controller.ControllerLayer;
import controller.ScreenController;
import ecs.entities.Hero;
import saveGame.SaveData;
import saveGame.SaveGame;
import starter.Game;
import tools.Constants;
import tools.Point;

import java.io.File;

import static saveGame.SaveGame.writeObject;
import static starter.Game.currentLevel;


public class PauseMenu<T extends Actor> extends ScreenController<T> {

    /** Creates a new PauseMenu with a new Spritebatch */
    public PauseMenu() {
        this(new SpriteBatch());
    }

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
                                saveGame();
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

        ScreenButton screenButton_Laden =
                new ScreenButton(
                        "Laden",
                        new Point(0, 0),
                        new TextButtonListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                File file = new File("ABC.txt");


                                SaveGame.readObject(("ABC.txt"));


                            }
                        },
                        new TextButtonStyleBuilder(FontBuilder.DEFAULT_FONT)
                                .setFontColor(Color.WHITE)
                                .build());
        screenButton_Laden.setPosition(
                (Constants.WINDOW_WIDTH) / 2f - screenText.getWidth(),
                ((Constants.WINDOW_HEIGHT) / 2f + screenButton_Laden.getHeight())-30f,
                Align.center | Align.bottom);

        add((T) screenButton_Laden, ControllerLayer.BOTTOM);

        ScreenButton screenButton_beenden =
                new ScreenButton(
                        "Beenden",
                        new Point(0, 0),
                        new TextButtonListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                saveGame();
                                System.exit(0);
                            }
                        },
                        new TextButtonStyleBuilder(FontBuilder.DEFAULT_FONT)
                                .setFontColor(Color.WHITE)
                                .build());
        screenButton_beenden.setPosition(
                (Constants.WINDOW_WIDTH) / 2f - screenText.getWidth(),
                ((Constants.WINDOW_HEIGHT) / 2f + screenButton_beenden.getHeight()) - 60f,
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

    private void saveGame(){
        SaveData save = new SaveData();
        Hero h = (Hero) starter.Game.getHero().orElseThrow();
        save.setH(h);
        save.setCurrentLevel(currentLevel);
        SaveGame.writeObject(save, "ABC.txt");
    }
}
