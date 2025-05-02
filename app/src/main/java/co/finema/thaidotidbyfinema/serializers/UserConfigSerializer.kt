package co.finema.thaidotidbyfinema.serializers

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import co.finema.thaidotidbyfinema.UserConfig
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object UserConfigSerializer : Serializer<UserConfig> {
  override val defaultValue: UserConfig = UserConfig.getDefaultInstance()

  override suspend fun readFrom(input: InputStream): UserConfig {
    try {
      return UserConfig.parseFrom(input)
    } catch (exception: InvalidProtocolBufferException) {
      throw CorruptionException("Cannot read proto.", exception)
    }
  }

  override suspend fun writeTo(t: UserConfig, output: OutputStream) = t.writeTo(output)
}
