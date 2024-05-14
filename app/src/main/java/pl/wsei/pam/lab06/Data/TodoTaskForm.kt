package pl.wsei.pam.lab06.Data

import pl.wsei.pam.lab06.Classes.LocalDateConverter
import pl.wsei.pam.lab06.Priority
import java.time.LocalDate

data class TodoTaskForm(
    val id: Int = 0,
    val title: String = "",
    val deadline: Long = LocalDateConverter.toMillis(LocalDate.now()),
    val isDone: Boolean = false,
    val priority: String = Priority.Low.name
)
