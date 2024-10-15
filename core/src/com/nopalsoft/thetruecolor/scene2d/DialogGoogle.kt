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

public class DialogGoogle extends Dialog {
    static final float WIDTH = 440;
    static final float HEIGHT = 250;

    Label lbText;
    TextButton btGoogleLogin;

    public DialogGoogle(Screens currentScreen) {
        super(currentScreen, WIDTH, HEIGHT, 300);

        setCloseButton(210);

        lbText = new Label(languages.get("loginToGoogle"), new LabelStyle(Assets.fontSmall, Color.BLACK));
        lbText.setWidth(getWidth() - 20);
        lbText.setFontScale(.75f);
        lbText.setWrap(true);
        lbText.setPosition(getWidth() / 2f - lbText.getWidth() / 2f, 140);

        btGoogleLogin = new TextButton("", new TextButtonStyle(Assets.buttonGoogleText, null, null, Assets.fontSmall));
        screen.addPressEffect(btGoogleLogin);
        btGoogleLogin.getLabel().setFontScale(.75f);

        btGoogleLogin.addListener(new ClickListener() {
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

        addActor(lbText);
        addActor(btGoogleLogin);

    }

    @Override
    public void show(Stage stage) {
        super.show(stage);

        String textButton = languages.get("login");
        if (game.gameServiceHandler.isSignedIn())
            textButton = languages.get("logout");

        btGoogleLogin.setText(textButton);
        btGoogleLogin.pack();
        btGoogleLogin.setPosition(getWidth() / 2f - btGoogleLogin.getWidth() / 2f, 35);
    }
}