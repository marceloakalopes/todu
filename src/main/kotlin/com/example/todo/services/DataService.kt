package com.example.todo.services

import com.example.todo.models.Task
import com.example.todo.utils.formatMention
import java.io.File
import java.time.LocalDate

/**
 * Read data from a file and return a list of tasks
 * @param [path] the path to the file
 * @return a list of tasks
 */
fun readData(path: String) : List<Task> {
    val tasks = mutableListOf<Task>()
    File(path).forEachLine {
        val parts = it.split(";")
        val id = parts[0].toInt()
        val done = parts[1].toBoolean()
        val dueDate = LocalDate.of(parts[2].split(" ")[0].toInt(), parts[2].split(" ")[1].toInt(), parts[2].split(" ")[2].toInt())
        val tags = parts[3].split(" ")
        val description = formatMention(parts[4]).trim()
        tasks.add(Task(id, done, dueDate, tags, description))
    }
    return tasks
}

fun writeData(path: String, tasks: List<Task>) {
    return
}