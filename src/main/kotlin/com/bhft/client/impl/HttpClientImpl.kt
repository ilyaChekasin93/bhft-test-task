package com.bhft.client.impl

import com.bhft.client.Client
import com.bhft.models.Request
import com.bhft.models.RequestMethod
import com.bhft.models.Response
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

object HttpClientImpl : Client {

    override fun <T> sendRequest(request: Request<T>): Response<String> =
        HttpClient.newBuilder().build()
            .send(request.toHttpRequest(), HttpResponse.BodyHandlers.ofString())
            .toResponse()

    private fun <T> Request<T>.toHttpRequest(): HttpRequest =
        HttpRequest.newBuilder()
            .uri(URI(uri))
            .headers(headers)
            .methodAndBody(method, this.body())
            .build()

    private fun HttpRequest.Builder.headers(headers: Map<String, String>?): HttpRequest.Builder {
        headers?.forEach { header -> header(header.key, header.value) }

        return this
    }

    private fun HttpRequest.Builder.methodAndBody(method: RequestMethod, body: String?) =
        when(method) {
            RequestMethod.GET -> GET()
            RequestMethod.POST -> POST(body.toBodyPublishers())
            RequestMethod.PUT -> PUT(body.toBodyPublishers())
            RequestMethod.DELETE -> DELETE()
        }

    private fun String?.toBodyPublishers() =
        if (this != null) {
            HttpRequest.BodyPublishers.ofString(this)
        } else {
            HttpRequest.BodyPublishers.noBody()
        }

    private fun HttpResponse<String>.toResponse() =
        Response(statusCode(), body(), headers().map())

}