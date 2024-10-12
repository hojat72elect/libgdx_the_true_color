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
import com.nopalsoft.thetruecolor.screens.Screens;

public class GameScreen extends Screens {
    public static int STATE_READY = 0;
    public static int STATE_RUNNING = 1;
    public static int STATE_GAMEOVER = 2;
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

    com.nopalsoft.thetruecolor.objects.Word oWord;
    com.nopalsoft.thetruecolor.scene2d.TableProgressbarTimer tableProgressbarTimer;

    public GameScreen(final com.nopalsoft.thetruecolor.TrueColorGame game) {
        super(game);

        oWord = new com.nopalsoft.thetruecolor.objects.Word();

        labelScore = new Label("0", new LabelStyle(Assets.fontSmall, Color.WHITE));
        labelScore.setColor(Color.RED);
        labelScore.setPosition(10, 735);

        initialTimeByWord = INITIAL_TIME_PER_WORD;

        tableProgressbarTimer = new com.nopalsoft.thetruecolor.scene2d.TableProgressbarTimer(SCREEN_WIDTH / 2f - com.nopalsoft.thetruecolor.scene2d.TableProgressbarTimer.WIDTH / 2f, 300);

        int buttonSize = 90;

        buttonTrue = new Button(Assets.buttonTrue);
        addEfectoPress(buttonTrue);
        buttonTrue.setSize(buttonSize, buttonSize);
        buttonTrue.setPosition(240 + 80, 60);
        buttonTrue.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                checkWord(true);
            }
        });

        buttonFalse = new Button(Assets.buttonFalse);
        addEfectoPress(buttonFalse);
        buttonFalse.setSize(buttonSize, buttonSize);
        buttonFalse.setPosition(240 - 170, 60);
        buttonFalse.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                checkWord(false);

            }

        });

        buttonBack = new Button(Assets.buttonBack);
        addEfectoPress(buttonBack);
        buttonBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!buttonBack.isDisabled()) {
                    changeScreenWithFadeOut(MainMenuScreen.class, game);
                }
            }
        });

        buttonTryAgain = new Button(Assets.buttonTryAgain);
        addEfectoPress(buttonTryAgain);
        buttonTryAgain.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!buttonTryAgain.isDisabled()) {
                    changeScreenWithFadeOut(GameScreen.class, game);
                }
            }
        });

        buttonShare = new Button(Assets.buttonShare);
        addEfectoPress(buttonShare);
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

        // TODO fix that can't be done because the app crashes and they don't accept it in the store.
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
        oWord.init();

        tableProgressbarTimer.remove();
        tableProgressbarTimer.initialize(oWord.getCurrentWordColor(), initialTimeByWord);
        stage.addActor(tableProgressbarTimer);
        stage.addActor(oWord.image);
    }

    private void checkWord(boolean selection) {
        if (state == STATE_RUNNING) {

            if ((oWord.color == oWord.text && selection) || (oWord.color != oWord.text && !selection)) {
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

            labelScore.setColor(com.nopalsoft.thetruecolor.objects.Word.getRandomColor());
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
            state = STATE_GAMEOVER;

            float animationTime = .8f;

            buttonFalse.addAction(Actions.sequence(Actions.alpha(0, animationTime), Actions.removeActor()));
            buttonTrue.addAction(Actions.sequence(Actions.alpha(0, animationTime), Actions.removeActor()));

            tableProgressbarTimer.timeIsOver = true;
            tableProgressbarTimer.addAction(Actions.sequence(Actions.alpha(0, animationTime), Actions.removeActor()));

            oWord.image.addAction(Actions.sequence(Actions.alpha(0, animationTime), Actions.removeActor()));

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
                new com.nopalsoft.thetruecolor.scene2d.DialogFacebook(this).show(stage);
            }

            Achievements.unlockTimesPlayedAchievements(Settings.numTimesPlayed);
            Settings.save();
        }
    }

    private Label getLabel(String scoreText) {
        StringBuilder scoreTextColor = new StringBuilder();

        // HOT FIX : TO PUT COLORS BETWEEN THE LETTERS IS OBVIOUSLY WRONG BUT I COULD NOT THINK OF ANYTHING ELSE
        String[] apend = {"[RED]", "[BLUE]", "[ORANGE]", "[RED]", "[BLUE]", "[ORANGE]", "[RED]", "[BLUE]", "[ORANGE]", "[RED]", "[BLUE]",
                "[ORANGE]", "[RED]", "[BLUE]", "[ORANGE]", "[RED]", "[BLUE]", "[ORANGE]", "[RED]", "[BLUE]", "[ORANGE]", "[RED]", "[BLUE]",
                "[ORANGE]", "[RED]", "[BLUE]", "[ORANGE]", "[RED]", "[BLUE]", "[ORANGE]", "[RED]", "[BLUE]", "[ORANGE]", "[RED]", "[BLUE]",
                "[ORANGE]"};
        for (int i = 0; i < scoreText.length(); i++) {
            scoreTextColor.append(apend[i]);
            scoreTextColor.append(scoreText.charAt(i));
        }
        scoreTextColor.append(apend[scoreText.length()]);

        Label lblScore = new Label(scoreTextColor + "\n" + score, new Label.LabelStyle(com.nopalsoft.thetruecolor.Assets.fontSmall, com.badlogic.gdx.graphics.Color.WHITE));
        lblScore.setAlignment(com.badlogic.gdx.utils.Align.center);
        lblScore.setFontScale(2.5f);
        lblScore.pack();
        lblScore.setPosition(SCREEN_WIDTH / 2f - lblScore.getWidth() / 2f, 380);
        return lblScore;
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
