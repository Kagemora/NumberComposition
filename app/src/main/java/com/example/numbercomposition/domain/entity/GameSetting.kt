package com.example.numbercomposition.domain.entity

import java.io.Serializable

data class GameSetting(
    val maxSumValue: Int,
    val minCountOfRightAnsers: Int,
    val minPercentOfRightAnswers: Int,
    val gameTimeInSeconds: Int
) : Serializable
