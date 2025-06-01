package co.finema.thaidotidbyfinema.databases.signatureimages

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SignatureImageDao {
    @Query("SELECT * from signature_image ORDER BY id ASC")
    suspend fun getAllSignatureImage(): List<SignatureImage>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSignatureImage(signatureImage: SignatureImage)

    @Query("UPDATE signature_image SET date_last_used = :dateLastUsed WHERE id = :id")
    suspend fun updateLastUsed(id: Int, dateLastUsed: String)

    @Query("DELETE from signature_image WHERE id = :id") suspend fun deleteSignatureImage(id: Int)
}
