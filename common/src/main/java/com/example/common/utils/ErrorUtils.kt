package com.example.common.utils

import retrofit2.HttpException
import java.io.IOException

fun CustomError.getDisplayMessage(): String {
    return when (this) {
        is CustomError.NoInternetError -> "No internet connection. Please check and try again."
        is CustomError.ServerError -> "A server error occurred: ${this.code}."
        else -> "An unexpected error occurred. Please try again."
    }
}



fun mapToCustomError(error: Throwable): CustomError {
    return when (error) {
        is IOException -> CustomError.NoInternetError
        is HttpException -> CustomError.ServerError(error.code())
        else -> CustomError.UnknownError
    }
}

