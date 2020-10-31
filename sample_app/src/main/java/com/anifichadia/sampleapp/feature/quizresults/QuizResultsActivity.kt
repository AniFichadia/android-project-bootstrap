package com.anifichadia.sampleapp.feature.quizresults

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.anifichadia.sampleapp.R
import com.anifichadia.sampleapp.databinding.ActivityQuizResultsBinding
import pl.droidsonroids.gif.GifDrawable


/**
 * This one doesn't need to be complex, just presents the results
 *
 * @author Aniruddh Fichadia
 * @date 2020-10-18
*/
class QuizResultsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizResultsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityQuizResultsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // I'm putting this in because my mum wanted a dog GIF! :D
        binding.quizResultsImgDogGif.setImageDrawable(GifDrawable(assets, "dog_wagging_tail.gif"))

        with(intent.extras!!) {
            binding.quizResultsTxtResult.text = getString(
                R.string.quiz_results_format_result,
                getInt(KEY_CORRECT_ANSWERS),
                getInt(KEY_QUESTION_COUNT)
            )
        }
        binding.quizResultsBtnExit.setOnClickListener {
            finish()
        }
    }


    companion object {
        private const val KEY_CORRECT_ANSWERS = "KEY_CORRECT_ANSWERS"
        private const val KEY_QUESTION_COUNT = "KEY_QUESTION_COUNT"

        fun newInstance(context: Context, correctAnswers: Int, questionCount: Int) =
            Intent(context, QuizResultsActivity::class.java).apply {
                putExtra(KEY_CORRECT_ANSWERS, correctAnswers)
                putExtra(KEY_QUESTION_COUNT, questionCount)
            }
    }
}
