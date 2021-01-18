package com.anifichadia.sampleapp.feature.multiplechoicequiz.question

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.anifichadia.bootstrap.app.framework.mvvm.DelegatingViewModelInitialiser
import com.anifichadia.bootstrap.app.shared.atomic
import com.anifichadia.bootstrap.app.shared.weak
import com.anifichadia.sampleapp.R
import com.anifichadia.sampleapp.databinding.FragmentMultipleChoiceQuestionBinding
import com.anifichadia.sampleapp.feature.MvvmFragment
import com.anifichadia.sampleapp.feature.multiplechoicequiz.MultipleChoiceQuizContract.QuizConfig
import com.anifichadia.sampleapp.feature.multiplechoicequiz.question._di.DaggerQuestionComponent
import com.anifichadia.sampleapp.feature.multiplechoicequiz.question._di.QuestionComponent
import com.anifichadia.sampleapp.feature.multiplechoicequiz.question._di.QuestionModule
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

/**
 * @author Aniruddh Fichadia
 * @date 2020-10-18
 */
class QuestionFragment : MvvmFragment<FragmentMultipleChoiceQuestionBinding>() {

    private lateinit var component: QuestionComponent
    private lateinit var viewModel: QuestionContract.ViewModel

    private var questionResultObserver: QuestionResultObserver? by weak()

    private var atom: QuizConfig by atomic(QuizConfig())


    override fun onAttach(context: Context) {
        super.onAttach(context)
        questionResultObserver = context as? QuestionResultObserver
    }

    override fun createBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        FragmentMultipleChoiceQuestionBinding.inflate(inflater)

    override fun bindView(binding: FragmentMultipleChoiceQuestionBinding) {
        val adapter = QuestionAnswerAdapter()
        binding.multipleChoiceQuestionRecyclerviewAnswers.layoutManager = LinearLayoutManager(requireContext())
        binding.multipleChoiceQuestionRecyclerviewAnswers.adapter = adapter

        adapter.itemClickListener = viewModel::onUserSubmittedAnswer
        adapter.items = viewModel.potentialAnswers

        adapter.answersLocked = true
        binding.multipleChoiceQuestionProgressImageLoading.visibility = View.VISIBLE
        Picasso.get()
            .load(viewModel.answerImageUrl)
            .into(binding.multipleChoiceQuestionImgDogImage, object : Callback {
                override fun onSuccess() {
                    adapter.answersLocked = false
                    binding.multipleChoiceQuestionProgressImageLoading.visibility = View.GONE
                }

                override fun onError(e: Exception) {
                    adapter.answersLocked = false
                    binding.multipleChoiceQuestionProgressImageLoading.visibility = View.GONE
                    // TODO: future, include error and retry state
                }
            })
    }


    override fun configureDi() {
        component = DaggerQuestionComponent.builder()
            .questionModule(QuestionModule())
            .build()
    }


    override fun createViewModels() {
        viewModel = DelegatingViewModelInitialiser.forComponent(this, component::createViewModel)
    }

    override fun observeViewModels() {
        val args = requireArguments()

        viewModel.potentialAnswers = args.getStringArrayList(KEY_POTENTIAL_ANSWERS)!!
        viewModel.answer = args.getString(KEY_ANSWER)!!
        viewModel.answerImageUrl = args.getString(KEY_ANSWER_IMAGE_URL)!!

        viewModel.userAnswer.observe(this) { userAnswer ->
            with(binding.multipleChoiceQuestionRecyclerviewAnswers.adapter as QuestionAnswerAdapter) {
                selectedIndex = userAnswer.answerIndex
                answersLocked = true
            }

            AlertDialog.Builder(requireContext())
                .setTitle(
                    if (userAnswer.correct) {
                        R.string.multiple_choice_quiz_answered_correctly
                    } else {
                        R.string.multiple_choice_quiz_answered_incorrectly
                    }
                )
                .setMessage(getString(R.string.multiple_choice_quiz_answered_result, viewModel.answer))
                .setCancelable(false)
                .setPositiveButton(R.string.multiple_choice_quiz_next) { _, _ ->
                    questionResultObserver?.let {
                        if (userAnswer.correct) {
                            it.onQuestionAnsweredCorrectly()
                        } else {
                            it.onQuestionAnsweredIncorrectly()
                        }
                    }
                }
                .show()
        }
    }


    companion object {
        private const val KEY_POTENTIAL_ANSWERS = "KEY_POTENTIAL_ANSWERS"
        private const val KEY_ANSWER = "KEY_ANSWER"
        private const val KEY_ANSWER_IMAGE_URL = "KEY_ANSWER_IMAGE_URL"

        fun newInstance(potentialAnswers: List<String>, answer: String, answerImageUrl: String) = QuestionFragment()
            .apply {
                arguments = createArgs(potentialAnswers, answer, answerImageUrl)
            }

        fun createArgs(potentialAnswers: List<String>, answer: String, answerImageUrl: String) = Bundle().apply {
            putStringArrayList(KEY_POTENTIAL_ANSWERS, ArrayList(potentialAnswers))
            putString(KEY_ANSWER, answer)
            putString(KEY_ANSWER_IMAGE_URL, answerImageUrl)
        }
    }
}
