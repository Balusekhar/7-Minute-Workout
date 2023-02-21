package com.example.a7minuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import com.example.a7minuteworkout.databinding.ActivityExcerciseBinding

class ExerciseActivity : AppCompatActivity() {
    private var binding : ActivityExcerciseBinding? = null

    private var restTimer : CountDownTimer? = null
    private var restProgress = 0

    private var exerciseTimer : CountDownTimer? = null
    private var exerciseProgress = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExcerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarExercise)

        if(supportActionBar!=null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbarExercise?.setNavigationOnClickListener {
           // onBackPressed()
            finish()
        }
        setUpRestView()
    }

    private fun setUpRestView(){
        // If we return to this activity from the main activity, the timer should restart
        if (restTimer!= null){
            restTimer?.cancel()
            restProgress = 0
        }
        setRestProgressBar()
    }

    private fun setRestProgressBar(){
        binding?.progressBar?.progress = restProgress
        restTimer = object:CountDownTimer(10000,1000){
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding?.progressBar?.progress = 10-restProgress
                binding?.tvTimer?.text = (10-restProgress).toString()
            }

            override fun onFinish() {
                setUpExerciseView()
                Toast.makeText(this@ExerciseActivity,"Exercise Starts Now",Toast.LENGTH_SHORT).show()
            }
        }.start()
    }

    private fun setUpExerciseView(){
        binding?.flProgressBar?.visibility  = View.INVISIBLE
        binding?.tvTitle?.text = "Exercise Name"
        binding?.flExerciseView?.visibility = View.VISIBLE

        if (exerciseTimer!= null){
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }
        setExerciseProgressBar()
    }

    private fun setExerciseProgressBar(){
        binding?.progressBarExercise?.progress = exerciseProgress
        exerciseTimer = object:CountDownTimer(30000,1000){
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                binding?.progressBarExercise?.progress = 30-exerciseProgress
                binding?.tvTimerExercise?.text = (30-exerciseProgress).toString()
            }

            override fun onFinish() {
                Toast.makeText(this@ExerciseActivity,"30 seconds over. Lets rest for 10 seconds",Toast.LENGTH_SHORT).show()
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (restTimer!= null){
            restTimer?.cancel()
            restProgress = 0
        }
        if (exerciseTimer!= null){
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }
        binding = null
    }
}