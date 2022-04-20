package com.bhft.client

import com.bhft.models.Request
import com.bhft.models.Response

interface Client {

    fun <T> sendRequest(request: Request<T>): Response<String>

}