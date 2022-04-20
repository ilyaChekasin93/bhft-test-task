package com.bhft.engine.impl

import com.bhft.client.Client
import com.bhft.client.impl.HttpClientImpl
import com.bhft.engine.HttpEngine
import com.bhft.executables.ResponseExecutables
import com.bhft.models.*
import com.bhft.models.context.ResponseAssertionContext
import com.bhft.storage.ResponseStorage
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.function.Executable
import kotlin.reflect.KVisibility
import kotlin.reflect.full.memberFunctions

object HttpEngineImpl: HttpEngine {

    private val client: Client = HttpClientImpl
    private val httpContext: ResponseStorage = ResponseStorage
    private val assertExecutables: ResponseExecutables = ResponseExecutables

    override fun send(vararg requests: Request<Any>) {
        httpContext.clear()
        httpContext.addResponses(
            requests.map { client.sendRequest(it) }
        )
    }

    override fun response(expected: ResponseAssertionContext) =
        Assertions.assertAll(
            getCalledExecutables(expected, httpContext.lastResponse())
        )

    override fun response(): Response<String> = httpContext.lastResponse()

    private fun getCalledExecutables(expected: ResponseAssertionContext, actual: Response<String>) =
        getExecutables(expected, actual)
            .mapNotNull { it.call(assertExecutables, expected, actual) }
            .map { it as Executable }
            .toList()

    private fun getExecutables(expected: ResponseAssertionContext, actual: Response<String>) =
        assertExecutables::class.memberFunctions
            .filter { it.visibility == KVisibility.PUBLIC }
            .filter { it.parameters.size == 3 }
            .filter { func ->
                val argumentsClass = func.parameters.map { it.type.classifier }
                argumentsClass[0] == assertExecutables::class &&
                argumentsClass[1] == expected::class &&
                argumentsClass[2] == actual::class &&
                func.returnType.classifier == Executable::class
            }.toList()
}