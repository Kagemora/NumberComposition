package com.example.numbercomposition.domain.entity

class GameResult (
    val winner:Boolean,
    val countOfRightAnswers:Int,
    val countOfQuestion: Int,
    val gameSettings: GameSetting
)