package com.nopalsoft.thetruecolor.game;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.nopalsoft.thetruecolor.Achievements;
import com.nopalsoft.thetruecolor.Assets;
import com.nopalsoft.thetruecolor.Settings;
import com.nopalsoft.thetruecolor.scene2d.CountDown;
import com.nopalsoft.thetruecolor.screens.MainMenuScreen;
import com.nopalsoft.thetruecolor.screens.BaseScreen;
import com.nopalsoft.thetruecolor.scene2d.TableProgressbarTimer;
import com.badlogic.gdx.utils.Align;
import com.nopalsoft.thetruecolor.objects.Word;
import com.nopalsoft.thetruecolor.TrueColorGame;
import com.nopalsoft.thetruecolor.scene2d.DialogFacebook;

public class GameScreen extends BaseScreen {
    public static int STATE_READY = 0;
    public static int STATE_RUNNING = 1;
    public static int STATE_GAME_OVER = 2;
    int state;

    public static float MINIMUM_TIME_PER_WORD = .62f;
    public static float INITIAL_TIME_PER_WORD = 5;
    float initialTimeByWord;

    Button buttonTrue, buttonFalse;

    Table tableMenu;
    Button buttonBack, buttonTryAgain, buttonShare;

    Label labelScore;

    int score;
    int scoreAnterior;

    Word word;
    TableProgressbarTimer tableProgressbarTimer;

