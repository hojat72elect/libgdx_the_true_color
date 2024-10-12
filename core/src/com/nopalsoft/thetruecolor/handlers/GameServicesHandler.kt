package com.nopalsoft.thetruecolor.handlers;

public interface GameServicesHandler {

    /**
     * This method abstracts away GPGS or AGC.
     */
    void submitScore(long score);

    /**
     * This method abstracts away GPGS or AGC.
     */
    void unlockAchievement(String achievementId);

    void unlockStepAchievement(float steps, String achievementID);

    /**
     * This method abstracts away GPGS or AGC.
     */
    void getLeaderboard();

    void getScores();

    /**
     * This method abstracts away GPGS or AGC.
     */
    void getAchievements();

    boolean isSignedIn();

    void signIn();

    void signOut();

}
