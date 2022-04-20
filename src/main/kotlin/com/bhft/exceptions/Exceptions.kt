package com.bhft.exceptions

class ResponseNotFoundException : RuntimeException("Response not found")

class StartAppContainerException(override val message: String?) : RuntimeException("App container start error: $message")