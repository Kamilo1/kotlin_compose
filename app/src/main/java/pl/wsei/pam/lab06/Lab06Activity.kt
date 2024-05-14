package pl.wsei.pam.lab06

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import pl.wsei.pam.lab06.ui.theme.Lab06Theme
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.Switch
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.launch
import pl.wsei.pam.lab06.Classes.FormViewModel
import pl.wsei.pam.lab06.Classes.ListViewModel
import pl.wsei.pam.lab06.Classes.LocalDateConverter
import pl.wsei.pam.lab06.Classes.TodoApplication
import pl.wsei.pam.lab06.Data.TodoTask
import pl.wsei.pam.lab06.Data.TodoTaskForm
import pl.wsei.pam.lab06.Data.TodoTaskUiState


class Lab06Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab06Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "list") {
        composable("list") { ListScreen(navController = navController) }
        composable("form") { FormScreen(navController = navController) }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    Lab06Theme {
        MainScreen(
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    navController: NavController,
    title: String,
    showBackIcon: Boolean,
    route: String,
    onSaveClick: () -> Unit = {}
) {
    TopAppBar(
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        ),
        title = { Text(text = title) },
        navigationIcon = {
            if (showBackIcon) {
                IconButton(onClick = { navController.navigate(route) }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        },
        actions = {
            if (route !== "form") {
                OutlinedButton(
                    onClick = onSaveClick
                )
                {
                    Text(
                        text = "Zapisz",
                        fontSize = 18.sp
                    )
                }
            } else {
                IconButton(onClick = { }) {
                    Icon(imageVector = Icons.Default.Settings, contentDescription = "")
                }
                IconButton(onClick = {

                }) {
                    Icon(imageVector = Icons.Default.Home, contentDescription = "")
                }
            }
        }
    )
}


@Composable
fun ListScreen(
    navController: NavController,
    viewModel: ListViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val listUiState by viewModel.listUiState.collectAsState()
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                shape = CircleShape,
                content = {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Add task",
                        modifier = Modifier.scale(1.5f)
                    )
                },
                onClick = {
                    navController.navigate("form")
                }
            )
        },
        topBar = {
            AppTopBar(
                navController = navController,
                title = "List",
                showBackIcon = false,
                route = "form",
            )
        },
        content = { it ->
            LazyColumn(
                modifier = Modifier.padding(it)
            ) {
                items(items = listUiState.items, key = { it.id }) {
                    ListItem(it)
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormScreen(
    navController: NavController,
    viewModel: FormViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            AppTopBar(
                navController = navController,
                title = "Form",
                showBackIcon = true,
                route = "list",
                onSaveClick = {
                    coroutineScope.launch {
                        viewModel.save()
                        navController.navigate("list")
                    }
                }
            )
        }
    ) {
        TodoTaskInputBody(
            todoUiState = viewModel.todoTaskUiState,
            onItemValueChange = viewModel::updateUiState,
            modifier = Modifier.padding(it)

        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PriorityDropdownMenu(selectedPriority: Priority, onPrioritySelected: (Priority) -> Unit) {
    var expanded by remember { mutableStateOf(false) }


    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {

        TextField(
            readOnly = true,
            value = selectedPriority.name,
            onValueChange = {},
            label = { Text("Priority") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }
        )


        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            Priority.entries.forEach { priority ->
                DropdownMenuItem(
                    text = { Text(priority.name) },
                    onClick = {
                        onPrioritySelected(priority)
                        expanded = true
                    }
                )
            }
        }
    }
}


@Composable
fun ListItem(item: TodoTask, modifier: Modifier = Modifier) {
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleLarge,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Text(
                    text = item.deadline.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Priority: ${item.priority}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = if (item.isDone) "Completed" else "Not Completed",
                    color = if (item.isDone) Color(0xFF4CAF50) else MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { onCompleteClicked(item) },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(if (item.isDone) "Not Completed" else "Completed")
            }
        }
    }
}

fun onCompleteClicked(item: TodoTask) {
    TODO("Not yet implemented")
}

fun todoTasks(): List<TodoTask> {
    return listOf(
        TodoTask("Clean dishes", LocalDate.of(2024, 4, 18), false, Priority.Low, 1),
        TodoTask("Read for 1 hour", LocalDate.of(2024, 5, 12), false, Priority.High, 2),
        TodoTask("Walk for 30 minutes", LocalDate.of(2024, 6, 28), true, Priority.Low, 3),
        TodoTask("Cooking", LocalDate.of(2024, 8, 18), false, Priority.Medium, 4),
    )
}


object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            ListViewModel(
                repository = todoApplication().container.todoTaskRepository
            )
        }
        initializer {
            FormViewModel(repository = todoApplication().container.todoTaskRepository)
        }
    }
}

