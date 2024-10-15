package com.nopalsoft.thetruecolor.scene2d;

import static com.nopalsoft.thetruecolor.Assets.languages;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.nopalsoft.thetruecolor.Assets;
import com.nopalsoft.thetruecolor.Settings;
import com.nopalsoft.thetruecolor.screens.BaseScreen;

public class DialogHelpSettings extends BaseDialog {
    static final float WIDTH = 440;
    static final float HEIGHT = 600;

    public enum Languages {
        DEFAULT, ENGLISH, SPANISH, CHINESE_TAIWAN, RUSSIAN, FRENCH, JAPANESE, PORTUGUESE
    }

    Table tableColors;

    TextButton buttonDefault, buttonEnglish, buttonSpanish, buttonChineseTaiwan, buttonRussian, buttonFrench, buttonJapanese, buttonPortugese;
    TextButton buttonMore;

    DialogMoreLanguages dialogMoreLanguages;

    public DialogHelpSettings(final BaseScreen currentScreen) {
        super(currentScreen, WIDTH, HEIGHT, 80);
        setCloseButton(560);

        Label labelLanguage = new Label(languages.get("language"), new LabelStyle(Assets.fontSmall, Color.BLACK));
        labelLanguage.setPosition(getWidth() / 2f - labelLanguage.getWidth() / 2f, 555);
        addActor(labelLanguage);

        dialogMoreLanguages = new DialogMoreLanguages(currentScreen);

        buttonMore = createButton(languages.get("more"), null, Assets.flagMore);
        buttonMore.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buttonMore.setChecked(false);
                dialogMoreLanguages.show(currentScreen.stage);
            }
        });

        buttonDefault = createButton("Default", Languages.DEFAULT, null);

        buttonEnglish = createButton("English", Languages.ENGLISH, Assets.flagEnglish);

        buttonSpanish = createButton("Espalier", Languages.SPANISH, Assets.flagSpanish);

        buttonChineseTaiwan = createButton("中文", Languages.CHINESE_TAIWAN, Assets.flagChinese_TW);

        buttonRussian = createButton("Русский", Languages.RUSSIAN, Assets.flagRussian);
        buttonRussian.getLabel().setFontScale(.7f);

        buttonFrench = createButton("Français", Languages.FRENCH, Assets.flagFrench);

        buttonJapanese = createButton("日本語", Languages.JAPANESE, Assets.flagJapanese);

        buttonPortugese = createButton("Portuguese", Languages.PORTUGUESE, Assets.flagPortugese);

        switch (Settings.selectedLanguage) {
            case DEFAULT:
                buttonDefault.setChecked(true);
                break;
            case ENGLISH:
                buttonEnglish.setChecked(true);
                break;
            case SPANISH:
                buttonSpanish.setChecked(true);
                break;
            case CHINESE_TAIWAN:
                buttonChineseTaiwan.setChecked(true);
                break;
            case RUSSIAN:
                buttonRussian.setChecked(true);
                break;
            case FRENCH:
                buttonFrench.setChecked(true);
                break;
            case JAPANESE:
                buttonJapanese.setChecked(true);
                break;
            case PORTUGUESE:
                buttonPortugese.setChecked(true);
                break;
        }


        ButtonGroup<TextButton> btGroup = new ButtonGroup<>(buttonDefault, buttonEnglish, buttonSpanish, buttonChineseTaiwan, buttonRussian, buttonFrench, buttonJapanese, buttonPortugese);
        btGroup.setMaxCheckCount(1);

        Table tableLanguages = new Table();
        tableLanguages.setSize(getWidth(), 200);
        tableLanguages.setPosition(0, 300);

        tableLanguages.defaults().expandX().pad(3f, 10, 3f, 10).fill().uniform();

        tableLanguages.add(buttonDefault);
        tableLanguages.add(buttonEnglish);
        tableLanguages.add(buttonSpanish);
        tableLanguages.row();
        tableLanguages.add(buttonChineseTaiwan);
        tableLanguages.add(buttonRussian);
        tableLanguages.add(buttonFrench);
        tableLanguages.row();
        tableLanguages.add(buttonJapanese);
        tableLanguages.add(buttonPortugese);
        tableLanguages.add(buttonMore);
        tableLanguages.row();

        // The colors
        tableColors = new Table();
        tableColors.setSize(getWidth(), 240);

        fillTableColors();

        addActor(tableColors);
        addActor(tableLanguages);
    }

    private void fillTableColors() {
        tableColors.clear();

        Image iBlue = new Image(Assets.barTimer);
        iBlue.setColor(Color.BLUE);

        Image iCyan = new Image(Assets.barTimer);
        iCyan.setColor(Color.CYAN);

        Image iGreen = new Image(Assets.barTimer);
        iGreen.setColor(Color.GREEN);

        Image iYellow = new Image(Assets.barTimer);
        iYellow.setColor(Color.YELLOW);

        Image iPink = new Image(Assets.barTimer);
        iPink.setColor(Color.PINK);

        Image iBrown = new Image(Assets.barTimer);
        iBrown.setColor(new Color(.6f, .3f, 0, 1));

        Image iPurple = new Image(Assets.barTimer);
        iPurple.setColor(Color.PURPLE);

        Image iRed = new Image(Assets.barTimer);
        iRed.setColor(Color.RED);

        tableColors.defaults().expandX().padTop(5).padBottom(5);

        tableColors.add(getNewLabelWithColor(languages.get("blue"), Color.BLUE));
        tableColors.add(iBlue).size(40).left();

        tableColors.add(getNewLabelWithColor(languages.get("cyan"), Color.CYAN));
        tableColors.add(iCyan).size(40).left();

        tableColors.row();
        tableColors.add(getNewLabelWithColor(languages.get("green"), Color.GREEN));
        tableColors.add(iGreen).size(40).left();

        tableColors.add(getNewLabelWithColor(languages.get("yellow"), Color.YELLOW));
        tableColors.add(iYellow).size(40).left();

        tableColors.row();
        tableColors.add(getNewLabelWithColor(languages.get("pink"), Color.PINK));
        tableColors.add(iPink).size(40).left();

        tableColors.add(getNewLabelWithColor(languages.get("brown"), new Color(.6f, .3f, 0, 1)));
        tableColors.add(iBrown).size(40).left();

        tableColors.row();
        tableColors.add(getNewLabelWithColor(languages.get("purple"), Color.PURPLE));
        tableColors.add(iPurple).size(40).left();

        tableColors.add(getNewLabelWithColor(languages.get("red"), Color.RED));
        tableColors.add(iRed).size(40).left();
    }

    private Label getNewLabelWithColor(String text, Color color) {
        LabelStyle labelStyleColors = new LabelStyle(Assets.fontSmall, color);
        Label label = new Label(text, labelStyleColors);
        if (Settings.selectedLanguage == Languages.RUSSIAN) {
            label.setFontScale(.7f);
        }
        return label;

    }

    private TextButton createButton(String text, Languages language, TextureRegionDrawable flag) {
        TextButton textButton = new TextButton(text, Assets.textButtonStyle);
        if (flag != null) {
            textButton.add(new Image(flag));
        }
        if (language != null) {
            textButton.addListener(addClickListener(language));
        }
        textButton.getLabel().setFontScale(.8f);
        return textButton;

    }

    private ClickListener addClickListener(final Languages language) {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Settings.selectedLanguage = language;
                Settings.save();
                Assets.loadAssetsWithSettings();
                fillTableColors();

            }
        };
    }

}