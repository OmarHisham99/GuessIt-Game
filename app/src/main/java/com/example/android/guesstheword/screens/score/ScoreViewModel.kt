package com.example.android.guesstheword.screens.score

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScoreViewModel(FinalScore : Int) : ViewModel() {
    private val _score = MutableLiveData<Int>()
    val score :LiveData<Int> get() = _score

    private val _eventPlayAgain = MutableLiveData<Boolean>()
    val eventPlayAgain:LiveData<Boolean> get() = _eventPlayAgain

     init {
         _score.value = FinalScore

     }
    fun onPlayAgain()
    {
        _eventPlayAgain.value = true
    }
    fun onPlayAgainComplete()
    {
        _eventPlayAgain.value= false
    }
}