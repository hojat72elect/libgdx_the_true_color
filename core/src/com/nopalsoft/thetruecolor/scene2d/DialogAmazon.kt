package com.nopalsoft.thetruecolor.scene2d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.nopalsoft.thetruecolor.Assets;
import com.nopalsoft.thetruecolor.screens.Screens;

public class DialogAmazon extends Dialog {
    static final float WIDTH = 440;
    static final float HEIGHT = 250;

    Label labelText;
    TextButton buttonAmazonLogin;

    public DialogAmazon(Screens currentScreen) {
        super(currentScreen, WIDTH, HEIGHT, 300);

        setCloseButton(210);

        labelText = new Label(languages.get("loginToGoogle").replace("Google", "Amazon"), new LabelStyle(Assets.fontSmall, Color.BLACK));
        labelText.setWidth(getWidth() - 20);
        labelText.setFontScale(.75f);
        labelText.setWrap(true);
        labelText.setPosition(getWidth() / 2f - labelText.getWidth() / 2f, 165);

        buttonAmazonLogin = new TextButton("", new TextButtonStyle(Assets.buttonPlay, null, null, Assets.fontSmall));
        screen.addPressEffect(buttonAmazonLogin);
        buttonAmazonLogin.getLabel().setFontScale(.75f);

        buttonAmazonLogin.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (game.gameServiceHandler.isSignedIn()) {
                    game.gameServiceHandler.signOut();
                } else {
                    game.gameServiceHandler.signIn();
                }
                hide();
            }
        });

        addActor(labelText);
        addActor(buttonAmazonLogin);

    }

    @Override
    public void show(Stage stage) {
        super.show(stage);

        String textButton = languages.get("login");
        if (game.gameServiceHandler.isSignedIn())
            textButton = languages.get("logout");

        buttonAmazonLogin.setText(textButton);
        buttonAmazonLogin.pack();
        buttonAmazonLogin.setPosition(getWidth() / 2f - buttonAmazonLogin.getWidth() / 2f, 35);
    }
}