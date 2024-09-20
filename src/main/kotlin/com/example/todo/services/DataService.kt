package com.example.todo.services

import com.example.todo.models.Task
import java.io.File
import java.time.LocalDate

/**
 * Create a new list with a new task
 * @param [currentList] the current list of tasks
 * @param [newTask] the new task to be added
 * @return a new list with the new task
 */
fun createNewList(currentList: List<Task>, newTask: Task): List<Task> {
    val tasks = mutableListOf<Task>()
    tasks.addAll(currentList)
    tasks.add(newTask)
    return tasks
}

/**
 * Delete a task from a list
 * @param [currentList] the current list of tasks
 * @param [taskToBeDeleted] the id of the task to be deleted
 * @return a new list without the task to be deleted
 */
fun deleteTaskFromList(currentList: List<Task>, taskToBeDeleted: Int): List<Task> {
    val tasks = mutableListOf<Task>()
    tasks.addAll(currentList)
    tasks.removeIf { it.id == taskToBeDeleted }
    return tasks
}

/**
 * Read data from a file and return a list of tasks
 * @param [path] the path to the file
 * @return a list of tasks
 */
fun readData(path: String): List<Task> {
    val tasks = mutableListOf<Task>()
    File(path).forEachLine {
        val parts = it.split(";")
        val id = parts[0].toInt()
        val done = parts[1].toBoolean()
        val dueDate =
            LocalDate.of(parts[2].split("-")[0].toInt(), parts[2].split("-")[1].toInt(), parts[2].split("-")[2].toInt())
        val tags = parts[3].split(" ")
        val description = parts[4].trim()
        tasks.add(Task(id, done, dueDate, tags, description))
    }
    return tasks
}

/**
 * Write data to a file
 * @param [path] the path to the file
 * @param [currentToDoList] the current list of tasks
 * @param [newTask] the new task to be added
 */
fun rewriteData(path: String, currentToDoList: List<Task>, newTask: Task? = null) {

    if (newTask != null) {
        val newList: List<Task> = createNewList(currentToDoList, newTask);

        File(path).writeText("")

        for (task in newList) {
            File(path).appendText("${task.id};${task.done};${task.dueDate};${task.tags.joinToString(" ")};${task.description}\n")
        }
    } else {
        File(path).writeText("")

        for (task in currentToDoList) {
            File(path).appendText("${task.id};${task.done};${task.dueDate};${task.tags.joinToString(" ")};${task.description}\n")
        }
    }
}

/**
 * Input a yes or no from the user
 * @param [initialMessage] the initial message to be displayed
 * @return true if the user inputs 'y' and false if the user inputs 'n'
 * @example inputUserYesOrNo("Do you want to add a new task?") -> 'y' -> true
 */
fun inputUserYesOrNo(initialMessage: String): Boolean {
    print("$initialMessage (y/n): ")
    val input = readLine()!!.trim().lowercase()
    return when (input) {
        "y" -> true
        "n" -> false
        else -> {
            inputUserYesOrNo("todo: '${input}' a valid input. Use 'y' for yes or 'n' for no:")
        }
    }
}

/**
 * Get the description of a task from the user
 * @return the description of the task
 */
fun getDescriptionFromUser(): String {
    print("Enter the description of the task: ")
    try {
        return readLine()!!.trim()
    } catch (e: Exception) {
        println("Invalid description")
        return getDescriptionFromUser()
    }
}

/**
 * Get the due date of a task from the user
 * @return the due date of the task
 */
fun getTagsFromUser(): List<String> {
    print("Enter the tags of the task: ")
    try {
        return readLine()!!.split(" ")
    } catch (e: Exception) {
        println("Invalid tags")
        return getTagsFromUser()
    }
}

/**
 * Get the due date of a task from the user
 * @return the due date of the task
 * @example
 */
fun getDueDateFromUser(): LocalDate {
    print("Enter the due date of the task (yyyy mm dd): ")
    try {
        val date = readLine()!!.trim()
        return LocalDate.of(date.split(" ")[0].toInt(), date.split(" ")[1].toInt(), date.split(" ")[2].toInt())
    } catch (e: Exception) {
        println("Invalid date")
        return getDueDateFromUser()
    }
}

/**
 * Get a task by its id
 * @param [tasks] the list of tasks
 * @param [id] the id of the task
 * @return the task with the given id
 */
fun getTaskById(tasks: List<Task>, id: Int): Task? {
    for (task in tasks) {
        if (task.id == id) {
            return task
        }
    }
    return null
}

/**
 * Get all indexes of tasks and sort them
 * @param [taskList] the list of tasks
 * @return a list of indexes of tasks
 */
fun getAllIndexesOfTasksAndSort(taskList: List<Task>): List<Int> {
    val indexes = mutableListOf<Int>()
    for (task in taskList) {
        indexes.add(task.id)
    }
    indexes.sort()
    return indexes
}