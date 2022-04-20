package com.bhft.models.context

import com.bhft.models.StatusCodeType

data class ResponseContext(
    private var statusCode: Int? = null,
    private var statusType: StatusCodeType? = null,
    private var body: Any? = null,
    private var headers: MutableMap<String, List<String>> = mutableMapOf(),
    private var bodyAssertionContext: BodyAssertionContext = BodyAssertionContext()
) {
    fun info() { statusType = StatusCodeType.INFO }
    fun success() { statusType = StatusCodeType.SUCCESS }
    fun badRequest() { statusType = StatusCodeType.BAD_REQUEST }
    fun serverError() { statusType = StatusCodeType.SERVER_ERROR }

    fun statusCode(value: Int) { statusCode = value }

    fun body(func: BodyAssertionContext.() -> Unit) = bodyAssertionContext.func()

    fun body(value: Any?) { body = value }

    fun header(header: Pair<String, String>) =
        headers.put(header.first, listOf(header.second))

    fun headers(vararg headers: Pair<String, String>) =
        headers.forEach { this.headers[it.first] = listOf(it.second) }

    fun toResponseAssertionContext() =
        ResponseAssertionContext(
            statusCode, body, headers, statusType,
            bodyAssertionContext.jsonPathToValue,
            bodyAssertionContext.isNotBlankPaths,
            bodyAssertionContext.isBlankPaths,
            bodyAssertionContext.isOriginalPaths,
            bodyAssertionContext.isEmptyJsonArray
        )
}