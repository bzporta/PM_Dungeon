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
    //Hero hero = (Hero) Game.getHero().orElseThrow();
    Integer level = 3;

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
                "Level Up!" +
                    "Neues Level:" + level,
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
        add((T) screenText, ControllerLayer.TOP);
        ScreenButton screenButton_HealSkill =
            new ScreenButton(
                "Healskill",
                new Point(0, 0),
                new TextButtonListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        Hero hero = (Hero) starter.Game.getHero().orElseThrow();
                        hero.setHealSkill();
                        starter.Game.getGame().toggleSkillMenu();
                    }
                },
                new TextButtonStyleBuilder(FontBuilder.DEFAULT_FONT)
                    .setFontColor(Color.WHITE)
                    .build()
            );
        screenButton_HealSkill.setPosition(
            (Constants.WINDOW_WIDTH) / 2f - screenButton_HealSkill.getWidth(),
            (Constants.WINDOW_HEIGHT) / 2f + screenButton_HealSkill.getHeight(),
            Align.center | Align.bottom);


        add((T) screenButton_HealSkill, ControllerLayer.TOP);

        ScreenButton screenButton_FreezeSkill =
            new ScreenButton(
                "FreezeSkill",
                new Point(0, 0),
                new TextButtonListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        Hero hero = (Hero) starter.Game.getHero().orElseThrow();
                        hero.setFreezeSkill();
                        starter.Game.getGame().toggleSkillMenu();
                    }
                },
                new TextButtonStyleBuilder(FontBuilder.DEFAULT_FONT)
                    .setFontColor(Color.WHITE)
                    .build()

            );
        screenButton_FreezeSkill.setPosition(
            (Constants.WINDOW_WIDTH) / 2f + screenButton_FreezeSkill.getWidth(),
            (Constants.WINDOW_HEIGHT) / 2f + screenButton_FreezeSkill.getHeight(),
            Align.center | Align.bottom);


        add((T) screenButton_FreezeSkill);
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
