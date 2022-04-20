package com.bhft.models

data class TodoModel(
    val id: Long,
    val text: String?,
    val completed: Boolean? = false
)