package com.bhft.dsl

import com.bhft.engine.HttpEngine
import com.bhft.engine.impl.HttpEngineImpl
import com.bhft.extentions.mapper.toRequest
import com.bhft.models.RequestMethod
import com.bhft.models.context.RequestContext
import com.bhft.models.context.ResponseAssertionContext
import com.bhft.models.context.ResponseContext

inline fun http(init: HttpEngine.() -> Unit) = HttpEngineImpl.init()

inline fun get(func: RequestContext.() -> Unit) =
    RequestContext().toRequest(RequestMethod.GET, func)

inline fun post(func: RequestContext.() -> Unit) =
    RequestContext().toRequest(RequestMethod.POST, func)

inline fun put(func: RequestContext.() -> Unit) =
    RequestContext().toRequest(RequestMethod.PUT, func)

inline fun delete(func: RequestContext.() -> Unit) =
    RequestContext().toRequest(RequestMethod.DELETE, func)

inline fun shouldBe(func: ResponseContext.() -> Unit): ResponseAssertionContext {
    val responseContext = ResponseContext()
    responseContext.func()

    return responseContext.toResponseAssertionContext()
}