fun CreationExtras.todoApplication(): TodoApplication {
    val app = this[APPLICATION_KEY]
    return app as TodoApplication
}



@Composable
fun TodoTaskInputBody(
    todoUiState: TodoTaskUiState,
    onItemValueChange: (TodoTaskForm) -> Unit,
    modifier: Modifier = Modifier,
    onSaveClick: () -> Unit = {}
) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TodoTaskInputForm(
            item = todoUiState.todoTask,
            onValueChange = onItemValueChange,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun TodoTaskInputForm(
    item: TodoTaskForm,
    onSave: (TodoTask) -> Unit = {},
    onValueChange: (TodoTaskForm) -> Unit,
    modifier: Modifier
) {
    Column(modifier = modifier) {
        TextField(
            value = item.title,
            onValueChange = { onValueChange(item.copy(title = it)) },
            label = { Text("Tytuł zadania") },
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Zadanie wykonane: ")
            Switch(
                checked = item.isDone,
                onCheckedChange = { onValueChange(item.copy(isDone = it)) }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        PriorityDropdownMenu(selectedPriority = Priority.valueOf(item.priority)) { newPriority ->
            onValueChange(item.copy(priority = newPriority.name))
        }
        Spacer(modifier = Modifier.height(16.dp))
        DateField(selectedDate = LocalDateConverter.fromMillis(item.deadline)) { newDate ->
            onValueChange(item.copy(deadline = LocalDateConverter.toMillis(newDate)))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                val newTask = item.toTodoTask()

                onSave(newTask)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Zapisz zadanie")
        }
    }
}

@Composable
fun DateField(selectedDate: LocalDate, onDateChange: (LocalDate) -> Unit) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        val year = selectedDate.year
        val month = selectedDate.monthValue - 1
        val day = selectedDate.dayOfMonth

        DatePickerDialog(
            context, { _, newYear, newMonth, newDay ->
                onDateChange(LocalDate.of(newYear, newMonth + 1, newDay))
                showDialog = false
            }, year, month, day
        ).show()
        showDialog = false
    }

    Button(
        onClick = { showDialog = true },
        content = {
            Text("Select Date: ${selectedDate.format(DateTimeFormatter.ISO_LOCAL_DATE)}")
        }
    )
}

fun TodoTask.toTodoTaskUiState(isValid: Boolean = false): TodoTaskUiState = TodoTaskUiState(
    todoTask = this.toTodoTaskForm(),
    isValid = isValid
)

fun TodoTaskForm.toTodoTask(): TodoTask = TodoTask(
    id = id,
    title = title,
    deadline = LocalDateConverter.fromMillis(deadline),
    isDone = isDone,
    priority = Priority.valueOf(priority)
)

fun TodoTask.toTodoTaskForm(): TodoTaskForm = TodoTaskForm(
    id = id,
    title = title,
    deadline = LocalDateConverter.toMillis(deadline),
    isDone = isDone,
    priority = priority.name
)

enum class Priority {
    High, Medium, Low
}