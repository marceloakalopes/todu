package com.example.todo.utils

import java.time.LocalDate

// Format when reading from file or when printing? Answer: when printing

/**
 * Format words that start with @ to be red in a string
 * @param [mention] the string to be formatted
 * @return the formatted string
 * @example formatMention("hello @world") -> "hello ${red}@world"
 */
fun formatMention(mention: String): String {
    if (mention.contains("@")) {
        val parts = mention.split(" ")
        val mentionIndex = parts.indexOfFirst { it.startsWith("@") }
        return when (mentionIndex) {
            0 -> "${red}${parts[mentionIndex]}${white} ${parts.subList(1, parts.size).joinToString(" ")}"
            parts.size - 1 -> "${parts.subList(0, mentionIndex).joinToString(" ")} ${red}${parts[mentionIndex]}${white}"
            else -> "${
                parts.subList(0, mentionIndex).joinToString(" ")
            } ${red}${parts[mentionIndex]}${white} ${parts.subList(mentionIndex + 1, parts.size).joinToString(" ")}"
        }
    } else {
        return mention
    }
}

/**
 * Format a date to a string
 * @param [date] the date to be formatted
 * @return the formatted date
 * @example formatDate(LocalDate.of(2021, 9, 9)) -> "Tue Sep 09"
 */
fun formatDate(date: LocalDate): String {
    val today = LocalDate.now()
    val tomorrow = today.plusDays(1)

    val formattedDate = when {
        date.isBefore(today) -> "${
            date.dayOfWeek.toString().substring(0, 3).lowercase().replaceFirstChar { char -> char.uppercase() }
        } ${
            date.month.toString().substring(0, 3).lowercase().replaceFirstChar { char -> char.uppercase() }
        } ${date.dayOfMonth}"

        date == today -> "today"
        date == tomorrow -> "tomorrow"
        else -> "${
            date.dayOfWeek.toString().substring(0, 3).lowercase().replaceFirstChar { char -> char.uppercase() }
        } ${
            date.month.toString().substring(0, 3).lowercase().replaceFirstChar { char -> char.uppercase() }
        } ${date.dayOfMonth}"
    }

    // Pad the formatted date to a fixed length of 15 characters
    return formattedDate.padEnd(15)
}