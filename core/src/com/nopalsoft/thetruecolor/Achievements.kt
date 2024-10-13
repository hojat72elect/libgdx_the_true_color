package com.nopalsoft.thetruecolor

import com.nopalsoft.thetruecolor.handlers.GameServicesHandler
import com.nopalsoft.thetruecolor.handlers.GoogleGameServicesHandler

object Achievements {

    private lateinit var gameHandler: GameServicesHandler
    private lateinit var beginner: String
    private lateinit var intermediate: String
    private lateinit var advanced: String
    private lateinit var expert: String
    private lateinit var god: String
    private lateinit var iLikeThisGame: String
    private lateinit var iLoveThisGame: String

    fun initialize(game: TrueColorGame) {
        gameHandler = game.gameServiceHandler

        if (gameHandler is GoogleGameServicesHandler) {
            beginner = "CgkIvIu0qPsVEAIQAg"
            intermediate = "CgkIvIu0qPsVEAIQBA"
            advanced = "CgkIvIu0qPsVEAIQBQ"
            expert = "CgkIvIu0qPsVEAIQBg"
            god = "CgkIvIu0qPsVEAIQBw"
            iLikeThisGame = "CgkIvIu0qPsVEAIQCw"
            iLoveThisGame = "CgkIvIu0qPsVEAIQDA"
        } else {
            beginner = "BeginnerID"
            intermediate = "IntermediateID"
            advanced = "AdvancedID"
            expert = "expertID"
            god = "godId"
            iLikeThisGame = "ILikeThisGameID"
            iLoveThisGame = "ILoveThisGameId"
        }
    }

    @JvmStatic
    fun unlockScoreAchievements(num: Long) {
        when (num) {
            250L -> gameHandler.unlockAchievement(god)

            175L -> gameHandler.unlockAchievement(expert)

            100L -> gameHandler.unlockAchievement(advanced)

            60L -> gameHandler.unlockAchievement(intermediate)

            30L -> gameHandler.unlockAchievement(beginner)

        }
    }

    @JvmStatic
    fun unlockTimesPlayedAchievements() {
        if (gameHandler is GoogleGameServicesHandler) {
            gameHandler.unlockStepAchievement(1F, iLikeThisGame)
            gameHandler.unlockStepAchievement(1F, iLoveThisGame)
        } else {
            gameHandler.unlockStepAchievement(
                Settings.numTimesPlayed.toFloat(),
                iLikeThisGame
            )
            gameHandler.unlockStepAchievement(
                Settings.numTimesPlayed * 100f / 250f,
                iLoveThisGame
            ) // To reach 100% you must play 250 games
        }
    }

}