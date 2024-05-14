package pl.wsei.pam.lab06.Data

import pl.wsei.pam.lab06.Priority
import java.time.LocalDate

data class TodoTask(
    val title: String,
    val deadline: LocalDate,
    val isDone: Boolean,
    val priority: Priority,
    val id: Int
)