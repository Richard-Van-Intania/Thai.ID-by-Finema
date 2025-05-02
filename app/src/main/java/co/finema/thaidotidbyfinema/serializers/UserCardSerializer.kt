package co.finema.thaidotidbyfinema.serializers

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import co.finema.thaidotidbyfinema.UserCard
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object UserCardSerializer : Serializer<UserCard> {
  override val defaultValue: UserCard = UserCard.getDefaultInstance()

  override suspend fun readFrom(input: InputStream): UserCard {
    try {
      return UserCard.parseFrom(input)
    } catch (exception: InvalidProtocolBufferException) {
      throw CorruptionException("Cannot read proto.", exception)
    }
  }

  override suspend fun writeTo(t: UserCard, output: OutputStream) = t.writeTo(output)
}
