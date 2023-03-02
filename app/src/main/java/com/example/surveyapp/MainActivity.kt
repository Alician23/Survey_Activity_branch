package com.example.surveyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider

const val EXTRA_YES_COUNT = "com.alicia.survey_activity.YES_COUNT"
const val EXTRA_NO_COUNT = "com.alicia.survey_activity.NO_COUNT"

class MainActivity : AppCompatActivity() {

    private lateinit var questionTextView: TextView
    private lateinit var yesTotalVote: TextView
    private lateinit var noTotalVote: TextView
    private lateinit var yesButton: Button
    private lateinit var noButton: Button
    private lateinit var resultButton: Button
    lateinit var resetButton: Button


    private var yesCount = 0
    private var noCount = 0


    private val surveyResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            handleSurveyResult(result)// this method is called when launched
    }

    private val surveyViewModel: SurveyViewModel by lazy {
        ViewModelProvider(this).get(SurveyViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        questionTextView = findViewById(R.id.tvQuestion)
        yesTotalVote = findViewById(R.id.tvYesLabel)
        noTotalVote = findViewById(R.id.tvNoLabel)
        yesButton = findViewById(R.id.btnYes)
        noButton = findViewById(R.id.btnNo)
        resultButton = findViewById(R.id.btnResult)
        resetButton = findViewById(R.id.btnReset)

        // Yes button is pressed by users. Yes responses are tally by sumYesCount method.
        yesButton.setOnClickListener {
            yesCount++
        }
        // sumNoCount function starts counting when users pressed the no button to answer no.
        noButton.setOnClickListener {
            noCount++
        }

        resetButton.setOnClickListener {
            yesCount = 0
            noCount = 0
        }

        // Reset yes and no TextView components to zeros
        resultButton.setOnClickListener {
            // begin a new activity to show results
            val resultsIntent = Intent( this, SurveyResultActivity::class.java)
            resultsIntent.putExtra(EXTRA_YES_COUNT, yesCount)
            resultsIntent.putExtra(EXTRA_NO_COUNT, noCount)
            resultLauncher.launch(resultsIntent)
        }

    fun handleSurveyResult(result: ActivityResult?) {
        if (result?.resultCode == RESULT_OK) {
            val intent = result.data
            val shouldReset = intent?.getBooleanExtra(EXTRA_DID_RESET_RESULT, false)?: false
            if (shouldReset) {
                surveyViewModel.clearCount()
                yesTotalVote.setText(String.format("%d", surveyViewModel.yesRespones))
                noTotalVote.setText(String.format("%d", surveyViewModel.noResponses))
            }
            Toast.makeText(this, "Result screen", Toast.LENGTH_LONG).show()
        } else if (result?.resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "Back to main", Toast.LENGTH_LONG).show()
        }
    }

     fun updateCounts() {
        yesTotalVote.text = "$yesCount + votes"
        noTotalVote.text = "$noCount + votes"
        }

     fun showResults() {
        val showResultsIntent = Intent (this, SurveyResultActivity::class.java)
        showResultsIntent.putExtra(EXTRA_YES_COUNT,SurveyViewModel.yesRespones)
        showResultsIntent.putExtra(EXTRA_NO_COUNT,SurveyViewModel.noResponses)
        surveyResultLauncher.launch(showResultsIntent)
        }
    }

}









