package com.example.game.ui

import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    private var passScore: Int = 0
    private var failScore: Int = 0

    fun getUserScore(): Pair<Int, Int>? {
        return if (passScore == 0 && failScore == 0) {
            null
        } else {
            Pair(passScore, failScore)
        }
    }

    fun increasePassCount() {
        passScore ++
    }

    fun increaseFailCount() {
        failScore ++
    }
}