package com.bhft.models.context

data class BodyAssertionContext(
    val jsonPathToValue: MutableMap<String, String> = mutableMapOf(),
    val isNotBlankPaths: MutableList<String> = mutableListOf(),
    val isBlankPaths: MutableList<String> = mutableListOf(),
    val isOriginalPaths: MutableList<String> = mutableListOf(),
    var isEmptyJsonArray: Boolean? = null
) {
    fun jsonPath(path: String, value: Any?) =
        jsonPathToValue.put(path, value.toString())

    fun isNotBlank(path: String) = isNotBlankPaths.add(path)

    fun isBlank(path: String) = isBlankPaths.add(path)

    fun isOriginal(path: String) = isOriginalPaths.add(path)

    fun isEmptyJsonArray() { isEmptyJsonArray = true }

    fun isNotEmptyJsonArray() { isEmptyJsonArray = false }
}