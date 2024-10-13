package com.nopalsoft.thetruecolor.handlers

interface GameServicesHandler {

    // This method abstracts away GPGS or AGC.
    fun submitScore(score: Long)

    // This method abstracts away GPGS or AGC.
    fun unlockAchievement(achievementId: String?)

    fun unlockStepAchievement(steps: Float, achievementID: String?)

    // This method abstracts away GPGS or AGC.
    fun getLeaderboard()

    fun getScores()

    // This method abstracts away GPGS or AGC.
    fun getAchievements()

    fun isSignedIn(): Boolean

    fun signIn()

    fun signOut()
}