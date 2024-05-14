package pl.wsei.pam.lab06.Data

data class TodoTaskUiState(
    var todoTask: TodoTaskForm = TodoTaskForm(),
    val isValid: Boolean = false
)
