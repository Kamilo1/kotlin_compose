package pl.wsei.pam.lab06.Classes

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pl.wsei.pam.lab06.Data.TodoTask
import pl.wsei.pam.lab06.Data.TodoTaskEntity
import pl.wsei.pam.lab06.Interfaces.TodoTaskDao
import pl.wsei.pam.lab06.Interfaces.TodoTaskRepository

class DatabaseTodoTaskRepository(val dao: TodoTaskDao) : TodoTaskRepository {

    override fun getAllAsStream(): Flow<List<TodoTask>> {
        return dao.findAll().map { it ->
            it.map {
                it.toModel()
            }
        }
    }

    override fun getItemAsStream(id: Int): Flow<TodoTask?> {
        return dao.find(id).map {
            it.toModel()
        }
    }

    override suspend fun insertItem(item: TodoTask) {
        dao.insertAll(TodoTaskEntity.fromModel(item))
    }

    override suspend fun deleteItem(item: TodoTask) {
        dao.removeById(TodoTaskEntity.fromModel(item))
    }

    override suspend fun updateItem(item: TodoTask) {
        dao.update(TodoTaskEntity.fromModel(item))
    }
}