package com.example.surveyapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider

const val EXTRA_DID_RESET_RESULT = "com.bignerdranch.android.surveystation.DID_RESET_RESULT"


class SurveyResultActivity : AppCompatActivity() {

    private lateinit var yesCountResult: TextView
    private lateinit var noCountResult: TextView
    lateinit var resetButton: Button
    lateinit var continueSurveyButton: Button

    private val surveyViewModel: SurveyViewModel by lazy {
        ViewModelProvider( this,).get(surveyViewModel::class.java)
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey_result)

        yesCountResult = findViewById(R.id.tv_Yes_Count_Results)
        noCountResult = findViewById(R.id.tv_No_Count_Results)
        resetButton = findViewById(R.id.btnReset)
        continueSurveyButton = findViewById(R.id.btnContinueSurvey)


        val yesCount = intent.getIntExtra(EXTRA_YES_COUNT, 0)
        val noCount = intent.getIntExtra(EXTRA_NO_COUNT, 0)

        surveyViewModel.yesRespones = yesCount
        surveyViewModel.noResponses = noCount

        yesCountResult.setText(String.format("%d",surveyViewModel.yesRespones))
        noCountResult.setText(String.format("%d", surveyViewModel.noResponses))

        resetButton.setOnClickListener {
            resetCount()
        }

        continueSurveyButton.setOnClickListener {
            returnToSurvey()
        }
    }
        // resetCount clear total votes to zero
    private fun resetCount() {
       surveyViewModel.clearCount()
       yesCountResult.setText(String.format("Yes count: %d", surveyViewModel.yesRespones))
       noCountResult.setText(String.format("No count: %d", surveyViewModel.noResponses))
       val resultIntent = Intent()
       resultIntent.putExtra(EXTRA_DID_RESET_RESULT, true)
       setResult(RESULT_OK, resultIntent)
       finish()
    }

    private fun returnToSurvey() {
       finish() // done with SquareActivity
    }



}



