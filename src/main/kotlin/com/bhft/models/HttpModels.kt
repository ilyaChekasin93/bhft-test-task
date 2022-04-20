package com.bhft.models

import com.bhft.extentions.mapper.fromJson
import com.bhft.extentions.mapper.toJson

data class Request<T>(
    val uri: String,
    val method: RequestMethod,
    val body: T? = null,
    val headers: Map<String, String> = mutableMapOf()
) {
    fun body(): String? =
        when(this.body) {
            is String -> this.body
            else -> this.body?.toJson()
        }
}

data class Response<T>(
    val code: Int? = null,
    val body: T? = null,
    val headers: Map<String, List<String>> = mutableMapOf(),
) {
    inline fun <reified Y : Any> body(): Y? =
        body?.toJson()?.fromJson()
}

enum class RequestMethod {
    GET, POST, PUT, DELETE
}

enum class StatusCodeType(val startWith: Int) {
    INFO(1),
    SUCCESS(2),
    REDIRECT(3),
    BAD_REQUEST(4),
    SERVER_ERROR(5)
}