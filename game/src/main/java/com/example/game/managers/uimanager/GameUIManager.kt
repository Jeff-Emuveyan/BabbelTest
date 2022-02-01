package com.example.game.managers.uimanager

import android.animation.ObjectAnimator
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.TextView

/**
 * The purpose of this class is to control the UI of the game.
 */
class GameUIManager(private val uiParams: UIParams,
                    private val config: Config
) {

    private var word: String = ""
    private var possibleTranslation: String = ""
    private var isCorrectTranslation: Boolean = false
    private var onSessionEnd: (Boolean)-> Unit = {}
    private var timer: CountDownTimer? = null
    private var passedQuestion: Boolean = false
    private var objectAnimator: ObjectAnimator? = null

    /***
     * This method starts the game with the following params:
     * @param word: This is the word that needs to be translated correctly.
     * @param possibleTranslation: This the a possible correct translation of the word.
     * @param isCorrectTranslation: This boolean is true only when the possibleTranslation is the
     * correct translation of word.
     */
    fun startGame(word: String, possibleTranslation: String, isCorrectTranslation: Boolean) {
        this.word = word
        this.possibleTranslation = possibleTranslation
        this.isCorrectTranslation = isCorrectTranslation
        this.passedQuestion = false
        startCountDownTimer(config.gameSpeed)
        setUpUI(word, possibleTranslation)
        startAnimation()
    }
    /***
     * This method provides a callback to indicate when the game session has ended.
     * @param onSessionEnd: a callback that is triggered when the user chooses an answer or when
     * the user failed to provide an answer before the time ran out. It gives a boolean: userAnsweredQuestionCorrectly
     * which will be true only when the user was able to determine correctly if the translation was true or false.
     */
    fun observeGameConclusion(onSessionEnd: (userAnsweredQuestionCorrectly: Boolean)-> Unit) {
        this.onSessionEnd = onSessionEnd
    }
    /***
     * Initializes the UI of the game.
     * @param word: This is the word that needs to be translated correctly.
     * @param possibleTranslation: This the a possible correct translation of the word.
     */
    private fun setUpUI(word: String, possibleTranslation: String) {
        turnOnGameUI(true)
        displayWords(word, possibleTranslation)
        handleButtonClicks()
    }

    private fun displayWords(word: String, possibleTranslation: String) {
        uiParams.run {
            tvWord.text = word
            tvPossibleTranslation.text = possibleTranslation
        }
    }
    /***
     * This method determines what happens when the user clicks on the positive or negative buttons.
     */
    private fun handleButtonClicks() {
        uiParams.positiveButton.setOnClickListener {
            completeGameSession(true)
        }
        uiParams.negativeButton.setOnClickListener {
            completeGameSession(false)
        }
    }
    /***
     * This method is called to end the game's session.
     * @param userAnswer: This boolean represents the user's choice. If it is true, it means the user
     * choose the positive button to indicate that the translation is correct. If it is null, it means
     * that the user was not able to determine if the translation was correct or false.
     */
    private fun completeGameSession(userAnswer: Boolean?) {
        objectAnimator?.reverse()
        objectAnimator?.duration = 500

        timer?.cancel()
        timer = null

        turnOnGameUI(false)
        if (userAnswer == null) {
            onSessionEnd.invoke(false)
        } else {
            onSessionEnd.invoke((userAnswer == isCorrectTranslation))
        }
    }

    /***
     * Starts a count down timer that dictates how long a game session can last.
     */
    private fun startCountDownTimer(gameSpeed: Long) {
        timer = object: CountDownTimer(gameSpeed, 1000) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {
                completeGameSession(null)
            }
        }
        timer?.start()
    }
    /**
     * Controls the visibility of the game UI.
     * @param turnOn: is true when the controls should be visible else false.
     */
    private fun turnOnGameUI(turnOn: Boolean) {
        if (turnOn) {
            uiParams.run {
                positiveButton.visibility = View.VISIBLE
                negativeButton.visibility = View.VISIBLE
                tvWord.visibility = View.VISIBLE
                tvPossibleTranslation.visibility = View.VISIBLE
            }
        } else {
            uiParams.run {
                positiveButton.visibility = View.GONE
                negativeButton.visibility = View.GONE
                tvWord.visibility = View.GONE
                tvPossibleTranslation.visibility = View.GONE
            }
        }
    }
    /***
     * Starts the animation to move the word in language 2 from top to bottom. The speed of this animation
     * is controlled by Config.gameSpeed and reversed when completeGameSession() is called.
     */
    private fun startAnimation() {
        objectAnimator = ObjectAnimator.ofFloat(uiParams.tvPossibleTranslation,
            "translationY",
            (config.screenHeight).toFloat() - 300).apply {

            duration = config.gameSpeed
            start()
        }
    }
}

class UIParams(val tvWord: TextView,
               val tvPossibleTranslation: TextView,
               val positiveButton: Button,
               val negativeButton: Button)

class Config(val screenHeight: Int,
             val gameSpeed: Long)