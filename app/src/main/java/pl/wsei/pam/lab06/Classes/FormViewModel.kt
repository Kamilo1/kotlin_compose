package pl.wsei.pam.lab06.Classes

import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import pl.wsei.pam.lab06.Data.TodoTaskForm
import pl.wsei.pam.lab06.Data.TodoTaskUiState
import pl.wsei.pam.lab06.Interfaces.TodoTaskRepository
import pl.wsei.pam.lab06.toTodoTask
import java.time.LocalDate

class FormViewModel(private val repository: TodoTaskRepository) : ViewModel() {

    var todoTaskUiState by mutableStateOf(TodoTaskUiState())


    suspend fun save() {
        if (validate()) {
            repository.insertItem(todoTaskUiState.todoTask.toTodoTask())
        }

    }

    fun updateUiState(todoTaskForm: TodoTaskForm) {
        todoTaskUiState = TodoTaskUiState(todoTask = todoTaskForm, isValid = validate(todoTaskForm))
    }

    private fun validate(uiState: TodoTaskForm = todoTaskUiState.todoTask): Boolean {
        return with(uiState) {
            val currentLocalDate = LocalDate.now()
            val deadlineLocalDate = LocalDateConverter.fromMillis(deadline)
            title.isNotBlank() && deadlineLocalDate.isAfter(currentLocalDate)
        }
    }
}





