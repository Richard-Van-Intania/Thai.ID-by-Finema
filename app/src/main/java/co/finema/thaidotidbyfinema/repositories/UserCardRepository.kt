package co.finema.thaidotidbyfinema.repositories

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import co.finema.thaidotidbyfinema.UserCard
import co.finema.thaidotidbyfinema.serializers.UserCardSerializer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.userCardStore: DataStore<UserCard> by
dataStore(fileName = "user_card.proto", serializer = UserCardSerializer)

class UserCardRepository(private val context: Context) {
    val userCardFlow: Flow<UserCard> = context.userCardStore.data

    val idString: Flow<String> = userCardFlow.map { prefs -> prefs.idString }
    val thaiPrefix: Flow<String> = userCardFlow.map { prefs -> prefs.thaiPrefix }
    val thaiName: Flow<String> = userCardFlow.map { prefs -> prefs.thaiName }
    val thaiMiddleName: Flow<String> = userCardFlow.map { prefs -> prefs.thaiMiddleName }
    val thaiSurname: Flow<String> = userCardFlow.map { prefs -> prefs.thaiSurname }
    val engPrefix: Flow<String> = userCardFlow.map { prefs -> prefs.engPrefix }
    val engName: Flow<String> = userCardFlow.map { prefs -> prefs.engName }
    val engMiddleName: Flow<String> = userCardFlow.map { prefs -> prefs.engMiddleName }
    val engSurname: Flow<String> = userCardFlow.map { prefs -> prefs.engSurname }
    val birthDate: Flow<String> = userCardFlow.map { prefs -> prefs.birthDate }

    suspend fun updateIdString(idString: String) {
        context.userCardStore.updateData { prefs -> prefs.toBuilder().setIdString(idString).build() }
    }

    suspend fun updateThaiPrefix(thaiPrefix: String) {
        context.userCardStore.updateData { prefs ->
            prefs.toBuilder().setThaiPrefix(thaiPrefix).build()
        }
    }

    suspend fun updateThaiName(thaiName: String) {
        context.userCardStore.updateData { prefs -> prefs.toBuilder().setThaiName(thaiName).build() }
    }

    suspend fun updateThaiMiddleName(thaiMiddleName: String) {
        context.userCardStore.updateData { prefs ->
            prefs.toBuilder().setThaiMiddleName(thaiMiddleName).build()
        }
    }

    suspend fun updateThaiSurname(thaiSurname: String) {
        context.userCardStore.updateData { prefs ->
            prefs.toBuilder().setThaiSurname(thaiSurname).build()
        }
    }

    suspend fun updateEngPrefix(engPrefix: String) {
        context.userCardStore.updateData { prefs -> prefs.toBuilder().setEngPrefix(engPrefix).build() }
    }

    suspend fun updateEngName(engName: String) {
        context.userCardStore.updateData { prefs -> prefs.toBuilder().setEngName(engName).build() }
    }

    suspend fun updateEngMiddleName(engMiddleName: String) {
        context.userCardStore.updateData { prefs ->
            prefs.toBuilder().setEngMiddleName(engMiddleName).build()
        }
    }

    suspend fun updateEngSurname(engSurname: String) {
        context.userCardStore.updateData { prefs ->
            prefs.toBuilder().setEngSurname(engSurname).build()
        }
    }

    suspend fun updateBirthDate(birthDate: String) {
        context.userCardStore.updateData { prefs -> prefs.toBuilder().setBirthDate(birthDate).build() }
    }
}
