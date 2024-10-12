package com.nopalsoft.thetruecolor.handlers

interface FacebookHandler {

    fun facebookSignIn()

    fun facebookSignOut()

    fun facebookIsSignedIn(): Boolean

    fun facebookGetScores()

    fun facebookSubmitScore(score: Int)
}