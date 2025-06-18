package co.finema.thaidotidbyfinema.databases.layouthistories

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "layout_history")
data class LayoutHistory(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "document_layout") val documentLayout: String,
    @ColumnInfo(name = "date_created") val dateCreated: String,
    @ColumnInfo(name = "date_last_used") val dateLastUsed: String,
    @ColumnInfo(name = "layout_raw_imagefile_name0") val layoutRawImagefileName0: String?,
    @ColumnInfo(name = "layout_raw_imagefile_name1") val layoutRawImagefileName1: String?,
    @ColumnInfo(name = "layout_raw_imagefile_name2") val layoutRawImagefileName2: String?,
    @ColumnInfo(name = "user_defined_name") val userDefinedName: String?,
)
