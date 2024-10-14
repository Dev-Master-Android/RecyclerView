package com.example.recyclerview

import java.io.Serializable

data class Item(
    val image: Int,
    val title: String,
    val description: String
) : Serializable
