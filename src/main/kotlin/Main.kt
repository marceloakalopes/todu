import java.io.File
import java.time.LocalDate

const val yellow = "\u001B[38;2;223;183;125m"
const val green = "\u001B[38;2;105;172;150m"
const val blue = "\u001B[38;2;116;188;218m"
const val red = "\u001B[38;2;218;28;23m"
const val purple = "\u001B[38;2;235;147;247m"
const val white = "\u001B[38;2;255;255;255m"

/**
 * Data class to represent a task
 *
 * @param [id] the id of the task
 * @param [done] if the task is done
 * @param [dueDate] the due date of the task
 * @param [tags] the tags of the task
 * @param [description] the description of the task
 *
 * @example Task(1, false, LocalDate.of(2021, 9, 9), listOf("mobile"), "update UI")
 */
data class Task(
    val id : Int,
    val done: Boolean,
    val dueDate: LocalDate,
    val tags: List<String>,
    val description: String
);

// Format when reading from file or when printing?

/**
 * Format words that start with @ to be red in a string
 *
 * @param [mention] the string to be formatted
 * @return the formatted string
 *
 * @example formatMention("hello @world") -> "hello ${red}@world"
 */
fun formatMention(mention: String) : String {
    if (mention.contains("@")) {
        val parts = mention.split(" ")
        val mentionIndex = parts.indexOfFirst { it.startsWith("@") }
        val mentionName = parts[mentionIndex].substring(1)
        return parts.subList(0, mentionIndex).joinToString(" ") + " ${red}@${mentionName} ${white}" + parts.subList(
            mentionIndex + 1,
            parts.size
        ).joinToString(" ")
    } else {
        return mention
    }
}

/**
 * Read data from a file and return a list of tasks
 *
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
        val description = formatMention(parts[4])
        tasks.add(Task(id, done, dueDate, tags, description))
    }
    return tasks
}

/**
 * Print a formatted row of a task
 *
 * @param [task] the task to be formatted
 *
 * @example printFormatedRow(Task(1, false, LocalDate.of(2021, 9, 9), listOf("mobile"), "update UI"))
 */
fun printFormatedRow(task: Task) {
    val id = when (task.id.toString().length) {
        1 -> task.id.toString().padEnd(2)
        else -> task.id.toString().padEnd(1)
    }
    val done = if (task.done) "[x]" else "[ ]"
    val dueDate = task.dueDate.toString()
    val tags = task.tags.joinToString(" ") { "+$it" }
    val description = task.description
    println("${yellow}${id}      ${white}${done}      ${red}${dueDate}      ${purple}${tags} ${white}${description}")

}

fun main(args: Array<String>) {


    val data = readData("/Users/marcelolopes/IdeaProjects/CSD215-Lab-1/tasks")
    val orderedData = data.sortedBy { it.dueDate }
    for (task in orderedData) {
        printFormatedRow(task)
    }

    println("\n----------------------------------------\n")

    println("${green}Overdue")
    println("${yellow}6" + "      " + "${white}[ ]" + "      " + "${red}Tue Sep 09" + "      " + "${purple}+mobile" + " ${white}update UI")
    println("${yellow}2" + "      " + "${white}[x]" + "      " + "${red}Wed Sep 11" + "      " + "${purple}+college" + " ${white}lab #2 ")
    println("${yellow}7" + "      " + "${white}[ ]" + "      " + "${red}Sat Sep 14" + "      " + "${purple}+web" + " ${white}authenication system for produs.io")


    println("\n${green}Today")
    println("${yellow}10" + "     " + "${white}[ ]" + "      " + "${blue}today" + "           " + "${purple}+web +ui" + " ${white}create a button component for the new design")
    println("${yellow}9" + "      " + "${white}[ ]" + "      " + "${blue}today" + "           " + "${purple}+security" + " ${white}create a new password policy")
    println("${yellow}3" + "      " + "${white}[x]" + "      " + "${blue}today" + "           " + "${purple}+meeting" + " ${white}ask ${red}@mike ${white}about the new project")

    println("\n${green}Tomorrow")
    println("${yellow}1" + "      " + "${white}[x]" + "      " + "${blue}tomorrow" + "        " + "${purple}+web" + " ${white}create a new landing page with ${red}@bob")
    println("${yellow}4" + "      " + "${white}[ ]" + "      " + "${blue}tomorrow" + "        " + "${purple}+ios +ui" + " ${white}update the app icon")
}