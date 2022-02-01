package com.example.game.ui

import android.os.Bundle
import android.util.DisplayMetrics
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.core.getScreenHeight
import com.example.game.managers.uimanager.Config
import com.example.game.managers.uimanager.GameUIManager
import com.example.game.managers.uimanager.UIParams
import com.example.game.databinding.GameFragmentBinding
import com.example.game.managers.wordsmanager.GameWordsManager
import com.example.game.ui.score.ScoreBottomSheet
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
        showGameInfo(viewModel.getUserScore())
    }

    /**
     * This method is called to display the current score board of the game.
     */
    private fun showGameInfo(userScore: Pair<Int, Int>?) {
        ScoreBottomSheet(userScore) { startGame() }
            .show(childFragmentManager, GameFragment::class.java.simpleName)
    }

    /**
     * This method starts a game session. A game session ends with the user chooses an answer or fails
     * to provide an answer. This method can be called multiple times.
     */
    private fun startGame() {
        val uiParams = UIParams(binding.tvWord, binding.tvTranslation, binding.buttonPositive, binding.buttonNegative)
        val config = Config(getScreenHeight(requireActivity()), 15_000)

        val gameUIManager =  GameUIManager(uiParams, config)
        gameUIManager.observeGameSessionConclusion { userAnsweredQuestionCorrectly ->
            handleGameSessionConclusion(userAnsweredQuestionCorrectly)
        }

        val gameWordsManager = GameWordsManager(requireContext())
        val gameData = gameWordsManager.getRandomGameData()

        gameUIManager.startGame(gameData.word, gameData.possibleTranslation, gameData.isCorrectTranslation)
    }

    /**
     * This method determines what happens after a game session is over. When this happens, we
     * increase the pass or failed count and restart the game session.
     */
    private fun handleGameSessionConclusion(userAnsweredQuestionCorrectly: Boolean) {
        when(userAnsweredQuestionCorrectly) {
            true -> viewModel.increasePassCount()
            false -> viewModel.increaseFailCount()
        }
        showGameInfo(viewModel.getUserScore())
    }

}