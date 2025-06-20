package co.finema.thaidotidbyfinema.databases.layouthistories

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [LayoutHistory::class], version = 1, exportSchema = false)
abstract class LayoutHistoryDatabase : RoomDatabase() {
    abstract fun layoutHistoryDao(): LayoutHistoryDao

    companion object {
        @Volatile private var Instance: LayoutHistoryDatabase? = null

        fun getDatabase(context: Context): LayoutHistoryDatabase {
            return Instance ?: synchronized(this) { Room.databaseBuilder(context, LayoutHistoryDatabase::class.java, "layout_history_database").build().also { Instance = it } }
        }
    }
}
