package pl.wsei.pam.lab06.Interfaces

import kotlinx.coroutines.flow.Flow
import pl.wsei.pam.lab06.Data.TodoTask

interface TodoTaskRepository {
    fun getAllAsStream(): Flow<List<TodoTask>>
    fun getItemAsStream(id: Int): Flow<TodoTask?>
    suspend fun insertItem(item: TodoTask)
    suspend fun deleteItem(item: TodoTask)
    suspend fun updateItem(item: TodoTask)
}