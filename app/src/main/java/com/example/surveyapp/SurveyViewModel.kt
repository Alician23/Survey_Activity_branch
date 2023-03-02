package com.example.surveyapp

import androidx.lifecycle.ViewModel

class SurveyViewModel: ViewModel() {
    var yesRespones = 0
    var noResponses = 0

    //sumYes method calls to add all yes answers and save in SurveyViewModel class.
    fun sumYes() {
        yesRespones++
    }

    fun sumNo() {
        noResponses++
    }

    // clearCount method resets yesTotalVote and noTotalVote TextViews to zeros.
    fun clearCount() {
        yesRespones = 0
        noResponses = 0
    }
}


