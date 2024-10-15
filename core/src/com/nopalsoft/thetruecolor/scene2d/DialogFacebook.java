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

public class DialogFacebook extends Dialog {
    static final float WIDTH = 440;
    static final float HEIGHT = 250;

    Label labelText;
    TextButton buttonFacebookLogin;

    public DialogFacebook(Screens currentScreen) {
        super(currentScreen, WIDTH, HEIGHT, 300);

        setCloseButton(210);

        labelText = new Label(languages.get("loginToFacebook"), new LabelStyle(Assets.fontSmall, Color.BLACK));
        labelText.setWidth(getWidth() - 20);
        labelText.setFontScale(.75f);
        labelText.setWrap(true);
        labelText.setPosition(getWidth() / 2f - labelText.getWidth() / 2f, 140);

        buttonFacebookLogin = new TextButton("", new TextButtonStyle(Assets.buttonFacebookText, null, null, Assets.fontSmall));
        screen.addPressEffect(buttonFacebookLogin);
        buttonFacebookLogin.getLabel().setFontScale(.75f);

        buttonFacebookLogin.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (game.facebookHandler.facebookIsSignedIn()) {
                    game.facebookHandler.facebookSignOut();
                } else {
                    game.facebookHandler.facebookSignIn();
                }
                hide();
            }
        });

        addActor(labelText);
        addActor(buttonFacebookLogin);

    }

    @Override
    public void show(Stage stage) {
        super.show(stage);

        String textButton = languages.get("login");
        if (game.facebookHandler.facebookIsSignedIn())
            textButton = languages.get("logout");

        buttonFacebookLogin.setText(textButton);
        buttonFacebookLogin.pack();
        buttonFacebookLogin.setPosition(getWidth() / 2f - buttonFacebookLogin.getWidth() / 2f, 35);
    }
}