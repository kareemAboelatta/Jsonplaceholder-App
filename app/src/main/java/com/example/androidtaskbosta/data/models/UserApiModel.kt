package com.example.androidtaskbosta.data.models

// For User
data class UserApiModel(
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val address: Address
) {
    data class Address(
        val street: String,
        val suite: String,
        val city: String,
        val zipcode: String
    )
}