    public GameScreen(final TrueColorGame game) {
        super(game);

        word = new Word();

        labelScore = new Label("0", new LabelStyle(Assets.fontSmall, Color.WHITE));
        labelScore.setColor(Color.RED);
        labelScore.setPosition(10, 735);

        initialTimeByWord = INITIAL_TIME_PER_WORD;

        tableProgressbarTimer = new TableProgressbarTimer(SCREEN_WIDTH / 2f - TableProgressbarTimer.WIDTH / 2f, 300);

        int buttonSize = 90;

        buttonTrue = new Button(Assets.buttonTrue);
        addPressEffect(buttonTrue);
        buttonTrue.setSize(buttonSize, buttonSize);
        buttonTrue.setPosition(240 + 80, 60);
        buttonTrue.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                checkWord(true);
            }
        });

        buttonFalse = new Button(Assets.buttonFalse);
        addPressEffect(buttonFalse);
        buttonFalse.setSize(buttonSize, buttonSize);
        buttonFalse.setPosition(240 - 170, 60);
        buttonFalse.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                checkWord(false);

            }

        });

        buttonBack = new Button(Assets.buttonBack);
        addPressEffect(buttonBack);
        buttonBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!buttonBack.isDisabled()) {
                    changeScreenWithFadeOut(MainMenuScreen.class, game);
                }
            }
        });

        buttonTryAgain = new Button(Assets.buttonTryAgain);
        addPressEffect(buttonTryAgain);
        buttonTryAgain.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!buttonTryAgain.isDisabled()) {
                    changeScreenWithFadeOut(GameScreen.class, game);
                }
            }
        });

        buttonShare = new Button(Assets.buttonShare);
        addPressEffect(buttonShare);
        buttonShare.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!buttonShare.isDisabled()) {
                    game.reqHandler.shareAPK();
                }

            }
        });

        tableMenu = new Table();
        tableMenu.setSize(SCREEN_WIDTH, 90);
        tableMenu.setPosition(0, 60);
        tableMenu.defaults().expandX().size(90);

        tableMenu.add(buttonBack);
        tableMenu.add(buttonTryAgain);

        // fix that can't be done because the app crashes and they don't accept it in the store.
        if (Gdx.app.getType() != ApplicationType.iOS) {
            tableMenu.add(buttonShare);
        }

        stage.addActor(buttonTrue);
        stage.addActor(buttonFalse);
        stage.addActor(labelScore);

        setReady();

        game.reqHandler.loadInterstitial();

    }

    public void createNewWord() {
        word.initialize();

        tableProgressbarTimer.remove();
        tableProgressbarTimer.initialize(word.getCurrentWordColor(), initialTimeByWord);
        stage.addActor(tableProgressbarTimer);
        stage.addActor(word.image);
    }

    private void checkWord(boolean selection) {
        if (state == STATE_RUNNING) {

            if ((word.color == word.text && selection) || (word.color != word.text && !selection)) {
                score++;
                Achievements.unlockScoreAchievements(score);

                if (score < 10) {
                    initialTimeByWord -= .14f;// 1.8seg menos
                } else if (score < 40) {
                    initialTimeByWord -= .05f;// 1.5seg menos
                } else if (score < 70) {
                    initialTimeByWord -= .015f;// .54seg menos
                } else {
                    initialTimeByWord -= .0075f;
                }

                if (initialTimeByWord < MINIMUM_TIME_PER_WORD) {
                    initialTimeByWord = MINIMUM_TIME_PER_WORD;
                }
                createNewWord();
            } else {
                setGameOver();
            }
        }

    }

    @Override
    public void update(float delta) {

        if (score > scoreAnterior) {
            scoreAnterior = score;

            labelScore.setColor(Word.getRandomColor());
            labelScore.setText(scoreAnterior + "");
        }

        if (tableProgressbarTimer.timeIsOver) {
            setGameOver();
        }
    }

    @Override
    public void draw(float delta) {
        batcher.begin();
        batcher.draw(Assets.header, 0, 780, 480, 20);
        batcher.draw(Assets.header, 0, 0, 480, 20);

        batcher.end();
    }

    private void setReady() {
        state = STATE_READY;
        stage.addActor(new CountDown(this));
    }

    public void setRunning() {
        if (state == STATE_READY) {
            state = STATE_RUNNING;
            createNewWord();
        }

    }

    private void setGameOver() {
        if (state == STATE_RUNNING) {
            state = STATE_GAME_OVER;

            float animationTime = .8f;

            buttonFalse.addAction(Actions.sequence(Actions.alpha(0, animationTime), Actions.removeActor()));
            buttonTrue.addAction(Actions.sequence(Actions.alpha(0, animationTime), Actions.removeActor()));

            tableProgressbarTimer.timeIsOver = true;
            tableProgressbarTimer.addAction(Actions.sequence(Actions.alpha(0, animationTime), Actions.removeActor()));

            word.image.addAction(Actions.sequence(Actions.alpha(0, animationTime), Actions.removeActor()));

            String scoreText = Assets.languages.get("score");

            Label lblScore = getLabel(scoreText);
            lblScore.getColor().a = 0;

            lblScore.addAction(Actions.sequence(Actions.delay(1), Actions.alpha(1, animationTime)));

            tableMenu.getColor().a = 0;

            buttonBack.setDisabled(true);
            buttonTryAgain.setDisabled(true);
            buttonShare.setDisabled(true);

            tableMenu.addAction(Actions.sequence(Actions.delay(1), Actions.alpha(1, animationTime), Actions.run(new Runnable() {

                @Override
                public void run() {
                    buttonBack.setDisabled(false);
                    buttonTryAgain.setDisabled(false);
                    buttonShare.setDisabled(false);
                }
            })));

            stage.addActor(lblScore);
            stage.addActor(tableMenu);
            Settings.setNewScore(score);
            game.facebookHandler.facebookSubmitScore(score);
            game.gameServiceHandler.submitScore(score);

            game.reqHandler.showAdBanner();

            Settings.numTimesPlayed++;
            if (Settings.numTimesPlayed % 7f == 0 || score > 80) {
                game.reqHandler.showInterstitial();
            }


            if (!game.facebookHandler.facebookIsSignedIn() && (Settings.numTimesPlayed == 5 || Settings.numTimesPlayed == 10)) {
                new DialogFacebook(this).show(stage);
            }

            Achievements.unlockTimesPlayedAchievements();
            Settings.save();
        }
    }

    private Label getLabel(String scoreText) {
        StringBuilder scoreTextColor = new StringBuilder();

        // HOT FIX : TO PUT COLORS BETWEEN THE LETTERS IS OBVIOUSLY WRONG BUT I COULD NOT THINK OF ANYTHING ELSE
        String[] append = {"[RED]", "[BLUE]", "[ORANGE]", "[RED]", "[BLUE]", "[ORANGE]", "[RED]", "[BLUE]", "[ORANGE]", "[RED]", "[BLUE]",
                "[ORANGE]", "[RED]", "[BLUE]", "[ORANGE]", "[RED]", "[BLUE]", "[ORANGE]", "[RED]", "[BLUE]", "[ORANGE]", "[RED]", "[BLUE]",
                "[ORANGE]", "[RED]", "[BLUE]", "[ORANGE]", "[RED]", "[BLUE]", "[ORANGE]", "[RED]", "[BLUE]", "[ORANGE]", "[RED]", "[BLUE]",
                "[ORANGE]"};
        for (int i = 0; i < scoreText.length(); i++) {
            scoreTextColor.append(append[i]);
            scoreTextColor.append(scoreText.charAt(i));
        }
        scoreTextColor.append(append[scoreText.length()]);

        Label labelScore = new Label(scoreTextColor + "\n" + score, new Label.LabelStyle(Assets.fontSmall, Color.WHITE));
        labelScore.setAlignment(Align.center);
        labelScore.setFontScale(2.5f);
        labelScore.pack();
        labelScore.setPosition(SCREEN_WIDTH / 2f - labelScore.getWidth() / 2f, 380);
        return labelScore;
    }

    @Override
    public void hide() {
        super.hide();
        game.reqHandler.hideAdBanner();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Keys.BACK | keycode == Keys.ESCAPE) {
            changeScreenWithFadeOut(MainMenuScreen.class, game);
            return true;
        }
        return super.keyDown(keycode);
    }

}
