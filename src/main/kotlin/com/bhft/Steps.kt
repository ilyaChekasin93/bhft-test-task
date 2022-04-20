package com.bhft

import com.bhft.constants.Constants.authorizationHeader
import com.bhft.constants.Constants.todosUrl
import com.bhft.dsl.*
import com.bhft.extentions.randomId
import com.bhft.extentions.todoBody

object Steps {

    fun createTask(text: String, id: Long = randomId(), completed: Boolean = false) {
        http {
            send(
                post {
                    url(todosUrl)
                    header(authorizationHeader)
                    body(todoBody(text, id, completed))
                }
            )
            response(
                shouldBe {
                    success()
                }
            )
        }
    }

    fun updateTask(id: Long, text: String, completed: Boolean) {
        http {
            send(
                put {
                    url("$todosUrl/$id")
                    header(authorizationHeader)
                    body(todoBody(text, id, completed))
                }
            )
            response(
                shouldBe {
                    success()
                }
            )
        }
    }

    fun deleteTask(id: Long) {
        http {
            send(
                delete {
                    url("$todosUrl/$id")
                    header(authorizationHeader)
                }
            )
            response(
                shouldBe {
                    success()
                }
            )
        }
    }

    fun taskIsPresent(id: Long, text: String, completed: Boolean = false) {
        http {
            send(
                get {
                    url(todosUrl)
                    header(authorizationHeader)
                }
            )
            response(
                shouldBe {
                    success()
                    body {
                        isNotBlank("$[?(@.id == '$id')]")
                        jsonPath("$[?(@.id == '$id')].text", text)
                        jsonPath("$[?(@.id == '$id')].completed", completed.toString())
                    }
                }
            )
        }
    }

    fun taskIsNotPresent(id: Long) {
        http {
            send(
                get {
                    url(todosUrl)
                    header(authorizationHeader)
                }
            )
            response(
                shouldBe {
                    success()
                    body {
                        isBlank("$[?(@.id == '$id')]")
                    }
                }
            )
        }
    }

    fun createTaskWithoutText(id: Long = randomId(), completed: Boolean = false) {
        http {
            send(
                post {
                    url(todosUrl)
                    header(authorizationHeader)
                    body(todoBody(null, id, completed))
                }
            )
            response(
                shouldBe {
                    badRequest()
                }
            )
        }
    }

    fun createTaskWithoutCompletedSign(id: Long = randomId(), text: String) {
        http {
            send(
                post {
                    url(todosUrl)
                    header(authorizationHeader)
                    body(todoBody(text, id, null))
                }
            )
            response(
                shouldBe {
                    badRequest()
                }
            )
        }
    }

    fun createDuplicateTask(text: String, id: Long = randomId(), completed: Boolean = false) {
        http {
            send (
                post {
                    url(todosUrl)
                    header(authorizationHeader)
                    body(todoBody(text, id, completed))
                },
                post {
                    url(todosUrl)
                    header(authorizationHeader)
                    body(todoBody(text, id, completed))
                }
            )
            response(
                shouldBe {
                    badRequest()
                }
            )
        }
    }

    fun createDuplicateTaskWithDifferentCompletedSigns(text: String, id: Long = randomId(), completed: Boolean = false) {
        http {
            send (
                post {
                    url(todosUrl)
                    header(authorizationHeader)
                    body(todoBody(text, id, completed))
                },
                post {
                    url(todosUrl)
                    header(authorizationHeader)
                    body(todoBody(text, id, !completed))
                }
            )
            response(
                shouldBe {
                    badRequest()
                }
            )
        }
    }

    fun taskIsOriginal(id: Long) {
        http {
            send(
                get {
                    url(todosUrl)
                    header(authorizationHeader)
                }
            )
            response(
                shouldBe {
                    success()
                    body {
                        isOriginal("$[?(@.id == '$id')]")
                    }
                }
            )
        }
    }

    fun taskListIsEmpty() {
        http {
            send(
                get {
                    url(todosUrl)
                    header(authorizationHeader)
                }
            )
            response(
                shouldBe {
                    success()
                    body {
                        isEmptyJsonArray()
                    }
                }
            )
        }
    }

    fun singleTaskByOffset(offset: Int, id: Long, text: String, completed: Boolean = false) {
        http {
            send(
                get {
                    url(todosUrl)
                    params(
                        "limit" to "1",
                        "offset" to "$offset"
                    )
                    header(authorizationHeader)
                }
            )
            response(
                shouldBe {
                    success()
                    body(
                        listOf(
                            todoBody(id = id, text = text, completed = completed)
                        )
                    )
                }
            )
        }
    }
}