package com.bhft.engine

import com.bhft.models.*
import com.bhft.models.context.ResponseAssertionContext

interface HttpEngine {

    fun send(vararg requests: Request<Any>)

    fun response(expected: ResponseAssertionContext)

    fun response(): Response<String>

}