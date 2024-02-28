package com.example.numbercomposition.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class Level : Parcelable{ //enum неявно реализуют Serializble. объект преращает в набор байтов и обратно
    TEST,EASY,NORMAL,HARD
}
//Serializble используется для "невстроенные типов данных" не инт,стринг, а те,которые мы сами создали