package com.nopalsoft.thetruecolor.scene2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.nopalsoft.thetruecolor.Assets;
import com.nopalsoft.thetruecolor.screens.Screens;

public class DialogMoreLanguages extends Dialog {
    static final float WIDTH = 440;
    static final float HEIGHT = 250;

    Label labelText;
    TextButton textButtonTranslate;

    public DialogMoreLanguages(Screens currentScreen) {
        super(currentScreen, WIDTH, HEIGHT, 300);

        setCloseButton(210);

        labelText = new Label(Assets.languages.get("translateDescription"), new Label.LabelStyle(Assets.fontSmall, Color.BLACK));
        labelText.setWidth(getWidth() - 20);
        labelText.setFontScale(.75f);
        labelText.setWrap(true);
        labelText.setPosition(getWidth() / 2f - labelText.getWidth() / 2f, getHeight() / 2f - labelText.getHeight() / 2f + 30);

        textButtonTranslate = new TextButton(Assets.languages.get("translate"), Assets.textButtonStyle);
        screen.addEfectoPress(textButtonTranslate);
        textButtonTranslate.getLabel().setFontScale(.75f);

        textButtonTranslate.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                textButtonTranslate.setChecked(false);
                Gdx.net.openURI("https://webtranslateit.com/en/projects/10553-The-true-color/invitation_request");
                hide();
            }
        });


        textButtonTranslate.pack();
        textButtonTranslate.setPosition(getWidth() / 2f - textButtonTranslate.getWidth() / 2f, 35);

        addActor(labelText);
        addActor(textButtonTranslate);

    }
}
