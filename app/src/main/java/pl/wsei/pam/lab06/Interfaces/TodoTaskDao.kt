package pl.wsei.pam.lab06.Interfaces

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import pl.wsei.pam.lab06.Data.TodoTaskEntity

@Dao
interface TodoTaskDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(vararg tasks: TodoTaskEntity)

    @Delete
    suspend fun removeById(item: TodoTaskEntity)

    @Query("Select * from tasks ORDER BY deadline DESC")
    fun findAll(): Flow<List<TodoTaskEntity>>

    @Query("Select * from tasks where id == :id")
    fun find(id: Int): Flow<TodoTaskEntity>

    @Update
    suspend fun update(task: TodoTaskEntity)
}