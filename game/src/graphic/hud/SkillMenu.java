package graphic.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.Align;
import controller.ControllerLayer;
import controller.ScreenController;
import ecs.entities.Hero;
import starter.Game;
import tools.Constants;
import tools.Point;

public class SkillMenu
    <T extends Actor> extends ScreenController<T> {



    /** Creates a new SkillMenu
     *  with a new Spritebatch */
    public SkillMenu
    () {
        this(new SpriteBatch());
    }

    /** Creates a new SkillMenu
     *  with a given Spritebatch */
    public SkillMenu
    (SpriteBatch batch) {
        super(batch);
        ScreenText screenText =
            new ScreenText(
                "Level Up!",
                new Point(0, 0),
                3,
                new LabelStyleBuilder(FontBuilder.DEFAULT_FONT)
                    .setFontcolor(Color.GREEN)
                    .build());
        screenText.setFontScale(3);
        screenText.setPosition(
            (Constants.WINDOW_WIDTH) / 2f - screenText.getWidth(),
            (Constants.WINDOW_HEIGHT) / 1.5f + screenText.getHeight(),
            Align.center | Align.bottom);
        add((T) screenText);
        ScreenText screenText_HealSkill =
            new ScreenText(
                "Healskill \n" +
                "Press H to unlock/upgrade",
                new Point(0, 0),
                3,
                new LabelStyleBuilder(FontBuilder.DEFAULT_FONT)
                    .setFontcolor(Color.GREEN)
                    .build());

        screenText_HealSkill.setPosition(
            (Constants.WINDOW_WIDTH) / 2f - screenText_HealSkill.getWidth(),
            (Constants.WINDOW_HEIGHT) / 2f + screenText_HealSkill.getHeight(),
            Align.center | Align.bottom);
        add((T) screenText_HealSkill);
        ScreenText screenText_IceBallSkill =
            new ScreenText(
                "IceballSkill \n" +
                    "Press J to unlock/upgrade",
                new Point(0, 0),
                3,
                new LabelStyleBuilder(FontBuilder.DEFAULT_FONT)
                    .setFontcolor(Color.GREEN)
                    .build());

        screenText_IceBallSkill.setPosition(
            (Constants.WINDOW_WIDTH) / 2f + screenText_IceBallSkill.getWidth(),
            (Constants.WINDOW_HEIGHT) / 2f + screenText_IceBallSkill.getHeight(),
            Align.center | Align.bottom);
        add((T) screenText_IceBallSkill);

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
