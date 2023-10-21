package com.example.common.utils


sealed class UIState<out T> {
    data class Success<T>(val data: T) : UIState<T>()
    data class Error(val error: CustomError) : UIState<Nothing>()
    object Loading : UIState<Nothing>()
    object Empty : UIState<Nothing>()
}