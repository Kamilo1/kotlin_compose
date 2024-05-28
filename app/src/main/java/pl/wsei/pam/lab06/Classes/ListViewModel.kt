package pl.wsei.pam.lab06.Classes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import pl.wsei.pam.lab06.Data.ListUiState
import pl.wsei.pam.lab06.Data.TodoTask
import pl.wsei.pam.lab06.Interfaces.TodoTaskRepository
import pl.wsei.pam.lab06.todoTasks

class ListViewModel(val repository: TodoTaskRepository) : ViewModel() {

    val listUiState: StateFlow<ListUiState> = repository.getAllAsStream()
        .map { tasks -> ListUiState(tasks) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = ListUiState(emptyList())
        )

    init {
        viewModelScope.launch {
            if (repository.getAllAsStream().first().isEmpty()) {
                todoTasks().forEach { task ->
                    repository.insertItem(task)
                }
            }
        }
    }
    fun deleteTask(task: TodoTask) {
        viewModelScope.launch {
            repository.deleteItem(task)
        }
    }


    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}