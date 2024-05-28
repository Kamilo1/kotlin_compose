package pl.wsei.pam.lab06.Classes

import android.content.Context
import pl.wsei.pam.lab06.Interfaces.AppContainer
import pl.wsei.pam.lab06.Interfaces.TodoTaskRepository
import pl.wsei.pam.lab06.NotificationHandler

class AppDataContainer(private val context: Context) : AppContainer {
    override val todoTaskRepository: TodoTaskRepository by lazy {
        val repository = DatabaseTodoTaskRepository(AppDatabase.getInstance(context).taskDao())
        repository
    }
    override val notificationHandler: NotificationHandler by lazy {
        NotificationHandler(context)
    }
}