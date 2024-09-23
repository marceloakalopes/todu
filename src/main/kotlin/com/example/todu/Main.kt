package com.example.todu

import checkForUpdates
import com.example.todu.models.Task
import com.example.todu.services.*
import java.time.LocalDate

private val PATH = System.getProperty("user.home") + "/.todu/tasks.txt"

/** ---------------------------------------- MAIN FUNCTION ---------------------------------------- */
fun main(args: Array<String>) {
    if (args.isEmpty()) {
        printInstructions()
        return
    }

    // Create the directory and the file if they do not exist
    createTodoDirectoryIfNotExists()
    createTasksFileIfNotExists()

    val todoTasksList: List<Task> = readData(PATH)

    when (args[0]) {
        "list" -> handleListCommand(args, todoTasksList)
        "new" -> handleNewTaskCommand(todoTasksList)
        "del" -> handleDeleteTaskCommand(args, todoTasksList)
        "check" -> handleCheckTaskCommand(args, todoTasksList)
        "uncheck" -> handleUncheckTaskCommand(args, todoTasksList)
        "--update" -> checkForUpdates()
        "--help" -> printInstructions()
        else -> println("todu: '${args[0]}' is not a valid command. See 'todu --help'")
    }
}

/**
 * This function handles the 'list' command and its options
 * @param [args] the command line arguments
 * @param [todoTasksList] the list of tasks
 */
private fun handleListCommand(args: Array<String>, todoTasksList: List<Task>) {
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
            else -> println("todu: '${args[1]}' is not a valid option. See 'todu --help'")
        }
    }
}

/**
 * This function handles the 'new' command
 * @param [todoTasksList] the list of tasks
 */
private fun handleNewTaskCommand(todoTasksList: List<Task>) {
    // Get all indexes of tasks and sort them
    val listOfIndexes = getAllIndexesOfTasksAndSort(todoTasksList)

    // If the list is empty, start with ID 1
    val newTaskId = if (listOfIndexes.isEmpty()) 1 else listOfIndexes.last() + 1

    // Get the new task description from the user
    val newTaskDescription: String = getDescriptionFromUser()

    // Get the new task tags from the user
    val newTaskTags: List<String> = getTagsFromUser()

    // Get the new task due date from the user
    val newTaskDueDate: LocalDate = getDueDateFromUser()

    // Create a new task
    val newTask = Task(newTaskId, false, newTaskDueDate, newTaskTags, newTaskDescription)

    // Rewrite the data
    rewriteData(PATH, todoTasksList, newTask)

    println("todu: new task added")
}


/**
 * This function handles the 'del' command
 * @param [args] the command line arguments
 * @param [todoTasksList] the list of tasks
 */
private fun handleDeleteTaskCommand(args: Array<String>, todoTasksList: List<Task>) {
    when {
        args.size == 1 -> println("todu: missing task ID or option")
        args.size == 2 -> {
            if (args[1] == "--checked-all") {
                if (inputUserYesOrNo("Do you want to delete all checked tasks?")) {
                    val newTasksList = deleteAllCheckedTasks(todoTasksList)
                    rewriteData(PATH, newTasksList)
                    println("todu: all checked tasks were deleted")
                }
            } else {
                val taskId = parseTaskId(args[1]) ?: return
                val taskToBeDeleted = getTaskById(todoTasksList, taskId)
                if (taskToBeDeleted != null) {
                    printFormattedRow(taskToBeDeleted)
                    if (inputUserYesOrNo("Do you want to delete this task?")) {
                        val newTasksList = deleteTaskFromList(todoTasksList, taskId)
                        rewriteData(PATH, newTasksList)
                        println("todu: task $taskId was deleted")
                    }
                } else {
                    println("todu: no task with id $taskId")
                }
            }
        }
        else -> println("todu: too many arguments. See 'todu --help'")
    }
}

/**
 * This function handles the 'check' command
 * @param [args] the command line arguments
 * @param [todoTasksList] the list of tasks
 */
private fun handleCheckTaskCommand(args: Array<String>, todoTasksList: List<Task>) {
    if (args.size != 2) {
        println("todu: please provide a valid task ID. See 'todu --help'")
        return
    }
    val taskId = parseTaskId(args[1]) ?: return
    val taskToBeChecked = getTaskById(todoTasksList, taskId)
    if (taskToBeChecked != null) {
        val newTaskChecked = checkTask(taskToBeChecked)
        val newList = createNewList(deleteTaskFromList(todoTasksList, taskId), newTaskChecked)
        rewriteData(PATH, newList)
        println("todu: checked task $taskId")
    } else {
        println("todu: no task with id $taskId")
    }
}

/**
 * This function handles the 'uncheck' command
 * @param [args] the command line arguments
 * @param [todoTasksList] the list of tasks
 */
private fun handleUncheckTaskCommand(args: Array<String>, todoTasksList: List<Task>) {
    if (args.size != 2) {
        println("todu: please provide a valid task ID. See 'todu --help'")
        return
    }
    val taskId = parseTaskId(args[1]) ?: return
    val taskToBeUnchecked = getTaskById(todoTasksList, taskId)
    if (taskToBeUnchecked != null) {
        val newTaskUnchecked = uncheckTask(taskToBeUnchecked)
        val newList = createNewList(deleteTaskFromList(todoTasksList, taskId), newTaskUnchecked)
        rewriteData(PATH, newList)
        println("todu: unchecked task $taskId")
    } else {
        println("todu: no task with id $taskId")
    }
}

/**
 * This function parses the task ID from the command line arguments
 * @param [taskIdArg] the task ID argument
 */
private fun parseTaskId(taskIdArg: String): Int? {
    return try {
        taskIdArg.toInt()
    } catch (e: NumberFormatException) {
        println("todu: '$taskIdArg' is not a valid task ID. See 'todu --help'")
        null
    }
}
