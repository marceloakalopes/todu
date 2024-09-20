package com.example.todo

import com.example.todo.models.Task
import com.example.todo.services.*
import java.time.LocalDate

const val PATH = "/Users/marcelolopes/IdeaProjects/CSD215-Lab-1/tasks.txt"

/** ---------------------------------------- MAIN FUNCTION ---------------------------------------- */

fun main(args: Array<String>) {

    when {
        args.isEmpty() -> println("Usage: todo [list] [add] [done] [remove] [help]")

        args.isNotEmpty() -> {
            val todoTasksList: List<Task> = readData(PATH)

            when (args[0]) {
                "list" -> {
                    when (args[1]) {
                        "-a" -> displayTodo(todoTasksList)
                        "-o" -> printTasksOverdue(todoTasksList)
                        "-t" -> printTasksToday(todoTasksList)
                        "-tm" -> printTasksTomorrow(todoTasksList)
                        "-l" -> printTasksLater(todoTasksList)
                        else -> println("todo: ${args[0]} '${args[1]}' is not a valid command. See 'todo --help'")
                    }
                }

                "new" -> {
                    if (inputUserYesOrNo("Do you want to add a new task?")) {
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
                    } else {
                        return
                    }
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
                        } else {
                            return
                        }
                    } else {
                        println("todo: no task with id $taskId")
                    }
                }

                "check" -> {

                }

                else -> println("todo: '${args[0]}' is not a valid command. See 'todo --help'")
            }
        }

    }


    /** ---------------------------------------- Hard Coded Examples ---------------------------------------- */

//    println("${green}Overdue")
//    println("${yellow}6" + "      " + "${white}[ ]" + "      " + "${red}Tue Sep 09" + "      " + "${purple}+mobile" + " ${white}update UI")
//    println("${yellow}2" + "      " + "${white}[x]" + "      " + "${red}Wed Sep 11" + "      " + "${purple}+college" + " ${white}lab #2 ")
//    println("${yellow}7" + "      " + "${white}[ ]" + "      " + "${red}Sat Sep 14" + "      " + "${purple}+web" + " ${white}authentication system for produs.io")
//
//
//    println("\n${green}Today")
//    println("${yellow}10" + "     " + "${white}[ ]" + "      " + "${blue}today" + "           " + "${purple}+web +ui" + " ${white}create a button component for the new design")
//    println("${yellow}9" + "      " + "${white}[ ]" + "      " + "${blue}today" + "           " + "${purple}+security" + " ${white}create a new password policy")
//    println("${yellow}3" + "      " + "${white}[x]" + "      " + "${blue}today" + "           " + "${purple}+meeting" + " ${white}ask ${red}@mike ${white}about the new project")
//
//    println("\n${green}Tomorrow")
//    println("${yellow}1" + "      " + "${white}[x]" + "      " + "${blue}tomorrow" + "        " + "${purple}+web" + " ${white}create a new landing page with ${red}@bob")
//    println("${yellow}4" + "      " + "${white}[ ]" + "      " + "${blue}tomorrow" + "        " + "${purple}+ios +ui" + " ${white}update the app icon")
}