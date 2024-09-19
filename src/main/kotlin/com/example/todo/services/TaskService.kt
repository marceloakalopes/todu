package com.example.todo.services

import com.example.todo.*
import com.example.todo.models.Task
import com.example.todo.utils.formatDate
import java.time.LocalDate

/**
 * Print a formatted row of a task
 * @param [task] the task to be formatted
 * @example printFormatedRow(Task(1, false, LocalDate.of(2021, 9, 9), listOf("mobile", "ios), "update UI"))
 */
fun printFormattedRow(task: Task) {
    val id = task.id.toString().padEnd(6)
    val done = (if (task.done) "[x]" else "[ ]").padEnd(9)
    val dueDate = when {
        task.dueDate.isBefore(LocalDate.now()) -> "$red${formatDate(task.dueDate)}"
        task.dueDate == LocalDate.now() -> "$blue${formatDate(task.dueDate)}"
        else -> "$blue"+ formatDate(task.dueDate)
    }
    val tags = (task.tags.joinToString(" ") { "+$it" }).lowercase()
    val description = task.description.lowercase()
    println("$yellow${id} $white${done}$dueDate $purple${tags} $white${description}")
}

/**
 * Print tasks that are overdue
 * @param [tasks] the list of tasks
 */
fun printTasksOverdue(tasks: List<Task>) {
    println("${green}Overdue")
    for (task in tasks) {
        if (task.dueDate.isBefore(LocalDate.now())) {
            printFormattedRow(task)
        }
    }
}

/**
 * Print tasks that are due today
 * @param [tasks] the list of tasks
 */
fun printTasksToday(tasks: List<Task>) {
    println("${green}Today")
    for (task in tasks) {
        if (task.dueDate == LocalDate.now()) {
            printFormattedRow(task)
        }
    }
}

/**
 * Print tasks that are due tomorrow
 * @param [tasks] the list of tasks
 */
fun printTasksTomorrow(tasks: List<Task>) {
    println("${green}Tomorrow")
    for (task in tasks) {
        if (task.dueDate == LocalDate.now().plusDays(1)) {
            printFormattedRow(task)
        }
    }
}

/**
 * Print tasks that are due later
 * @param [tasks] the list of tasks
 */
fun printTasksLater(tasks: List<Task>) {
    println("${green}Later")
    for (task in tasks) {
        if (task.dueDate.isAfter(LocalDate.now().plusDays(1))) {
            printFormattedRow(task)
        }
    }
}

/**
 * Display the tasks in a formatted way
 * @param [tasks] the list of tasks to be displayed
 * @example displayTodo(listOf(Task(1, false, LocalDate.of(2021, 9, 9), listOf("mobile", "ios"), "update UI")))
 */
fun displayTodo(tasks: List<Task>) {
    // Print overdue tasks
    printTasksOverdue(tasks)

    // Print today tasks
    println()
    printTasksToday(tasks)

    // Print tomorrow tasks
    println()
    printTasksTomorrow(tasks)

    // Print later tasks
    println()
    printTasksLater(tasks)
}