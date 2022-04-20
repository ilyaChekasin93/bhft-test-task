package com.bhft.executables

import com.bhft.extentions.jsonPath
import com.bhft.extentions.mapper.toJsonElement
import com.bhft.models.Response
import com.bhft.models.context.ResponseAssertionContext
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.function.Executable

object ResponseExecutables {

    fun executableForStatusCode(expected: ResponseAssertionContext, actual: Response<String>) =
        expected.code?.let {
            Executable {
                Assertions.assertEquals(it, actual.code)
            }
        }

    fun executableForSuccessSing(expected: ResponseAssertionContext, actual: Response<String>) =
        expected.status?.let {
            Executable {
                Assertions.assertTrue(actual.code.toString().startsWith(it.startWith.toString()))
            }
        }

    fun executableForHeaders(expected: ResponseAssertionContext, actual: Response<String>) =
        if (expected.headers.isNotEmpty()) {
            Executable {
                Assertions.assertEquals(expected.headers, actual.headers)
            }
        } else null

    fun executableForBody(expected: ResponseAssertionContext, actual: Response<String>) =
        expected.body?.let {
            Executable {
                Assertions.assertEquals(it.toJsonElement(), actual.body?.toJsonElement())
            }
        }

    fun executableForBodyIsEmptyJsonArraySing(expected: ResponseAssertionContext,
                                                      actual: Response<String>) =
        expected.isEmptyJsonArray?.let {
            Executable {
                val actualBody = actual.body?.toJsonElement()
                val emptyJsonArray = emptyList<String>().toJsonElement()
                if (it) {
                    Assertions.assertEquals(emptyJsonArray, actualBody)
                } else {
                    Assertions.assertNotEquals(emptyJsonArray, actualBody)
                }
            }
        }

    fun executableForJsonPath(expected: ResponseAssertionContext, actual: Response<String>) =
        Executable {
            expected.jsonPathToValue.forEach { jsonPath ->
                Assertions.assertEquals(
                    actual.body.jsonPath(jsonPath.key).firstOrNull(),
                    jsonPath.value
                )
            }
        }

    fun executableIsNotBlankJsonPath(expected: ResponseAssertionContext, actual: Response<String>) =
        Executable {
            expected.isNotBlankPaths.forEach { jsonPath ->
                val valueByPath = actual.body.jsonPath(jsonPath)
                Assertions.assertTrue(valueByPath.isNotEmpty())
            }
        }

    fun executableIsBlankJsonPath(expected: ResponseAssertionContext, actual: Response<String>) =
        Executable {
            expected.isBlankPaths.forEach { jsonPath ->
                val valueByPath = actual.body.jsonPath(jsonPath)
                Assertions.assertTrue(valueByPath.isEmpty())
            }
        }

    fun executableIsOriginalJsonPath(expected: ResponseAssertionContext, actual: Response<String>) =
        Executable {
            expected.isOriginalPaths.forEach { jsonPath ->
                val valueByPath = actual.body.jsonPath(jsonPath)
                Assertions.assertEquals(valueByPath.size, 1)
            }
        }
}