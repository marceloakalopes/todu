package com.example.todo.services

import com.example.todo.models.Task
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDate

const val PATH = "/Users/marcelolopes/IdeaProjects/CSD215-Lab-1/test.txt"

class DataServiceKtTest {

    @Test
    fun createNewList() {
        val initialList = listOf(
            Task(1, false, LocalDate.of(2024, 9, 10), listOf("work"), "Complete the final report")
        )
        val newTask = Task(2, false, LocalDate.of(2024, 9, 14), listOf("home"), "Fix the kitchen sink")

        val updatedList = createNewList(initialList, newTask)

        assertEquals(2, updatedList.size)
        assertEquals(2, updatedList[1].id)
        assertEquals("Fix the kitchen sink", updatedList[1].description)
    }

    @Test
    fun createNewListWithEmptyList() {
        val initialList = emptyList<Task>()
        val newTask = Task(1, false, LocalDate.of(2024, 9, 14), listOf("home"), "Fix the kitchen sink")

        val updatedList = createNewList(initialList, newTask)

        assertEquals(1, updatedList.size)
        assertEquals(1, updatedList[0].id)
        assertEquals("Fix the kitchen sink", updatedList[0].description)
    }

    @Test
    fun deleteTaskFromList() {
        val initialList = listOf(
            Task(1, false, LocalDate.of(2024, 9, 10), listOf("work"), "Complete the final report"),
            Task(2, false, LocalDate.of(2024, 9, 14), listOf("home"), "Fix the kitchen sink")
        )

        val updatedList = deleteTaskFromList(initialList, 1)

        assertEquals(1, updatedList.size)
        assertNull(updatedList.find { it.id == 1 })
    }

    @Test
    fun deleteTaskFromEmptyList() {
        val initialList = emptyList<Task>()

        val updatedList = deleteTaskFromList(initialList, 1)

        assertEquals(0, updatedList.size)
    }

    @Test
    fun deleteNonExistentTask() {
        val initialList = listOf(
            Task(1, false, LocalDate.of(2024, 9, 10), listOf("work"), "Complete the final report")
        )

        val updatedList = deleteTaskFromList(initialList, 2)

        assertEquals(1, updatedList.size)
        assertNotNull(updatedList.find { it.id == 1 })
    }

    @Test
    fun checkTask() {
        val task = Task(1, false, LocalDate.of(2024, 9, 10), listOf("work"), "Complete the report")

        val checkedTask = checkTask(task)

        assertTrue(checkedTask.done)
    }

    @Test
    fun uncheckTask() {
        val task = Task(1, true, LocalDate.of(2024, 9, 10), listOf("work"), "Complete the report")

        val uncheckedTask = uncheckTask(task)

        assertFalse(uncheckedTask.done)
    }

    @Test
    fun readData() {
        val tasks = readData(PATH)

        assertEquals(10, tasks.size)
        assertEquals(1, tasks[0].id)
        assertEquals("Complete the final report for client", tasks[0].description)
    }

    @Test
    fun rewriteData() {
        val path = "src/test/resources/test_output.txt"
        val initialList = listOf(
            Task(1, false, LocalDate.of(2024, 9, 10), listOf("work"), "Complete the report"),
            Task(2, false, LocalDate.of(2024, 9, 14), listOf("home"), "Fix the kitchen sink")
        )

        rewriteData(path, initialList)

        val updatedList = readData(path)
        assertEquals(2, updatedList.size)
    }

    @Test
    fun rewriteDataWithEmptyList() {
        val path = "src/test/resources/test_output_empty.txt"
        val initialList = emptyList<Task>()

        rewriteData(path, initialList)

        val updatedList = readData(path)
        assertEquals(0, updatedList.size)
    }

    @Test
    fun getTaskById() {
        val taskList = listOf(
            Task(1, false, LocalDate.of(2024, 9, 10), listOf("work"), "Complete the report"),
            Task(2, false, LocalDate.of(2024, 9, 14), listOf("home"), "Fix the sink")
        )

        val task = getTaskById(taskList, 2)

        assertNotNull(task)
        assertEquals(2, task?.id)
        assertEquals("Fix the sink", task?.description)
    }

    @Test
    fun getTaskByNonExistentId() {
        val taskList = listOf(
            Task(1, false, LocalDate.of(2024, 9, 10), listOf("work"), "Complete the report")
        )

        val task = getTaskById(taskList, 99)

        assertNull(task)
    }

    @Test
    fun getAllIndexesOfTasksAndSort() {
        val taskList = listOf(
            Task(3, false, LocalDate.of(2024, 9, 10), listOf("work"), "Complete the report"),
            Task(1, false, LocalDate.of(2024, 9, 14), listOf("home"), "Fix the sink"),
            Task(2, false, LocalDate.of(2024, 9, 12), listOf("school"), "Submit assignment")
        )

        val indexes = getAllIndexesOfTasksAndSort(taskList)

        assertEquals(listOf(1, 2, 3), indexes)
    }

    @Test
    fun getAllIndexesOfEmptyTaskList() {
        val taskList = emptyList<Task>()

        val indexes = getAllIndexesOfTasksAndSort(taskList)

        assertTrue(indexes.isEmpty())
    }
}
