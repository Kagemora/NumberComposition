package com.example.numbercomposition.domain.usecases

import com.example.numbercomposition.domain.entity.GameSetting
import com.example.numbercomposition.domain.entity.Level
import com.example.numbercomposition.domain.repostitory.GameRepository

class GetGameSettingsUseCase(
    private val repository: GameRepository
) {
        operator fun invoke(level: Level): GameSetting {
            return repository.getGameSettings(level)
        }
}