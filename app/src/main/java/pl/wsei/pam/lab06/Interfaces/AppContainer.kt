package pl.wsei.pam.lab06.Interfaces

import pl.wsei.pam.lab06.NotificationHandler

interface AppContainer {

    val todoTaskRepository: TodoTaskRepository
    val notificationHandler: NotificationHandler
}
