package com.bhft.extentions

import com.jayway.jsonpath.JsonPath
import com.bhft.models.*
import net.minidev.json.JSONArray
import java.util.*

fun String?.jsonPath(jsonPath: String): List<String> =
    if (this != null) {
        try {
            when (val result = JsonPath.parse(this).read<Any>(jsonPath)) {
                is JSONArray -> result.map { it.toString() }
                else -> listOf(result.toString())
            }
        } catch (e: Exception) {
            emptyList()
        }
    } else emptyList()

fun String.toBase64String(): String = Base64.getEncoder().encodeToString(this.encodeToByteArray())

fun randomId(): Long = (0..100000).random().toLong()

fun todoBody(text: String?, id: Long = randomId(), completed: Boolean? = false) = TodoModel(id, text, completed)