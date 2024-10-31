package com.nopalsoft.thetruecolor.leaderboard;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.nopalsoft.thetruecolor.Assets;
import com.nopalsoft.thetruecolor.screens.MainMenuScreen;
import com.nopalsoft.thetruecolor.screens.BaseScreen;
import com.nopalsoft.thetruecolor.scene2d.DialogFacebook;
import com.nopalsoft.thetruecolor.scene2d.DialogAmazon;
import com.nopalsoft.thetruecolor.scene2d.DialogGoogle;
import com.nopalsoft.thetruecolor.TrueColorGame;



public class DialogRanking extends Group {
    public static final float WIDTH = 400;
    public static final float HEIGHT = 385;

    MainMenuScreen menuScreen;
    TrueColorGame game;

    Label rankingTitle;

    Button btFacebook;
    Button btGoogle;

    DialogFacebook dialogFacebook;
    DialogGoogle dialogGoogle;
    DialogAmazon dialogAmazon;

    Table container;

    public DialogRanking(MainMenuScreen screen) {
        menuScreen = screen;
        game = screen.game;
        setBounds(BaseScreen.SCREEN_WIDTH / 2f - WIDTH / 2f, 210, WIDTH, HEIGHT);
        setBackground(Assets.dialogRanking);

        rankingTitle = new Label(Assets.languages.get("ranking"), new Label.LabelStyle(Assets.fontSmall, Color.WHITE));
        rankingTitle.setPosition(15, 328);

        dialogFacebook = new DialogFacebook(screen);
        dialogGoogle = new DialogGoogle(screen);
        dialogAmazon = new DialogAmazon(screen);

        btFacebook = new Button(Assets.buttonFacebook);

        menuScreen.addPressEffect(btFacebook);
        btFacebook.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dialogFacebook.show(getStage());
            }
        });

        TextureRegionDrawable btLoginKeyFrame = Assets.buttonGoogle;


        btGoogle = new Button(btLoginKeyFrame);

        menuScreen.addPressEffect(btGoogle);
        btGoogle.addListener(new ClickListener() {
        });

        Table tableSocial = new Table();
        tableSocial.setSize(130, 50);
        tableSocial.setPosition(255, 328);
        tableSocial.defaults().expandX().size(50).right();
        tableSocial.add(btFacebook);

        if (Gdx.app.getType() != ApplicationType.WebGL && Gdx.app.getType() != ApplicationType.iOS) {
            tableSocial.add(btGoogle);
        }

        addActor(rankingTitle);
        addActor(tableSocial);

        container = new Table();

        ScrollPane scroll = new ScrollPane(container);
        scroll.setSize(WIDTH, 320);
        scroll.setPosition(0, 0);

        addActor(scroll);

        container.top();
    }

    private void setBackground(NinePatchDrawable dialogRanking) {
        Image imageDialogRanking = new Image(dialogRanking);
        imageDialogRanking.setSize(getWidth(), getHeight());
        addActor(imageDialogRanking);

    }

    public void addPerson(Person person) {
        container.add(person);
        container.row();
    }

    public void clearLeaderboard() {
        container.clear();
    }
}
