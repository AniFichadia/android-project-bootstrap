package com.anifichadia.sampleapp.feature.multiplechoicequiz

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.anifichadia.bootstrap.app.framework.mvvm.Event
import com.anifichadia.sample_sdk.domain.DogBreed
import com.anifichadia.sampleapp.shared.usecase.RetrieveAllBreedsUseCase
import com.anifichadia.sampleapp.shared.usecase.RetrieveRandomImageUrlForBreedUseCase
import com.anifichadia.sampleapp.feature.multiplechoicequiz.MultipleChoiceQuizContract.LoadingStatus
import com.anifichadia.sampleapp.feature.multiplechoicequiz.MultipleChoiceQuizContract.LoadingStatus.FAILURE
import com.anifichadia.sampleapp.feature.multiplechoicequiz.MultipleChoiceQuizContract.LoadingStatus.LOADING
import com.anifichadia.sampleapp.feature.multiplechoicequiz.MultipleChoiceQuizContract.LoadingStatus.SUCCESS
import com.anifichadia.sampleapp.feature.multiplechoicequiz.MultipleChoiceQuizContract.Question
import com.anifichadia.sampleapp.feature.multiplechoicequiz.MultipleChoiceQuizContract.QuizConfig
import com.anifichadia.sampleapp.feature.multiplechoicequiz.MultipleChoiceQuizContract.QuizResult
import com.anifichadia.sampleapp.feature.multiplechoicequiz.MultipleChoiceQuizContract.QuizStatus
import com.anifichadia.sampleapp.feature.multiplechoicequiz.MultipleChoiceQuizContract.ViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random
import kotlin.random.nextInt

/**
 * @author Aniruddh Fichadia
 * @date 2020-10-18
 */
class MultipleChoiceQuizViewModel @Inject constructor(
    private val retrieveAllBreedsUseCase: RetrieveAllBreedsUseCase,
    private val retrieveRandomImageUrlForBreedUseCase: RetrieveRandomImageUrlForBreedUseCase
) : ViewModel() {

    private val quizConfig = QuizConfig()

    private val _loadingStatus = MutableLiveData<LoadingStatus>()
    override val loadingStatus: LiveData<LoadingStatus> = _loadingStatus

    private val _quizStatus = MutableLiveData<QuizStatus>()
    override val quizStatus: LiveData<QuizStatus> = _quizStatus

    private val _quizResult = MutableLiveData<Event<QuizResult>>()
    override val quizResult: MutableLiveData<Event<QuizResult>> = _quizResult


    override fun generateQuizIfRequired() {
        if (_loadingStatus.value == SUCCESS) {
            return
        }

        _loadingStatus.value = LOADING
        launch {
            when (val retrieveAllBreedsResult = retrieveAllBreedsUseCase.execute(Unit)) {
                is RetrieveAllBreedsUseCase.Result.Success -> {
                    val breeds = retrieveAllBreedsResult.output

                    // Note: Sharing the random instance. If this is created in the async blocks for the questions below,
                    // it may generate the same questions multiple times. This is because batches of async blocks may be
                    // executed at the same time
                    val random = Random(System.currentTimeMillis())

                    val questions = (1..quizConfig.questionCount)
                        .map {
                            async {
                                val selectableAnswers = mutableListOf<DogBreed>().apply { addAll(breeds) }

                                // Pick potential answers without duplication
                                val potentialAnswers = (1..quizConfig.potentialAnswerCount)
                                    .map { selectableAnswers.removeAt(random.nextInt(selectableAnswers.indices)) }
                                // Pick a random alternative as the answer
                                val answer = potentialAnswers[random.nextInt(potentialAnswers.indices)]

                                when (val answerImageResult = retrieveRandomImageUrlForBreedUseCase.execute(answer)) {
                                    is RetrieveRandomImageUrlForBreedUseCase.Result.Success -> {
                                        Question(
                                            potentialAnswers = potentialAnswers.map { it.createDisplayName() },
                                            answer = answer.createDisplayName(),
                                            answerImageUrl = answerImageResult.output
                                        )
                                    }
                                    is RetrieveRandomImageUrlForBreedUseCase.Result.Failure -> null
                                }
                            }
                        }
                        .awaitAll()

                    // Null values indicate retrieval failures
                    val usableQuestions = questions.filterNotNull()
                    val successfullyCreatedQuestions = usableQuestions.size == quizConfig.questionCount
                    if (successfullyCreatedQuestions) {
                        _loadingStatus.value = SUCCESS
                        _quizStatus.value = QuizStatus(
                            allQuestions = usableQuestions,
                            currentQuestionNumber = 1
                        )
                    } else {
                        _loadingStatus.value = FAILURE
                    }
                }
                is RetrieveAllBreedsUseCase.Result.Failure -> {
                    _loadingStatus.value = FAILURE
                }
            }
        }
    }

    override fun onQuestionAnsweredCorrectly() {
        handleQuestionAnswered(true)
    }

    override fun onQuestionAnsweredIncorrectly() {
        handleQuestionAnswered(false)
    }

    private fun handleQuestionAnswered(answeredCorrectly: Boolean) {
        val previousStatus = _quizStatus.value!!

        val isLastQuestion = previousStatus.currentQuestionNumber == quizConfig.questionCount
        val newQuestionNumber = if (!isLastQuestion) {
            previousStatus.currentQuestionNumber + 1
        } else {
            previousStatus.currentQuestionNumber
        }
        val newCorrectAnswerCount = if (answeredCorrectly) {
            previousStatus.correctAnswerCount + 1
        } else {
            previousStatus.correctAnswerCount
        }

        _quizStatus.value = previousStatus.copy(
            currentQuestionNumber = newQuestionNumber,
            correctAnswerCount = newCorrectAnswerCount
        )

        if (isLastQuestion) {
            // When the last question has been answered, emit a 'quiz completion' event
            _quizResult.value = Event(
                QuizResult(
                    newCorrectAnswerCount,
                    quizConfig.questionCount
                )
            )
        }
    }
}

private fun DogBreed.createDisplayName() = if (subBreedName == null) {
    breedName
} else {
    "$subBreedName $breedName"
}

