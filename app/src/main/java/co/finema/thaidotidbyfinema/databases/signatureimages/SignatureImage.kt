package co.finema.thaidotidbyfinema.databases.signatureimages

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "signature_image")
data class SignatureImage(
 @PrimaryKey(autoGenerate = true) val id: Int = 0,
 @ColumnInfo(name = "file_name") val fileName: String,
 @ColumnInfo(name = "date_created") val dateCreated: String,
 @ColumnInfo(name = "date_last_used") val dateLastUsed: String,
)
