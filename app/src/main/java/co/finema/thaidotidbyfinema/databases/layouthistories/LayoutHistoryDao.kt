package co.finema.thaidotidbyfinema.databases.layouthistories

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LayoutHistoryDao {
    @Query("SELECT * from layout_history ORDER BY id ASC")
    suspend fun getAllLayoutHistory(): List<LayoutHistory>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLayoutHistory(layoutHistory: LayoutHistory)

    @Query("UPDATE layout_history SET date_last_used = :dateLastUsed WHERE id = :id")
    suspend fun updateLastUsed(id: Int, dateLastUsed: String)

    @Query("UPDATE layout_history SET user_defined_name = :userDefinedName WHERE id = :id")
    suspend fun updateUserDefinedName(id: Int, userDefinedName: String)

    @Query("DELETE from layout_history WHERE id = :id") suspend fun deleteLayoutHistory(id: Int)
}
