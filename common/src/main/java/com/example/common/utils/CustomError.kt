package com.example.common.utils

sealed class CustomError : Throwable() {
    object NoInternetError : CustomError()
    data class ServerError(val code: Int) : CustomError()
    object UnknownError : CustomError()
}
