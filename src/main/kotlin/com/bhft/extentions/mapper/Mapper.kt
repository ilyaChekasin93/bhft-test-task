package com.bhft.extentions.mapper

import com.bhft.models.*
import com.bhft.models.context.RequestContext
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonParser

inline fun RequestContext.toRequest(
    method: RequestMethod,
    func: RequestContext.() -> Unit
): Request<Any> {
    this.func()

    return this.toRequest(method)
}

inline fun <reified T : Any> String.fromJson(): T =
    Gson().fromJson(this, T::class.java)

fun Any.toJson(): String = Gson().toJson(this)

fun Any.toJsonElement(): JsonElement =
    when (this) {
        is String -> JsonParser.parseString(this)
        else -> JsonParser.parseString(this.toJson())
    }