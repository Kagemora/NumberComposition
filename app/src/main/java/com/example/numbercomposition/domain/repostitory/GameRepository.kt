package com.example.numbercomposition.domain.repostitory

import com.example.numbercomposition.domain.entity.GameSetting
import com.example.numbercomposition.domain.entity.Level
import com.example.numbercomposition.domain.entity.Question

interface GameRepository {
    fun generateQuestion(
        maxSumValue: Int,
        countOfOptions:Int
    ):Question
    fun getGameSettings(level:Level):GameSetting
}