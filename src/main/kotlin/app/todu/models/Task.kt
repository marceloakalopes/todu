package com.example.todu.models

import java.time.LocalDate

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
    val id: Int,
    val done: Boolean,
    val dueDate: LocalDate,
    val tags: List<String>,
    val description: String
)
