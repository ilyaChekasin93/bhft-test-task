package com.bhft.models.context

import com.bhft.models.Request
import com.bhft.models.RequestMethod

data class RequestContext(
    private var headers: MutableMap<String, String> = mutableMapOf(),
    private var params: MutableMap<String, String> = mutableMapOf(),
    private var body: Any? = null
) {
    private lateinit var url: String

    fun url(value: String) { url = value }

    fun body(value: Any?) { body = value }

    fun param(param: Pair<String, String>) =
        params.put(param.first, param.second)

    fun params(vararg values: Pair<String, String>) =
        params.putAll(values.toMap())

    fun header(value: Pair<String, String>) =
        headers.put(value.first, value.second)

    fun headers(vararg values: Pair<String, String>) =
        headers.putAll(values.toMap())

    fun toRequest(method: RequestMethod) = Request(fullUrl(), method, body, headers)

    private fun fullUrl(): String =
        if (params.isNotEmpty()) {
            "$url?${params.entries.joinToString( separator = "&" )}"
        } else url
}