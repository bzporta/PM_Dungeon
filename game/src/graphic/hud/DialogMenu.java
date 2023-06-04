package graphic.hud;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.Align;
import controller.ScreenController;
import tools.Point;

public class DialogMenu<T extends Actor> extends ScreenController<T> {

    /**
     * Creates a Screencontroller with a ScalingViewport which stretches the ScreenElements on
     * resize
     *
     * @param batch the batch which should be used to draw with
     */
    public DialogMenu(SpriteBatch batch) {
        super(batch);
    }

    public DialogMenu() {
        this(new SpriteBatch());
    }

    public void createDialogMenu() {
        ScreenImage dialog = new ScreenImage("hud/Dialog Box.png", new Point(15, 0));
        dialog.setScaleX(1.5f);
        dialog.setScaleY(1.25f);
        add((T) dialog);
        ScreenInput textInput = new ScreenInput("Write in this Chatbox...", new Point(0, 0));
        add((T) textInput);
        textInput.setWidth(dialog.getWidth());
        textInput.setMaxLength(100);
        textInput.setPosition(
                dialog.getOriginX() + 250f,
                dialog.getOriginY() + textInput.getHeight(),
                Align.bottom);
        ScreenButton enterButton =
                new ScreenButton(
                        "Enter",
                        new Point(0, 0),
                        new TextButtonListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {}
                        });
        enterButton.setPosition(
                dialog.getWidth() + 150f,
                dialog.getOriginY() + textInput.getHeight(),
                Align.bottom);
        add((T) enterButton);

        ScreenButton escapeButton =
                new ScreenButton(
                        "Back",
                        new Point(0, 0),
                        new TextButtonListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                hideMenu();
                                removeDialogMenu();
                            }
                        });
        escapeButton.setPosition(dialog.getOriginX() + 65f, dialog.getHeight() - 5f, Align.bottom);
        add((T) escapeButton);
    }

    /** shows the Menu */
    public void showMenu() {
        this.forEach((Actor s) -> s.setVisible(true));
    }
    /** hides the Menu */
    public void hideMenu() {
        this.forEach((Actor s) -> s.setVisible(false));
    }

    public void removeDialogMenu() {
        this.forEach((Actor s) -> s.remove());
    }
}
