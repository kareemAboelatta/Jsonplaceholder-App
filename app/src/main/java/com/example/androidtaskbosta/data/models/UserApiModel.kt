package com.example.androidtaskbosta.data.models

import com.example.androidtaskbosta.domain.models.User

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


fun UserApiModel.mapToUser(): User = User(
    id = id,
    name = name,
    username = username,
    email = email,
    address = address.mapToAddress()
)

private fun UserApiModel.Address.mapToAddress(): User.Address = User.Address(
    street = street,
    suite = suite,
    city = city,
    zipcode = zipcode
)

