package com.bhft.containers

import com.bhft.exceptions.StartAppContainerException
import org.testcontainers.containers.GenericContainer
import org.testcontainers.utility.DockerImageName

object TodoAppContainer {

    val url: String

    private const val IMAGE_NAME = "todo-app"
    private const val IMAGE_VERSION = "latest"

    private const val PORT = 4242

    private val container: GenericContainer<*>

    init {
        val todoAppImageName = DockerImageName.parse("$IMAGE_NAME:$IMAGE_VERSION")
        try {
            container = GenericContainer(todoAppImageName).withExposedPorts(PORT)
            container.start()
            val appPort = container.getMappedPort(PORT)
            url = "http://${container.host}:$appPort"
        } catch (e: Exception) {
            throw StartAppContainerException(e.message)
        }
    }

    fun stop() = container.stop()

}