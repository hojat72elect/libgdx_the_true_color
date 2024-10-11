package com.nopalsoft.thetruecolor.handlers;

public interface FacebookHandler {

    void facebookSignIn();

    void facebookSignOut();

    boolean facebookIsSignedIn();

    void facebookGetScores();

    void facebookSubmitScore(final int score);

}
