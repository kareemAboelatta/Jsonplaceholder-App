package com.example.androidtaskbosta.domain.models

data class User(
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
