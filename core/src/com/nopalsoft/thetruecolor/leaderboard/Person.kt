package com.nopalsoft.thetruecolor.leaderboard;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.nopalsoft.thetruecolor.Assets;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;

public class Person extends Group implements Comparable<Person> {
    final float WIDTH = DialogRanking.WIDTH - 5;
    final float HEIGHT = 75;

    public enum AccountType {
        GOOGLE_PLAY, FACEBOOK, AMAZON
    }

    public Person.AccountType accountType;

    final public String id;
    public String name;
    public long score;

    Label labelName;
    Label labelScore;


    public Person(Person.AccountType accountType, String id, String name, long score) {
        setBounds(0, 0, WIDTH, HEIGHT);

        this.accountType = accountType;
        this.id = id;
        this.name = name;
        this.score = score;

        Image imageAccount = getImage(accountType);

        labelName = new Label(name, new LabelStyle(Assets.fontSmall, Color.BLACK));
        labelName.setFontScale(.7f);
        labelName.setPosition(140, 36);

        labelScore = new Label(formatScore(), new LabelStyle(Assets.fontSmall, Color.RED));
        labelScore.setPosition(140, 5);

        addActor(imageAccount);
        addActor(labelName);
        addActor(labelScore);

        // Separator
        Image imageHeader = new Image(Assets.header);
        imageHeader.setPosition(0, 0);
        imageHeader.setSize(WIDTH, 5);
        addActor(imageHeader);
    }

    private Image getImage(Person.AccountType accountType) {
       TextureRegionDrawable accountKey;
        switch (accountType) {
            case AMAZON:
                accountKey = Assets.buttonAmazon;
                break;
            case FACEBOOK:
                accountKey = Assets.buttonFacebook;
                break;
            default:
            case GOOGLE_PLAY:
                accountKey = Assets.buttonGoogle;
                break;
        }

        Image imageAccount = new Image(accountKey);
        imageAccount.setSize(30, 30);
        imageAccount.setPosition(10, HEIGHT / 2f - imageAccount.getHeight() / 2f);
        return imageAccount;
    }

    public void setPicture(TextureRegionDrawable drawable) {

        // I use an image button because it can have a background and an image.
        ImageButton imageButtonCharacter = new ImageButton(new ImageButton.ImageButtonStyle(drawable, null, null, Assets.photoFrame, null, null));
        imageButtonCharacter.setSize(60, 60);
        imageButtonCharacter.getImageCell().size(60);
        imageButtonCharacter.setPosition(60, HEIGHT / 2f - imageButtonCharacter.getHeight() / 2f);
        addActor(imageButtonCharacter);

    }


    public String formatScore() {
        String score = String.valueOf(this.score);
        int floatPos = score.contains(".") ? score.length() - score.indexOf(".") : 0;
        int nGroups = (score.length() - floatPos - 1 - (score.contains("-") ? 1 : 0)) / 3;
        for (int i = 0; i < nGroups; i++) {
            int commaPos = score.length() - i * 4 - 3 - floatPos;
            score = score.substring(0, commaPos) + "," + score.substring(commaPos);
        }
        return score;
    }

    @Override
    public int compareTo(Person otherPerson) {
        return Long.compare(otherPerson.score, score);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Person) {
            Person objPerson = (Person) obj;
            return id.equals(objPerson.id) && accountType == objPerson.accountType;

        } else
            return false;
    }

    public void updateData(String _name, long _score) {
        name = _name;
        score = _score;
        labelName.setText(name);
        labelScore.setText(formatScore());

    }
}
