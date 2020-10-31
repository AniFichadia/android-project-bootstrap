package com.anifichadia.sampleapp.feature.multiplechoicequiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.anifichadia.bootstrap.app.framework.mvvm.DelegatingViewModelInitialiser
import com.anifichadia.bootstrap.app.framework.mvvm.observeEvent
import com.anifichadia.sampleapp.R
import com.anifichadia.sampleapp.WoofyQuizApplication
import com.anifichadia.sampleapp.databinding.ActivityMultipleChoiceQuizBinding
import com.anifichadia.sampleapp.feature.MvvmActivity
import com.anifichadia.sampleapp.feature.multiplechoicequiz._di.DaggerMultipleChoiceQuizComponent
import com.anifichadia.sampleapp.feature.multiplechoicequiz._di.MultipleChoiceQuizComponent
import com.anifichadia.sampleapp.feature.multiplechoicequiz._di.MultipleChoiceQuizModule
import com.anifichadia.sampleapp.feature.multiplechoicequiz.question.QuestionFragment
import com.anifichadia.sampleapp.feature.multiplechoicequiz.question.QuestionResultObserver
import com.anifichadia.sampleapp.feature.quizresults.QuizResultsActivity
import com.anifichadia.sampleapp.framework.dependencyinjection.UseCaseModule


/**
 * @author Aniruddh Fichadia
 * @date 2020-10-18
 */
class MultipleChoiceQuizActivity : MvvmActivity<ActivityMultipleChoiceQuizBinding>(), QuestionResultObserver {

    private lateinit var component: MultipleChoiceQuizComponent
    private lateinit var viewModel: MultipleChoiceQuizContract.ViewModel


    override fun createBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        ActivityMultipleChoiceQuizBinding.inflate(layoutInflater)

    override fun bindView(binding: ActivityMultipleChoiceQuizBinding) {
        binding.multipleChoiceQuizBtnReloadQuiz.setOnClickListener {
            viewModel.generateQuizIfRequired()
        }
    }


    override fun configureDi() {
        component = DaggerMultipleChoiceQuizComponent.builder()
            .appComponent((this.application as WoofyQuizApplication).appComponent)
            .multipleChoiceQuizModule(MultipleChoiceQuizModule())
            .useCaseModule(UseCaseModule())
            .build()
            .also { it.inject(this) }
    }


    override fun createViewModels() {
        viewModel = DelegatingViewModelInitialiser.forComponent(this, component::createViewModel)
    }

    override fun observeViewModels() {
        viewModel.loadingStatus.observe(this) { loadingStatus ->
            when (loadingStatus) {
                MultipleChoiceQuizContract.LoadingStatus.LOADING -> {
                    binding.multipleChoiceQuizContainerLoading.visibility = View.VISIBLE
                    binding.multipleChoiceQuizContainerSuccess.visibility = View.GONE
                    binding.multipleChoiceQuizContainerError.visibility = View.GONE
                }
                MultipleChoiceQuizContract.LoadingStatus.SUCCESS -> {
                    binding.multipleChoiceQuizContainerLoading.visibility = View.GONE
                    binding.multipleChoiceQuizContainerSuccess.visibility = View.VISIBLE
                    binding.multipleChoiceQuizContainerError.visibility = View.GONE
                }
                MultipleChoiceQuizContract.LoadingStatus.FAILURE -> {
                    binding.multipleChoiceQuizContainerLoading.visibility = View.GONE
                    binding.multipleChoiceQuizContainerSuccess.visibility = View.GONE
                    binding.multipleChoiceQuizContainerError.visibility = View.VISIBLE
                }
            }
        }
        viewModel.quizStatus.observe(this) { status ->
            val currentQuestionNumber = status.currentQuestionNumber
            val questionCount = status.questionCount
            val correctAnswerCount = status.correctAnswerCount

            binding.multipleChoiceQuizTxtQuestionNumber.text = "$currentQuestionNumber"
            binding.multipleChoiceQuizTxtQuestionCount.text = "$questionCount"

            binding.multipleChoiceQuizProgressQuizStatus.apply {
                this.progress = currentQuestionNumber
                this.secondaryProgress = correctAnswerCount
                this.max = questionCount
            }

            val currentQuestion = status.currentQuestion
            val fragment = QuestionFragment.newInstance(
                currentQuestion.potentialAnswers,
                currentQuestion.answer,
                currentQuestion.answerImageUrl,
            )
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.multiple_choice_quiz_container_question, fragment)
                .commit()
        }
        viewModel.quizResult.observeEvent(this) { result ->
            startActivity(QuizResultsActivity.newInstance(this, result.correctAnswerCount, result.questionCount))
            finish()
        }

        viewModel.generateQuizIfRequired()
    }

    //region QuestionResultObserver
    override fun onQuestionAnsweredCorrectly() {
        viewModel.onQuestionAnsweredCorrectly()
    }

    override fun onQuestionAnsweredIncorrectly() {
        viewModel.onQuestionAnsweredIncorrectly()
    }
    //endregion
}
