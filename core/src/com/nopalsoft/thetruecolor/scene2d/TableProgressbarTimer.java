package com.nopalsoft.thetruecolor.scene2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.nopalsoft.thetruecolor.Assets;

public class TableProgressbarTimer extends Table {
    public static float WIDTH = 450;
    public static float HEIGHT = 30;

    private float totalTime;
    private float actualTime;
    Image imageBar;

    Color color;

    public boolean timeIsOver;

    public TableProgressbarTimer(float x, float y) {
        this.setBounds(x, y, WIDTH, HEIGHT);
        imageBar = new Image(Assets.barTimer);
        addActor(imageBar);
    }

    public void initialize(Color color, float totalTime) {
        this.color = color;
        this.totalTime = totalTime;
        actualTime = 0;
        imageBar.setSize(0, 30);
        imageBar.setColor(this.color);
        timeIsOver = false;
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (!timeIsOver) {
            actualTime += Gdx.graphics.getRawDeltaTime();
            if (actualTime >= totalTime) {
                timeIsOver = true;
                actualTime = totalTime;
            }
            imageBar.setWidth(WIDTH * (actualTime / totalTime));
        }

    }

}
