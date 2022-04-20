package com.bhft.constants

import com.bhft.containers.TodoAppContainer
import com.bhft.extentions.toBase64String

object Constants {

    private const val username = "admin"
    private const val password = "admin"

    val authorizationHeader = "Authorization" to "Basic " + "$username:$password".toBase64String()

    private const val todosUri = "/todos"
    val todosUrl = "${TodoAppContainer.url}$todosUri"

}