package com.bhft.storage

import com.bhft.exceptions.ResponseNotFoundException
import com.bhft.models.Response

object ResponseStorage {

    private val responses: MutableList<Response<String>> = mutableListOf()

    fun addResponses(responses: Collection<Response<String>>) = this.responses.addAll(responses)

    fun lastResponse(): Response<String> =
        try {
            responses.last()
        } catch (e: NoSuchElementException) {
            throw ResponseNotFoundException()
        }

    fun responses(): List<Response<String>> = responses

    fun clear() = responses.clear()
}