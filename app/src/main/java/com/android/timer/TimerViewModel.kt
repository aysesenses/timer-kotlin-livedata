package com.android.timer

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TimerViewModel: ViewModel() {

    val buttonVisible = MutableLiveData<Boolean>()
    val currentTime = MutableLiveData<Long>()

    private var timer: CountDownTimer? = null

    companion object {

        // Time when the game is over
        private const val DONE = 0L

        // Countdown time interval
        private const val ONE_SECOND = 1000L

        // Total time for the game
        private const val COUNTDOWN_TIME = 60000L

    }

    init {
        Log.i("TimerViewModel", "TimerViewModel created!")
        startTimer()
    }

    fun startTimer() {
        buttonVisible.value = false

        //şuan ki zamanı bir değişkene atayıp koruyoruz. Bunu zamanın değerini tekrar başlatmak için Long? çevirdik.
        val lastTime = currentTime.value?.times(1000)

        //currentTime viewmodel ilk başalatıldığında null du. time değeri null ise  zaman time2 den başlasın
        timer = object : CountDownTimer(lastTime ?: COUNTDOWN_TIME, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                //milisaniye cinsinden geçen zamanı günceller
                currentTime.value = millisUntilFinished / ONE_SECOND
            }

            //süre bittiğinde bu method çağrılır ve sürenin değeri OL olduğunda oyun biter
            override fun onFinish() {
                currentTime.value = DONE
            }
        }
        timer?.start()
    }

    fun stopTimer() {
        buttonVisible.value = true
        timer?.cancel()
    }

    override fun onCleared() {
        Log.i("TimeViewModel", "TimerViewModel destroyed!")
        super.onCleared()
        // Cancel the timer
        timer?.cancel()
    }
}