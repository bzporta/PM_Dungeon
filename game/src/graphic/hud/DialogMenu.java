package graphic.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.Align;
import controller.ScreenController;
import ecs.entities.Hero;
import ecs.systems.ECS_System;
import java.util.Arrays;
import java.util.LinkedList;
import starter.Game;
import tools.Point;

/**
 * DialogMenu class
 */
public class DialogMenu<T extends Actor> extends ScreenController<T> {

    /** Creates a new DialogMenu with a new Spritebatch */
    public DialogMenu(SpriteBatch batch) {
        super(batch);
    }

    /** Creates a new DialogMenu and creates a new SpriteBatch */
    public DialogMenu() {
        this(new SpriteBatch());
    }

    private ScreenInput textInput;
    private ScreenText text;
    private boolean isOpen = false;
    private final String question1 =
            "Du bist mein Sohn, aber ich bin nicht dein Vater. Wer hat das gesagt?";
    private final String question2 =
            """
            Ich bin eine Datenstruktur, die nach dem Last-In-First-Out-Prinzip funktioniert.
            Elemente werden in mich eingefügt und wieder entfernt,
            wobei immer das zuletzt eingefügte Element als erstes entfernt wird. Was bin ich?""";
    private LinkedList<String> questions = new LinkedList<>(Arrays.asList(question1, question2));
    ;
    private final String questList =
            "1: Grave-Quest: Activate 10 Gravestones \n" + "2: Bounthunter-Quest: Kill 10 Monsters";
    private final String controll = "Rätsel ??? Quest ??? ... ???";

    /** Creates the DialogMenu
     *
     * The DialogMenu is a Menu where the Player can write in a Textfield. The player is able to choose a Quest or a Riddle.
     */
    public void createDialogMenu() {

        ScreenImage dialog = new ScreenImage("hud/Dialog Box.png", new Point(15, 0));
        dialog.setScaleX(1.5f);
        dialog.setScaleY(1.25f);
        add((T) dialog);
        textInput = new ScreenInput("Write in this Chatbox", new Point(0, 0));
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
                            public void clicked(InputEvent event, float x, float y) {
                                checkAnswer();
                            }
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
        text =
                new ScreenText(
                        controll,
                        new Point(0, 0),
                        1f,
                        new LabelStyleBuilder(FontBuilder.DEFAULT_FONT)
                                .setFontcolor(Color.PINK)
                                .build());
        text.setPosition(
                dialog.getOriginX() + 50f,
                dialog.getHeight() - escapeButton.getHeight() - 15f,
                Align.left);
        add((T) text);
    }

    /** shows the Menu */
    public void showMenu() {
        isOpen = true;
        this.forEach((Actor s) -> s.setVisible(true));
    }
    /** hides the Menu */
    public void hideMenu() {
        if (Game.systems != null) {
            Game.systems.forEach(ECS_System::toggleRun);
        }
        isOpen = false;
        this.forEach((Actor s) -> s.setVisible(false));
    }

    /** removes the Menu */
    public void removeDialogMenu() {
        this.forEach((Actor s) -> s.remove());
    }

    /** checks the Answer of the Player
     *
     * If the Player writes in the Textfield, the answer will be checked with RegEx. If the answer is correct, the Player gets a reward.
     *
     * The first if-statement checks if the player wants to do a riddle or a quest. The switch-case-statement checks the answers of the riddles.
     */
    public void checkAnswer() {

        if (textInput.getText().matches("[rR]([äÄ]|[aA][eE])[tT][sS][eE][lL]")) {
            if (!
                    questions.isEmpty()) {
                text.setText(questions.peek());
                questions.pop();
            } else {
                text.setText("...........???????????");
            }
        } else if (textInput.getText().matches("[qQ][uU][eE][sS][tT]")) {
            text.setText(questList);
        }

        switch (text.getText().toString()) {
            case question1 -> {
                if (textInput.getText().matches("[mM][uU][tT]{2}[eE][rR]")) {
                    text.setText("Korrekt");
                    getReward();
                }
            }
            case question2 -> {
                if (textInput.getText().matches("[sS][tT][aA][cC][kK]")) {
                    text.setText("Korrekt");
                    getReward();
                }
            }
            case questList -> {
                if (textInput.getText().matches("1")) {
                    starter.Game.getGraveQuest().activateQuest();
                } else if (textInput.getText().matches("2")) {
                    starter.Game.getKillQuest().activateQuest();
                }
            }
        }
    }

    /** gives the Player a reward
     *
     * The Player gets 50 XP.
     */
    public void getReward() {
        Hero hero = (Hero) starter.Game.getHero().orElseThrow();
        hero.getXP().addXP(50);
    }

    /** Returns true if the Menu is open
     *
     * @return isOpen true if the Menu is open
     * */
    public boolean getIsOpen() {
        return isOpen;
    }
}
