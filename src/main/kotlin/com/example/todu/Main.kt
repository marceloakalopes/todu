package com.example.todu

import checkForUpdates
import com.example.todu.models.Task
import com.example.todu.services.*
import uninstallApp
import java.time.LocalDate

private val PATH = System.getProperty("user.home") + "/.todu/tasks.txt"

/** ---------------------------------------- MAIN FUNCTION ---------------------------------------- */

fun main(args: Array<String>) {

    when {
        args.isEmpty() -> printInstructions()

        args.isNotEmpty() -> {

            // Create the directory and the file if they do not exist
            createTodoDirectoryIfNotExists()
            createTasksFileIfNotExists()

            val todoTasksList: List<Task> = readData(PATH)

            when (args[0]) {
                "list" -> {
                    if (args.size == 1) {
                        displayTodo(todoTasksList)
                    } else {
                        when (args[1]) {
                            "-o" -> {
                                println()
                                printTasksOverdue(todoTasksList)
                                println()
                            }
                            "-t" -> {
                                println()
                                printTasksToday(todoTasksList)
                                println()
                            }
                            "-tm" -> {
                                println()
                                printTasksTomorrow(todoTasksList)
                                println()
                            }
                            "-l" -> {
                                println()
                                printTasksLater(todoTasksList)
                                println()
                            }
                            else -> println("todo: ${args[0]} '${args[1]}' is not a valid command. See 'todo --help'")
                        }
                    }
                }

                "new" -> {
                    // Get all indexes of tasks and sort them
                    val listOfIndexes = getAllIndexesOfTasksAndSort(todoTasksList)

                    // Get the new task description from the user
                    val newTaskDescription: String = getDescriptionFromUser()

                    // Get the new task due date from the user
                    val newTaskTags: List<String> = getTagsFromUser()

                    // Get the new task due date from the user
                    val newTaskDueDate: LocalDate = getDueDateFromUser()

                    // Create a new task
                    val newTask =
                        Task(listOfIndexes.last() + 1, false, newTaskDueDate, newTaskTags, newTaskDescription)

                    // Rewrite the data
                    rewriteData(PATH, todoTasksList, newTask)

                    println("todu: new task added")
                }

                "del" -> {
                    val taskId: Int = args[1].toInt()
                    val taskToBeDeleted: Task? = getTaskById(todoTasksList, taskId)
                    if (taskToBeDeleted != null) {
                        println()
                        printFormattedRow(taskToBeDeleted)
                        println()
                        if (inputUserYesOrNo("Do you want to delete this task?")) {
                            val newTasksList: List<Task> = deleteTaskFromList(todoTasksList, taskId)
                            rewriteData(PATH, newTasksList)
                            println("todu: task $taskId was deleted")
                        } else {
                            return
                        }
                    } else {
                        println("todu: no task with id $taskId")
                    }
                }

                "check" -> {
                    val taskId: Int = args[1].toInt()
                    val taskToBeChecked: Task? = getTaskById(todoTasksList, taskId)
                    if (taskToBeChecked != null) {
                        val newTaskChecked: Task = checkTask(taskToBeChecked)
                        val newList =
                            createNewList(deleteTaskFromList(todoTasksList, taskToBeChecked.id), newTaskChecked)
                        rewriteData(PATH, newList)
                        println("todu: checked task $taskId")
                    } else {
                        println("todu: no task with id $taskId")
                    }
                }

                "uncheck" -> {
                    val taskId: Int = args[1].toInt()
                    val taskToBeUnchecked: Task? = getTaskById(todoTasksList, taskId)
                    if (taskToBeUnchecked != null) {
                        val newTaskUnchecked: Task = uncheckTask(taskToBeUnchecked)
                        val newList =
                            createNewList(deleteTaskFromList(todoTasksList, taskToBeUnchecked.id), newTaskUnchecked)
                        rewriteData(PATH, newList)
                        println("todu: task $taskId was unchecked")
                    } else {
                        println("todu: no task with id $taskId")
                    }
                }

                "--update" -> checkForUpdates()

                "--uninstall" -> uninstallApp()

                "--help" -> printInstructions()

                else -> println("todu: '${args[0]}' is not a valid command. See 'todu --help'")
            }
        }

    }
}