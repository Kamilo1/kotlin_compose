package pl.wsei.pam.lab06.Classes

import android.app.Application
import pl.wsei.pam.lab06.Interfaces.AppContainer

class TodoApplication : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this.applicationContext)
    }
}