package co.finema.thaidotidbyfinema.databases.exporthistories

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "export_history") data class ExportHistory(@PrimaryKey(autoGenerate = true) val id: Int = 0)
