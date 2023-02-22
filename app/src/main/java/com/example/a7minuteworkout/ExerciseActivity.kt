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

    private var exerciseList : ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1

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

        exerciseList = Constants.defaultExerciseList()

        setUpRestView()
    }

    private fun setUpRestView(){
        binding?.flRestView?.visibility  = View.VISIBLE
        binding?.tvTitle?.visibility = View.VISIBLE
        binding?.tvExerciseName?.visibility =  View.INVISIBLE
        binding?.flExerciseView?.visibility = View.INVISIBLE
        binding?.ivImage?.visibility =  View.INVISIBLE
        // If we return to this activity from the main activity, the timer should restart
        if (restTimer!= null){
            restTimer?.cancel()
            restProgress = 0
        }
        setRestProgressBar()
    }

    private fun setRestProgressBar(){
        binding?.progressBar?.progress = restProgress
        restTimer = object:CountDownTimer(3000,1000){
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding?.progressBar?.progress = 10-restProgress
                binding?.tvTimer?.text = (10-restProgress).toString()
            }

            override fun onFinish() {
                currentExercisePosition++
                setUpExerciseView()
                Toast.makeText(this@ExerciseActivity,"Exercise Starts Now",Toast.LENGTH_SHORT).show()
            }
        }.start()
    }

    private fun setUpExerciseView(){
        binding?.flRestView?.visibility  = View.INVISIBLE
        binding?.tvTitle?.visibility = View.INVISIBLE
        binding?.tvExerciseName?.visibility =  View.VISIBLE
        binding?.flExerciseView?.visibility = View.VISIBLE
        binding?.ivImage?.visibility =  View.VISIBLE

        if (exerciseTimer!= null){
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }
        binding?.ivImage?.setImageResource(exerciseList!![currentExercisePosition].image)
        binding?.tvExerciseName?.text = exerciseList!![currentExercisePosition].name

        setExerciseProgressBar()
    }

    private fun setExerciseProgressBar(){
        binding?.progressBarExercise?.progress = exerciseProgress
        exerciseTimer = object:CountDownTimer(3000,1000){
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                binding?.progressBarExercise?.progress = 30-exerciseProgress
                binding?.tvTimerExercise?.text = (30-exerciseProgress).toString()
            }

            override fun onFinish() {
                if (currentExercisePosition<exerciseList?.size!! -1 ){
                    setUpRestView()
                }else{
                    Toast.makeText(this@ExerciseActivity,"CONGRATS",Toast.LENGTH_SHORT).show()
                }
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