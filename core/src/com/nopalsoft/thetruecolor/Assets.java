package com.nopalsoft.thetruecolor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.I18NBundle;
import java.util.Locale;

public class Assets {

    public static I18NBundle languages;

    public static BitmapFont fontSmall;
    public static BitmapFont fontExtraLarge;

    public static TextureRegionDrawable title;
    public static AtlasRegion header;

    public static NinePatchDrawable pixelBlack;
    public static NinePatchDrawable barTimer;
    public static NinePatchDrawable dialogRanking;
    public static NinePatchDrawable dialogWindow;

    public static TextureRegionDrawable photoFrame;
    public static TextureRegionDrawable buttonFacebook;
    public static NinePatchDrawable buttonFacebookText;
    public static TextureRegionDrawable buttonGoogle;
    public static NinePatchDrawable buttonGoogleText;
    public static TextureRegionDrawable buttonAmazon;

    public static TextureRegionDrawable one;
    public static TextureRegionDrawable two;
    public static TextureRegionDrawable three;

    public static TextureRegionDrawable buttonRate;
    public static TextureRegionDrawable buttonAchievement;
    public static TextureRegionDrawable buttonLeaderboard;
    public static TextureRegionDrawable buttonHelp;
    public static TextureRegionDrawable buttonTrue;
    public static TextureRegionDrawable buttonFalse;
    public static TextureRegionDrawable buttonBack;
    public static TextureRegionDrawable buttonTryAgain;
    public static TextureRegionDrawable buttonShare;

    public static NinePatchDrawable buttonPlay;
    public static NinePatchDrawable buttonEnabled;
    public static NinePatchDrawable buttonDisabled;
    public static TextureRegionDrawable play;

    public static TextureRegionDrawable flagChinese_TW;
    public static TextureRegionDrawable flagRussian;
    public static TextureRegionDrawable flagSpanish;
    public static TextureRegionDrawable flagEnglish;
    public static TextureRegionDrawable flagFrench;
    public static TextureRegionDrawable flagJapanese;
    public static TextureRegionDrawable flagPortugese;
    public static TextureRegionDrawable flagMore;

    public static TextButtonStyle textButtonStyle;

    private static TextureAtlas atlas;

    public static void load() {
        atlas = new TextureAtlas(Gdx.files.internal("data/atlasMap.txt"));

        fontSmall = new BitmapFont(Gdx.files.internal("data/font32.fnt"), atlas.findRegion("font32"));
        fontSmall.getData().markupEnabled = true;

        fontExtraLarge = new BitmapFont(Gdx.files.internal("data/font100.fnt"), atlas.findRegion("font100"));

        title = new TextureRegionDrawable(atlas.findRegion("titulo"));
        header = atlas.findRegion("header");

        pixelBlack = new NinePatchDrawable(new NinePatch(atlas.findRegion("pixelNegro"), 1, 1, 0, 0));
        barTimer = new NinePatchDrawable(new NinePatch(atlas.findRegion("barTimer"), 4, 4, 5, 4));
        dialogRanking = new NinePatchDrawable(new NinePatch(atlas.findRegion("dialogRanking"), 40, 40, 63, 30));
        dialogWindow = new NinePatchDrawable(new NinePatch(atlas.findRegion("dialogVentana"), 33, 33, 33, 33));

        buttonPlay = new NinePatchDrawable(new NinePatch(atlas.findRegion("btJugar"), 30, 30, 25, 25));
        buttonEnabled = new NinePatchDrawable(new NinePatch(atlas.findRegion("btEnabled"), 9, 9, 7, 7));
        buttonDisabled = new NinePatchDrawable(new NinePatch(atlas.findRegion("btDisabled"), 9, 9, 7, 7));
        play = new TextureRegionDrawable(atlas.findRegion("play"));

        buttonFacebook = new TextureRegionDrawable(atlas.findRegion("btFacebook"));
        buttonFacebookText = new NinePatchDrawable(new NinePatch(atlas.findRegion("btFacebookText"), 55, 20, 0, 0));
        buttonGoogle = new TextureRegionDrawable(atlas.findRegion("btGoogle"));
        buttonGoogleText = new NinePatchDrawable(new NinePatch(atlas.findRegion("btGoogleText"), 60, 20, 0, 0));
        buttonAmazon = new TextureRegionDrawable(atlas.findRegion("btAmazon"));
        photoFrame = new TextureRegionDrawable(atlas.findRegion("photoFrame"));

        one = new TextureRegionDrawable(atlas.findRegion("one"));
        two = new TextureRegionDrawable(atlas.findRegion("two"));
        three = new TextureRegionDrawable(atlas.findRegion("three"));

        buttonRate = new TextureRegionDrawable(atlas.findRegion("btRate"));
        buttonAchievement = new TextureRegionDrawable(atlas.findRegion("btAchievement"));
        buttonLeaderboard = new TextureRegionDrawable(atlas.findRegion("btLeaderboard"));
        buttonHelp = new TextureRegionDrawable(atlas.findRegion("btHelp"));
        buttonTrue = new TextureRegionDrawable(atlas.findRegion("btTrue"));
        buttonFalse = new TextureRegionDrawable(atlas.findRegion("btFalse"));
        buttonBack = new TextureRegionDrawable(atlas.findRegion("btBack"));
        buttonTryAgain = new TextureRegionDrawable(atlas.findRegion("btTryAgain"));
        buttonShare = new TextureRegionDrawable(atlas.findRegion("btShare"));

        textButtonStyle = new TextButtonStyle(buttonDisabled, buttonEnabled, buttonEnabled, fontSmall);

        flagChinese_TW = new TextureRegionDrawable(atlas.findRegion("flags/flag_twd"));
        flagEnglish = new TextureRegionDrawable(atlas.findRegion("flags/flag_gbp"));
        flagSpanish = new TextureRegionDrawable(atlas.findRegion("flags/flag_esp"));
        flagRussian = new TextureRegionDrawable(atlas.findRegion("flags/flag_rub"));
        flagFrench = new TextureRegionDrawable(atlas.findRegion("flags/flag_frf"));
        flagJapanese = new TextureRegionDrawable(atlas.findRegion("flags/flag_jpy"));
        flagPortugese = new TextureRegionDrawable(atlas.findRegion("flags/flag_pte"));
        flagMore = new TextureRegionDrawable(atlas.findRegion("flags/flag_more"));

        loadAssetsWithSettings();

    }

    public static void loadAssetsWithSettings() {
        Locale locale;
        switch (com.nopalsoft.thetruecolor.Settings.selectedLanguage) {
            case ENGLISH:
                locale = Locale.ROOT;
                break;
            case SPANISH:
                locale = new Locale("es");
                break;
            case RUSSIAN:
                locale = new Locale("ru");
                break;
            case FRENCH:
                locale = new Locale("fr");
                break;
            case JAPANESE:
                locale = new Locale("ja");
                break;
            case PORTUGUESE:
                locale = new Locale("pt");
                break;
            case CHINESE_TAIWAN:
                locale = new Locale("zh", "TW");
                break;
            case DEFAULT:
            default:
                locale = Locale.getDefault();
                break;
        }

        languages = I18NBundle.createBundle(Gdx.files.internal("strings/strings"), locale);
    }

}
