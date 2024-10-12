package com.nopalsoft.thetruecolor;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.nopalsoft.thetruecolor.handlers.FacebookHandler;
import com.nopalsoft.thetruecolor.handlers.GameServicesHandler;
import com.nopalsoft.thetruecolor.handlers.HandlerGWT;
import com.nopalsoft.thetruecolor.handlers.HandlerGWT.OnTextureLoaded;
import com.nopalsoft.thetruecolor.handlers.RequestHandler;
import com.nopalsoft.thetruecolor.leaderboard.Person;
import com.nopalsoft.thetruecolor.screens.MainMenuScreen;
import com.nopalsoft.thetruecolor.screens.Screens;
import java.util.Iterator;

public class TrueColorGame extends Game {
    public Array<Person> arrPerson;

    public final GameServicesHandler gameServiceHandler;
    public final RequestHandler reqHandler;
    public final FacebookHandler facebookHandler;
    public final HandlerGWT handlerGWT;

    public TrueColorGame(RequestHandler reqHandler,
                         GameServicesHandler gameServiceHandler,
                         FacebookHandler facebookHandler,
                         HandlerGWT handlerGWT) {
        this.reqHandler = reqHandler;
        this.gameServiceHandler = gameServiceHandler;
        this.facebookHandler = facebookHandler;
        this.handlerGWT = handlerGWT;
    }

    public Stage stage;
    public SpriteBatch batcher;

    @Override
    public void create() {

        stage = new Stage(new StretchViewport(Screens.SCREEN_WIDTH, Screens.SCREEN_HEIGHT));
        batcher = new SpriteBatch();

        Settings.load();
        Assets.load();
        com.nopalsoft.thetruecolor.Achievements.init(this);
        setScreen(new MainMenuScreen(this));

    }

    public void setArrayPerson(Array<Person> _arrPerson) {
        if (arrPerson == null) {
            arrPerson = _arrPerson;
        } else {
            for (Person oPerson : _arrPerson) {
                if (!arrPerson.contains(oPerson, false))// false to compare by equals which I already overwrote.
                    arrPerson.add(oPerson);
                else {
                    arrPerson.get(arrPerson.indexOf(oPerson, false)).updateData(oPerson.name, oPerson.score);
                }
            }
        }

        for (Person oPerson : arrPerson) {
            getPersonPhoto(oPerson);
        }

        // If I'm not in the main menu, it doesn't update.
        if (getScreen() instanceof MainMenuScreen) {
            MainMenuScreen oScreen = (MainMenuScreen) getScreen();
            oScreen.updateLeaderboard();
        }

    }

    private void getPersonPhoto(final Person oPerson) {
        handlerGWT.getTextureFromFacebook("https://picsum.photos/200", new OnTextureLoaded() {
            @Override
            public void onTextureLoaded(Texture texture) {
                oPerson.setPicture(new TextureRegionDrawable(new TextureRegion(texture)));
            }
        });
    }

    /**
     * Called when the Facebook session is closed, removes all Facebook users from the table.
     */
    public void removeFromArray(com.nopalsoft.thetruecolor.leaderboard.Person.AccountType cuenta) {
        if (arrPerson == null)
            return;

        Iterator<Person> i = arrPerson.iterator();
        while (i.hasNext()) {
            Person obj = i.next();
            if (obj.accountType == cuenta)
                i.remove();
        }

        // If I'm not in the main menu, it won't update.
        if (getScreen() instanceof MainMenuScreen) {
            MainMenuScreen oScreen = (MainMenuScreen) getScreen();
            oScreen.updateLeaderboard();
        }
    }
}
