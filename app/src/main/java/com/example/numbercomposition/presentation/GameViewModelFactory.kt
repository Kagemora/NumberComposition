package com.example.numbercomposition.presentation

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.numbercomposition.domain.entity.Level
import java.lang.RuntimeException

class GameViewModelFactory(
    private val level: Level,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(GameViewModel::class.java)) {
            return GameViewModel(application, level) as T
        }
        throw RuntimeException("Unknown view model class $modelClass")
    }
}
/*
Если нужно что-то передать в конструктор ViewModel, то необходимо
создать фабрику вью моделей см.выше, куда мы передаем параметры, которые нам необходимы
при инициализации , создаем проверку, наша ли это вью модель
 */