package com.bhft.models.context

import com.bhft.models.StatusCodeType

data class ResponseAssertionContext(
    val code: Int? = null,
    val body: Any? = null,
    val headers: MutableMap<String, List<String>> = mutableMapOf(),
    var status: StatusCodeType? = null,
    val jsonPathToValue: MutableMap<String, String> = mutableMapOf(),
    val isNotBlankPaths: MutableList<String> = mutableListOf(),
    val isBlankPaths: MutableList<String> = mutableListOf(),
    val isOriginalPaths: MutableList<String> = mutableListOf(),
    var isEmptyJsonArray: Boolean? = null
)