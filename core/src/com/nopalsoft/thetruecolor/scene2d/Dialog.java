package com.nopalsoft.thetruecolor.scene2d;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.I18NBundle;
import com.nopalsoft.thetruecolor.Assets;
import com.nopalsoft.thetruecolor.screens.BaseScreen;
import com.nopalsoft.thetruecolor.TrueColorGame;

public class Dialog extends Group {
    public static final float ANIMATION_DURATION = .3f;

    private final Image dim;
    protected BaseScreen screen;
    protected I18NBundle languages;
    protected TrueColorGame game;


    public Dialog(BaseScreen currentScreen, float width, float height, float positionY) {
        screen = currentScreen;
        game = currentScreen.game;
        languages = Assets.languages;
        setSize(width, height);
        setY(positionY);

        dim = new Image(Assets.pixelBlack);
        dim.setSize(BaseScreen.SCREEN_WIDTH, BaseScreen.SCREEN_HEIGHT);

        setBackGround(Assets.dialogWindow);

    }

    protected void setCloseButton(float positionY) {
        Button btClose = new Button(Assets.buttonFalse);
        btClose.setSize((float) 50, (float) 50);
        btClose.setPosition((float) 400, positionY);
        screen.addPressEffect(btClose);
        btClose.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hide();
            }
        });
        addActor(btClose);

    }

    private void setBackGround(NinePatchDrawable imageBackground) {
        Image img = new Image(imageBackground);
        img.setSize(getWidth(), getHeight());
        addActor(img);

    }

    public void show(Stage stage) {

        setOrigin(getWidth() / 2f, getHeight() / 2f);
        setX(BaseScreen.SCREEN_WIDTH / 2f - getWidth() / 2f);

        setScale(.5f);
        addAction(Actions.scaleTo(1, 1, ANIMATION_DURATION));

        dim.getColor().a = 0;
        dim.addAction(Actions.alpha(.7f, ANIMATION_DURATION));

        stage.addActor(dim);
        stage.addActor(this);

        game.reqHandler.showAdBanner();
    }

    public void hide() {
        game.reqHandler.hideAdBanner();
        addAction(Actions.sequence(Actions.scaleTo(.5f, .5f, ANIMATION_DURATION), Actions.removeActor()));
        dim.addAction(Actions.sequence(Actions.alpha(0, ANIMATION_DURATION), Actions.removeActor()));

    }

}
