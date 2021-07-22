package com.example.android.guesstheword.screens.game

import android.os.CountDownTimer
import android.support.v4.app.INotificationSideChannel
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    companion object {
        // These represent different important times
        // This is when the game is over
        const val DONE = 0L
        // This is the number of milliseconds in a second
        const val ONE_SECOND = 1000L
        // This is the total time of the game
        const val COUNTDOWN_TIME = 10000L
    }
    private val timer:CountDownTimer

    private var _CurrentTime = MutableLiveData<Long>()
    val CurrentTime :LiveData<Long> get() = _CurrentTime

    val CurrentTimeString = Transformations.map(CurrentTime) { time->
        DateUtils.formatElapsedTime(time)

    }

    private val _word = MutableLiveData<String>()
    val word :LiveData<String> get() = _word
    // The current score
    private val _score = MutableLiveData<Int>()
    val score : LiveData<Int> get() = _score

    private lateinit var wordList: MutableList<String>

    private val _gameFinished = MutableLiveData<Boolean>()
    val gameFinished :LiveData<Boolean>
    get() = _gameFinished

    init {
        Log.i("GameViewModel","Game ViewModel Created")
        resetList()
        nextWord()
        _score.value = 0
        _word.value=""
        _CurrentTime.value = COUNTDOWN_TIME
        _gameFinished.value=false
        timer = object :CountDownTimer(COUNTDOWN_TIME, ONE_SECOND)
        {
            override fun onTick(p0: Long) {
                _CurrentTime.value = (p0 / ONE_SECOND)
            }

            override fun onFinish() {
               _CurrentTime.value = DONE
                _gameFinished.value = true
            }
        }
        timer.start()
    }

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
        Log.i("GameViewModel","GameViewModelDestroyed")
    }
    private fun resetList() {
        wordList = mutableListOf(
                "queen",
                "hospital",
                "basketball",
                "cat",
                "change",
                "snail",
                "soup",
                "calendar",
                "sad",
                "desk",
                "guitar",
                "home",
                "railway",
                "zebra",
                "jelly",
                "car",
                "crow",
                "trade",
                "bag",
                "roll",
                "bubble"
        )
        wordList.shuffle()
    }

    private fun nextWord() {
        //Select and remove a word from the list
        if (wordList.isEmpty()) {
            resetList()
        }
        _word.value = wordList.removeAt(0)

    }
    fun onSkip() {
        _score.value = (score.value)?.minus(1)
        nextWord()
    }

    fun onCorrect() {
        _score.value=(score.value)?.plus(1)
        nextWord()
    }
    fun onGameComplete()
    {
        _gameFinished.value =false
    }
}