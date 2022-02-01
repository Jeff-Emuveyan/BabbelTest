package com.example.game.ui.score

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.game.R
import com.example.game.databinding.ScoreBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScoreBottomSheet(private var userScore: Pair<Int, Int>?,
                       private val startGame: ()->Unit) : BottomSheetDialogFragment() {

    private var _binding: ScoreBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        _binding = ScoreBottomSheetBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //needed to remove the white background color of the bottom sheet.
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpUIState(userScore)

    }

    private fun setUpUIState(userScore: Pair<Int, Int>?) {
        when(userScore) {
            null -> {
                binding.tvInfo.text = getString(R.string.start_game_message)
                binding.button.text = getString(R.string.start_game)
            }
            else -> {
                showScore(userScore)
            }
        }
        binding.button.setOnClickListener {
            startGame.invoke()
            dismiss()
        }
    }

    private fun showScore(userScore: Pair<Int, Int>) {
        val passScore = userScore.first
        val failScore = userScore.second
        binding.tvInfo.text = getString(R.string.score_detail, passScore, failScore)
        binding.button.text = getString(R.string.continue_game)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        _binding = null
    }
}