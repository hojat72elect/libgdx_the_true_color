package com.nopalsoft.thetruecolor.scene2d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.nopalsoft.thetruecolor.Assets;
import com.nopalsoft.thetruecolor.game.GameScreen;
import com.nopalsoft.thetruecolor.screens.Screens;

public class CountDown extends Group {

    Image imageOne, imageTwo, imageThree;
    GameScreen gameScreen;
    Label labelText;

    float timeByNumber = 1.25f;

    public CountDown(GameScreen screen) {
        setBounds(0, 0, Screens.SCREEN_WIDTH, Screens.SCREEN_HEIGHT);
        gameScreen = screen;

        labelText = new Label(Assets.languages.get("verdaderoFalse"), new LabelStyle(Assets.fontSmall, Color.BLACK));
        labelText.setFontScale(1.2f);
        labelText.setPosition(getWidth() / 2f - labelText.getWidth() * labelText.getFontScaleX() / 2f, 300);

        imageOne = new Image(Assets.one);
        imageOne.setPosition(getWidth() / 2f - imageOne.getWidth() / 2f, 500);

        imageTwo = new Image(Assets.two);
        imageTwo.setPosition(getWidth() / 2f - imageTwo.getWidth() / 2f, 500);

        imageThree = new Image(Assets.three);
        imageThree.setPosition(getWidth() / 2f - imageThree.getWidth() / 2f, 500);

        Runnable runAfterThree = new Runnable() {

            @Override
            public void run() {
                imageThree.remove();
                addActor(imageTwo);
            }
        };
        imageThree.addAction(Actions.sequence(Actions.fadeOut(timeByNumber), Actions.run(runAfterThree)));

        Runnable runAfterTwo = new Runnable() {

            @Override
            public void run() {
                imageTwo.remove();
                addActor(imageOne);
            }
        };
        imageTwo.addAction(Actions.sequence(Actions.fadeOut(timeByNumber), Actions.run(runAfterTwo)));

        Runnable runAfterOne = new Runnable() {

            @Override
            public void run() {
                imageOne.remove();
                gameScreen.setRunning();
                remove();

            }
        };
        imageOne.addAction(Actions.sequence(Actions.fadeOut(timeByNumber), Actions.run(runAfterOne)));

        addActor(imageThree);
        addActor(labelText);

    }
}
