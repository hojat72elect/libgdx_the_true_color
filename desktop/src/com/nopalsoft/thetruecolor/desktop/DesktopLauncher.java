package com.nopalsoft.thetruecolor.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.nopalsoft.thetruecolor.TrueColorGame;

public class DesktopLauncher {
    public static TrueColorGame game;

    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 480;
        config.height = 800;
        game = new TrueColorGame();
        new LwjglApplication(game, config);
    }
}
