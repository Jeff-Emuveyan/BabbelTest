package com.example.game

import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.idling.CountingIdlingResource
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.game.managers.wordsmanager.GameWordsManager

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before


@RunWith(AndroidJUnit4::class)
class GameWordsManagerInstrumentedTest {

    private lateinit var gameWordsManager: GameWordsManager
    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext

    @Before
    fun setup() {
        gameWordsManager = GameWordsManager(appContext)
    }

    @Test
    fun can_read_data_from_asset() {
        val listAsString = gameWordsManager.getJsonDataFromAsset(appContext, "words.json")
        assert(!listAsString.isNullOrEmpty())
    }

    @Test
    fun can_convert_string_of_words_to_class() {
        val listOfWords = gameWordsManager.getListOfWordsFromAssetsFolder()
        assert(listOfWords.isNotEmpty())
        assert(listOfWords.size == 297)
    }

    @Test
    fun can_get_random_word() {
        val randomA = gameWordsManager.getRandomGameData()
        val randomB = gameWordsManager.getRandomGameData()
        assert(randomA.word != randomB.word)
    }
}