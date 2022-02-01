package com.example.game.wordsmanager

import android.content.Context
import com.example.game.model.GameData
import com.example.game.model.Word
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException
import kotlin.random.Random

/**
 * The purpose of this class is simple: Provide a word, provide a translation and finally provide
 * a boolean that indicates if the translation is the true translation of the word.
 */
class GameWordsManager(private val context: Context) {

    private val listOfWords: List<Word> by lazy {
        getListOfWordsFromAssetsFolder()
    }

    fun getRandomGameData(): GameData {
        val sizeOfWords = listOfWords.size
        val randomNumber = Random.nextInt(0, sizeOfWords-1)
        val randomWord = listOfWords[randomNumber]

        //Now, when presenting a word and its translation to the user, there are times when the translation
        //should be the correct translation and there are times when the translation should be the wrong
        //translation so as to make the game unpredictable for the user. To achieve this, we simply check
        //if the randomNumber is a factor of 2. If it is, we will send the user a question which has the
        //correct translation.
        return if ((randomNumber % 2) == 0) {
            GameData(randomWord.text_eng, randomWord.text_spa, true)
        } else {
            //We deliberately remove one character from the translation to make it wrong
            GameData(randomWord.text_eng, randomWord.text_spa.drop(1), false)
        }
    }

    /***
     * Convert the json to a list of words
     */
    private fun getListOfWordsFromAssetsFolder(): List<Word> {
        val jsonList = getJsonDataFromAsset(context, "words.json")
        return if (jsonList == null) {
            emptyList()
        } else {
            Gson().fromJson(jsonList)
        }
    }

    /***
     * Gets the json string from the assets folder.
     */
    private fun getJsonDataFromAsset(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

    internal inline fun <reified T> Gson.fromJson(json: String) =
        fromJson<T>(json, object : TypeToken<T>() {}.type)
}