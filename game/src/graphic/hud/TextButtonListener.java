package graphic.hud;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.io.Serializable;

/** This <code>ClickListener</code> should be implemented for all <code>ScreenButton</code>s. */
public abstract class TextButtonListener extends ClickListener {
    @Override
    public abstract void clicked(InputEvent event, float x, float y);
}
