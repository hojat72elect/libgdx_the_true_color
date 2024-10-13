package com.nopalsoft.thetruecolor;

import com.nopalsoft.thetruecolor.handlers.GameServicesHandler;
import com.nopalsoft.thetruecolor.handlers.GoogleGameServicesHandler;

public class Achievements {

    static GameServicesHandler gameHandler;

    static String beginner, intermediate, advanced, expert, god, iLikeThisGame, iLoveThisGame;

    public static void init(TrueColorGame game) {
        gameHandler = game.gameServiceHandler;

        if (gameHandler instanceof GoogleGameServicesHandler) {
            beginner = "CgkIvIu0qPsVEAIQAg";
            intermediate = "CgkIvIu0qPsVEAIQBA";
            advanced = "CgkIvIu0qPsVEAIQBQ";
            expert = "CgkIvIu0qPsVEAIQBg";
            god = "CgkIvIu0qPsVEAIQBw";
            iLikeThisGame = "CgkIvIu0qPsVEAIQCw";
            iLoveThisGame = "CgkIvIu0qPsVEAIQDA";
        } else {
            beginner = "BeginnerID";
            intermediate = "IntermediateID";
            advanced = "AdvancedID";
            expert = "expertID";
            god = "godId";
            iLikeThisGame = "ILikeThisGameID";
            iLoveThisGame = "ILoveThisGameId";
        }

    }

    public static void unlockScoreAchievements(long num) {

        if (num == 250) {
            gameHandler.unlockAchievement(god);
        } else if (num == 175) {
            gameHandler.unlockAchievement(expert);
        } else if (num == 100) {
            gameHandler.unlockAchievement(advanced);
        } else if (num == 60) {
            gameHandler.unlockAchievement(intermediate);
        } else if (num == 30) {
            gameHandler.unlockAchievement(beginner);
        }

    }

    public static void unlockTimesPlayedAchievements() {
        if (gameHandler instanceof GoogleGameServicesHandler) {
            gameHandler.unlockStepAchievement(1, iLikeThisGame);
            gameHandler.unlockStepAchievement(1, iLoveThisGame);
        } else {
            gameHandler.unlockStepAchievement(Settings.numTimesPlayed, iLikeThisGame);
            gameHandler.unlockStepAchievement(Settings.numTimesPlayed * 100f / 250f, iLoveThisGame);//Para llegar al 100% se deben hacer 250 juegos
        }
    }

}