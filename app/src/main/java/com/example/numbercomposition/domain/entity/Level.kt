package com.example.numbercomposition.domain.entity

import java.io.Serializable

enum class Level { //enum неявно реализуют Serializble. объект преращает в набор байтов и обратно
    TEST,EASY,NORMAL,HARD
}
//Serializble используется для "невстроенные типов данных" не инт,стринг, а те,которые мы сами создали