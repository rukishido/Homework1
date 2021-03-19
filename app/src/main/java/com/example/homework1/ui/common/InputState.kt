package com.example.homework1.ui.common


sealed class InputState {
    data class Error<out T>(val errorType: T) : InputState()
    data class Data<out T>(val data: T) : InputState()
}