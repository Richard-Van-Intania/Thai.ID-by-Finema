package co.finema.thaidotidbyfinema.databases.signatureimage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SignatureImage::class], version = 1, exportSchema = false)
abstract class SignatureImageDatabase : RoomDatabase() {
  abstract fun signatureImageDao(): SignatureImageDao

  companion object {
    @Volatile private var Instance: SignatureImageDatabase? = null

    fun getDatabase(context: Context): SignatureImageDatabase {
      return Instance
          ?: synchronized(this) {
            Room.databaseBuilder(
                    context, SignatureImageDatabase::class.java, "signature_image_database")
                .build()
                .also { Instance = it }
          }
    }
  }
}
