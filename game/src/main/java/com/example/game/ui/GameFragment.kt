package com.example.game.ui

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.game.managers.uimanager.Config
import com.example.game.managers.uimanager.GameUIManager
import com.example.game.managers.uimanager.UIParams
import com.example.game.databinding.GameFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GameFragment : Fragment() {

    companion object {
        fun newInstance() = GameFragment()
    }

    private val viewModel: GameViewModel by viewModels()
    private var _binding: GameFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = GameFragmentBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        startGame(binding.root)
        binding.button.setOnClickListener {
            startGame(binding.root)
        }
    }

    private fun startGame(view: View) {

        val displayMetrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)

        var height = displayMetrics.heightPixels
        val uiParams = UIParams(binding.textView, binding.textView2, binding.buttonPositive, binding.buttonNegative)
        val config = Config(height, 15_000)
        val gameUIManager =  GameUIManager(uiParams, config)
        gameUIManager.observeGameConclusion { userAnsweredQuestionCorrectly ->
            Log.e("Jeff", "Game has ended with result: $userAnsweredQuestionCorrectly")
        }

        val gameWordsManager = com.example.game.managers.wordsmanager.GameWordsManager(requireContext())
        val gameData = gameWordsManager.getRandomGameData()

        gameUIManager.startGame(gameData.word, gameData.possibleTranslation, gameData.isCorrectTranslation)
    }

}