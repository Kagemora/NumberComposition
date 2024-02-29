package com.example.numbercomposition.presentation

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.numbercomposition.R
import com.example.numbercomposition.data.GameRepositoryImpl
import com.example.numbercomposition.domain.entity.GameResult
import com.example.numbercomposition.domain.entity.GameSetting
import com.example.numbercomposition.domain.entity.Level
import com.example.numbercomposition.domain.entity.Question
import com.example.numbercomposition.domain.usecases.GenerateQuestionUseCase
import com.example.numbercomposition.domain.usecases.GetGameSettingsUseCase

class GameViewModel(application: Application) : AndroidViewModel(application) {
    private lateinit var gameSetting: GameSetting
    private lateinit var level: Level

    private val repository = GameRepositoryImpl
    private val context = application


    private val generateQuestionUseCase = GenerateQuestionUseCase(repository)
    private val getGameSettingUseCase = GetGameSettingsUseCase(repository)


    private var timer: CountDownTimer? = null


    private val _formattedTime = MutableLiveData<String>()
    val formattedTime: LiveData<String>
        get() = _formattedTime


    private val _question = MutableLiveData<Question>()
    val question: LiveData<Question>
        get() = _question


    private val _percentOfRightAnswers = MutableLiveData<Int>()
    val percentOfRightAnswers: LiveData<Int>
        get() = _percentOfRightAnswers


    private val _progressAnswers = MutableLiveData<String>()
    val progressAnswers: LiveData<String>
        get() = _progressAnswers


    private val _enoughCount = MutableLiveData<Boolean>()
    val enoughCount: LiveData<Boolean>
        get() = _enoughCount


    private val _enoughPercent = MutableLiveData<Boolean>()
    val enoughPercent: LiveData<Boolean>
        get() = _enoughPercent


    private val _gameResult = MutableLiveData<GameResult>()
    val gameResult: LiveData<GameResult>
        get() = _gameResult


    private val _minPercent = MutableLiveData<Int>()
    val minPercent: LiveData<Int>
        get() = _minPercent


    private var countOfRightAnswers = 0
    private var countOfQuestions = 0

    //настройки игры
    fun startGame(level: Level) {
        getGameSettings(level)
        startTimer()
        generateQuestion()
    }

    fun chooseAnswer(number: Int) {
        checkAnswer(number)
        updateProgress()
        generateQuestion()
    }

    private fun updateProgress() {
        val percent = calculatePercentOfRightAnswers()
        _percentOfRightAnswers.value = percent
        _progressAnswers.value = String.format(
            context.resources.getString(R.string.progress_answers),
            countOfRightAnswers,
            gameSetting.minCountOfRightAnswers
        )
        _enoughCount.value = countOfRightAnswers >= gameSetting.minCountOfRightAnswers
        _enoughPercent.value = percent >= gameSetting.minPercentOfRightAnswers
    }
    //процент правильных ответов
    private fun calculatePercentOfRightAnswers(): Int {
        return ((countOfRightAnswers / countOfQuestions.toDouble()) * 100).toInt()
    }

    //если ответ правильный увеличиваем вместе с вопросом
    private fun checkAnswer(number: Int) {
        val rightAnswer = question.value?.rightAnswer
        if (number == rightAnswer) {
            countOfRightAnswers++
        }
        countOfQuestions++
    }

    //игровые настройки от теста к харду
    private fun getGameSettings(level: Level) {
        this.level = level
        this.gameSetting = getGameSettingUseCase(level)
        _minPercent.value = gameSetting.minPercentOfRightAnswers
    }

    //таймер запускаем
    private fun startTimer() {
        timer = object : CountDownTimer(
            gameSetting.gameTimeInSeconds * MILLIS_IN_SECONDS,
            MILLIS_IN_SECONDS
        ) {
            override fun onTick(millisUntilFinished: Long) {
                _formattedTime.value = formatTime(millisUntilFinished)
            }

            override fun onFinish() {
                finishGame()
            }

        }
        timer?.start()
    }

    //формат времени
    private fun formatTime(millisUntilFinished: Long): String {
        val seconds = millisUntilFinished / MILLIS_IN_SECONDS
        val minutes = seconds / SECONDS_IN_MINUTES
        val leftSeconds = seconds - (minutes * SECONDS_IN_MINUTES)
        return String.format("%02d:%02d", minutes, leftSeconds)
    }

    //установка максимальной суммы
    private fun generateQuestion() {
        _question.value = generateQuestionUseCase(gameSetting.maxSumValue)
    }

    private fun finishGame() {
        _gameResult.value = GameResult(
            enoughCount.value ==true && enoughPercent.value == true,
            countOfRightAnswers,
            countOfQuestions,
            gameSetting
        )
    }

    //при закрытии фрагмента деактивируем таймер
    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }

    companion object {
        private const val MILLIS_IN_SECONDS = 1000L
        private const val SECONDS_IN_MINUTES = 60
    }
}