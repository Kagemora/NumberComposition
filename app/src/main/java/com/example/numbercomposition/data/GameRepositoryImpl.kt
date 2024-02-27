package com.example.numbercomposition.data

import com.example.numbercomposition.domain.entity.GameSetting
import com.example.numbercomposition.domain.entity.Level
import com.example.numbercomposition.domain.entity.Question
import com.example.numbercomposition.domain.repostitory.GameRepository
import kotlin.math.*
import kotlin.random.Random

object GameRepositoryImpl:GameRepository {
    private const val MIN_SUM_VALUE = 2
    private const val MIN_ANSWER_VALUE = 1
    override fun generateQuestion(maxSumValue: Int, countOfOptions: Int): Question {
        val sum = Random.nextInt(MIN_SUM_VALUE,maxSumValue+1)//ВОПРОС
        val visibleNumber = Random.nextInt(MIN_ANSWER_VALUE,sum)
        val options = HashSet<Int>()//чтоб не было одинаковых значений
        val rightAnswer = sum-visibleNumber//правильный ответ
        options.add(rightAnswer)//добавляем правильный ответ в коллекцию
        val from =max(rightAnswer-countOfOptions, MIN_ANSWER_VALUE)
        val to = min(maxSumValue-1,rightAnswer+countOfOptions)
        while (options.size<countOfOptions){
            options.add(Random.nextInt(from,to))
        }
        return Question(sum,visibleNumber,options.toList())
    }

    override fun getGameSettings(level: Level): GameSetting {
        return when(level){
            Level.TEST->{
                GameSetting(
                    10,
                    3,
                    50,
                    8
                )
            }
            Level.EASY->{
                GameSetting(
                    10,
                    10,
                    70,
                    60
                )
            }
            Level.NORMAL->{
                GameSetting(
                    20,
                    20,
                    80,
                    8
                )
            }
            Level.HARD->{
                GameSetting(
                    30,
                    30,
                    90,
                    40
                )
            }
        }
    }

}