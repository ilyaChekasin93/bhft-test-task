package com.bhft

import com.bhft.Steps.createDuplicateTask
import com.bhft.Steps.createDuplicateTaskWithDifferentCompletedSigns
import com.bhft.Steps.createTask
import com.bhft.Steps.createTaskWithoutCompletedSign
import com.bhft.Steps.createTaskWithoutText
import com.bhft.Steps.deleteTask
import com.bhft.Steps.singleTaskByOffset
import com.bhft.Steps.taskIsNotPresent
import com.bhft.Steps.taskIsOriginal
import com.bhft.Steps.taskIsPresent
import com.bhft.Steps.taskListIsEmpty
import com.bhft.Steps.updateTask
import com.bhft.containers.TodoAppContainer
import com.bhft.extentions.*
import org.junit.jupiter.api.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class TodoAppTest {

    @ParameterizedTest
    @ValueSource(booleans = [ true, false ])
    fun `create opened and closed tasks test`(completed: Boolean) {
        val id = randomId()
        val text = "Text for task create test"

        createTask(id = id, text = text, completed = completed)
        taskIsPresent(id = id, text = text, completed = completed)
    }

    @ParameterizedTest
    @ValueSource(booleans = [ true, false ])
    fun `delete opened and closed task test`(completed: Boolean) {
        val id = randomId()
        val text = "Text for task delete test"

        createTask(id = id, text = text, completed = completed)
        deleteTask(id)
        taskIsNotPresent(id)
    }

    @ParameterizedTest
    @ValueSource(booleans = [ true, false ])
    fun `open and close task test`(completed: Boolean) {
        val id = randomId()
        val text = "Text for close task test"

        createTask(id = id, text = text, completed = !completed)
        updateTask(id = id, text = text, completed = completed)
        taskIsPresent(id = id, text = text, completed = completed)
    }

    @ParameterizedTest
    @ValueSource(booleans = [ true, false ])
    fun `change opened and closed task description test`(completed: Boolean) {
        val id = randomId()
        val text = "Text for change task text test"
        val changedTaskTest = "Text for change task text test after edit"

        createTask(id = id, text = text, completed = completed)
        updateTask(id = id, text = changedTaskTest, completed = completed)
        taskIsPresent(id = id, text = changedTaskTest, completed = completed)
    }

    @ParameterizedTest
    @ValueSource(booleans = [ true, false ])
    fun `creating tasks with same description and completed sign test`(completed: Boolean) {
        val firstId = randomId()
        val secondId = randomId()
        val text = "Text for task create test"

        createTask(id = firstId, text = text, completed = completed)
        createTask(id = secondId, text = text, completed = completed)

        taskIsPresent(id = firstId, text = text, completed = completed)
        taskIsPresent(id = secondId, text = text, completed = completed)
    }

    @Test
    @Order(2)
    fun `get task by offset test`() {
        val firstId = randomId()
        val secondId = randomId()
        val thirdId = randomId()
        val fourthId = randomId()

        val text = "Text for task create test"

        listOf(firstId, secondId, thirdId, fourthId).forEach { createTask(id = it, text = text) }

        singleTaskByOffset(2, thirdId, text)
    }

    @Test
    @Order(1)
    fun `get tasks empty list test`() {
        taskListIsEmpty()
    }

    @ParameterizedTest
    @ValueSource(booleans = [ true, false ])
    fun `create opened and closed task without text test`(completed: Boolean) {
        val id = randomId()

        createTaskWithoutText(id = id, completed = completed)
        taskIsNotPresent(id = id)
    }

    @Test
    fun `create task without completed sign test`() {
        val id = randomId()
        val text = "Text for change task text test"

        createTaskWithoutCompletedSign(id = id, text = text)
        taskIsNotPresent(id = id)
    }

    @ParameterizedTest
    @ValueSource(booleans = [ true, false ])
    fun `create opened and closed duplicate tasks test`(completed: Boolean) {
        val id = randomId()
        val text = "Text for task create test"

        createDuplicateTask(id = id, text = text, completed = completed)
        taskIsOriginal(id = id)
    }

    @ParameterizedTest
    @ValueSource(booleans = [ true, false ])
    fun `create opened and closed duplicate tasks with different completed signs test`(completed: Boolean) {
        val id = randomId()
        val text = "Text for task create test"

        createDuplicateTaskWithDifferentCompletedSigns(id = id, text = text, completed = completed)
        taskIsOriginal(id = id)
    }

    companion object {

        @JvmStatic
        @AfterAll
        fun afterAll() {
            TodoAppContainer.stop()
        }

    }
}