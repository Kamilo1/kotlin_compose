package pl.wsei.pam.lab06.Classes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import pl.wsei.pam.lab06.Data.TodoTaskForm
import pl.wsei.pam.lab06.Data.TodoTaskUiState
import pl.wsei.pam.lab06.Interfaces.TodoTaskRepository
import pl.wsei.pam.lab06.toTodoTask

class FormViewModel(private val repository: TodoTaskRepository) : ViewModel() {

    var todoTaskUiState by mutableStateOf(TodoTaskUiState())
        private set

    suspend fun save() {

        repository.insertItem(todoTaskUiState.todoTask.toTodoTask())

    }

    fun updateUiState(todoTaskForm: TodoTaskForm) {
        todoTaskUiState = TodoTaskUiState(todoTask = todoTaskForm, isValid = validate(todoTaskForm))
    }

    private fun validate(uiState: TodoTaskForm = todoTaskUiState.todoTask): Boolean {
        return with(uiState) {
            title.isNotBlank()
        }
    }
}





