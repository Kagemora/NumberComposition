package com.example.numbercomposition.domain.entity

data class GameSetting (
    val maxSumValue:Int,
    val minCountOfRightAnsers:Int,
    val minPercentOfRightAnswers:Int,
    val gameTimeInSeconds:Int
)
