package com.example.todo

import com.example.todo.models.Task
import com.example.todo.services.*

/** ---------------------------------------- MAIN FUNCTION ---------------------------------------- */

fun main(args: Array<String>) {

    when {
        args.isEmpty() -> println("Usage: todo [list] [add] [done] [remove] [help]")

        args.isNotEmpty() -> {
            val todoTasksList: List<Task> = readData("/Users/marcelolopes/IdeaProjects/CSD215-Lab-1/tasks")

            if (args[0] == "list") {

                if (args[1] == "-a") {
                    // println(args.toList())
                    displayTodo(todoTasksList)

                }

                // Print tasks that are overdue
                if (args[1] == "-o") {
                    printTasksOverdue(todoTasksList)
                }

                // Print tasks that are due today
                if (args[1] == "-t") {
                     printTasksToday(todoTasksList)
                }

                // Print tasks that are due tomorrow
                if (args[1] == "-tm") {
                    printTasksTomorrow(todoTasksList)
                }

                // Print tasks that are due later
                if (args[1] == "-l") {
                    printTasksLater(todoTasksList)
                }

            }

//            if (args[0] == "new") {
//
//            }

            else {
                println("todo: '${args[0]}' is not a valid command. See 'todo --help'")
            }
        }

    }


/** ---------------------------------------- Hard Coded Examples ---------------------------------------- */

//    println("${green}Overdue")
//    println("${yellow}6" + "      " + "${white}[ ]" + "      " + "${red}Tue Sep 09" + "      " + "${purple}+mobile" + " ${white}update UI")
//    println("${yellow}2" + "      " + "${white}[x]" + "      " + "${red}Wed Sep 11" + "      " + "${purple}+college" + " ${white}lab #2 ")
//    println("${yellow}7" + "      " + "${white}[ ]" + "      " + "${red}Sat Sep 14" + "      " + "${purple}+web" + " ${white}authenication system for produs.io")
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