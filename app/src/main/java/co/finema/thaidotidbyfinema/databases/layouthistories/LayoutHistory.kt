package co.finema.thaidotidbyfinema.databases.layouthistories

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "layout_history")
data class LayoutHistory(@PrimaryKey(autoGenerate = true) val id: Int = 0)
