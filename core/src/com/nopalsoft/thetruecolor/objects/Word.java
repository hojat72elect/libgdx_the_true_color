package com.nopalsoft.thetruecolor.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.nopalsoft.thetruecolor.Assets;
import com.nopalsoft.thetruecolor.Settings;
import com.nopalsoft.thetruecolor.scene2d.DialogHelpSettings.Languages;
import com.nopalsoft.thetruecolor.screens.Screens;

public class Word {
    public static final int COLOR_BLUE = 0;
    public static final int COLOR_CYAN = 1;
    public static final int COLOR_GREEN = 2;
    public static final int COLOR_YELLOW = 3;

    /**
     * The color of the word.
     */
    public int color;

    /**
     * What the word compare says with the table above.
     */
    public int text;

    public Label image;

    public Word() {
        image = new Label("", new LabelStyle(Assets.fontExtraLarge, Color.WHITE));
    }

    public void init() {
        color = MathUtils.random(0, 7);

        // 35% chance that what the word says and its color are the same.
        if (MathUtils.randomBoolean(.35f)) {
            text = color;
        } else {
            text = MathUtils.random(0, 7);
        }

        String textColor;
        switch (text) {
            case COLOR_BLUE:
                textColor = "blue";
                break;
            case COLOR_CYAN:
                textColor = "cyan";
                break;
            case COLOR_GREEN:
                textColor = "green";
                break;
            case COLOR_YELLOW:
                textColor = "yellow";
                break;
            case 4:
                textColor = "pink";
                break;
            case 5:
                textColor = "brown";
                break;
            case 6:
                textColor = "purple";
                break;
            default:
            case 7:
                textColor = "red";
                break;
        }

        image.remove();
        image.setText(Assets.languages.get(textColor));
        image.setColor(getCurrentWordColor());
        if (Settings.selectedLanguage == Languages.RUSSIAN)
            image.setFontScale(.68f);
        else
            image.setFontScale(1);
        image.pack();
        image.setPosition(Screens.SCREEN_WIDTH / 2f - image.getWidth() / 2f, 450);
    }

    /**
     * It is the color of the word.
     */
    public Color getCurrentWordColor() {
        return getColor(color);
    }

    public static Color getColor(int wordColor) {
        Color color;
        switch (wordColor) {
            case 0:
                color = Color.BLUE;
                break;
            case 1:
                color = Color.CYAN;
                break;
            case 2:
                color = Color.GREEN;
                break;
            case 3:
                color = Color.YELLOW;
                break;
            case 4:
                color = Color.PINK;
                break;
            case 5:
                color = new Color(.6f, .3f, 0, 1);// Cafe
                break;
            case 6:
                color = Color.PURPLE;
                break;
            default:
            case 7:
                color = Color.RED;
                break;
        }
        return color;
    }

    public static Color getRandomColor() {
        return getColor(MathUtils.random(7));
    }

}
