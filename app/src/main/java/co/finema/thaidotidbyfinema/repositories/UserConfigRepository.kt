package co.finema.thaidotidbyfinema.repositories

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import co.finema.thaidotidbyfinema.DATA_STORE_FILE_NAME
import co.finema.thaidotidbyfinema.UserConfig
import co.finema.thaidotidbyfinema.ViewLayout
import co.finema.thaidotidbyfinema.serializers.UserConfigSerializer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.userConfigStore: DataStore<UserConfig> by
    dataStore(fileName = DATA_STORE_FILE_NAME, serializer = UserConfigSerializer)

class UserConfigRepository(private val context: Context) {
  val userConfigFlow: Flow<UserConfig> = context.userConfigStore.data

  val passcode: Flow<String> = userConfigFlow.map { prefs -> prefs.passcode }
  val salt: Flow<String> = userConfigFlow.map { prefs -> prefs.salt }
  val isSelectedNeverShowAgain: Flow<Boolean> =
      userConfigFlow.map { prefs -> prefs.isSelectedNeverShowAgain }
  val hideInstruction: Flow<Boolean> = userConfigFlow.map { prefs -> prefs.hideInstruction }
  val exportCount: Flow<Int> = userConfigFlow.map { prefs -> prefs.exportCount }
  val locale: Flow<String> = userConfigFlow.map { prefs -> prefs.locale }
  val useBiometric: Flow<Boolean> = userConfigFlow.map { prefs -> prefs.useBiometric }
  val homeViewLayout: Flow<ViewLayout> = userConfigFlow.map { prefs -> prefs.homeViewLayout }
  val historyViewLayout: Flow<ViewLayout> = userConfigFlow.map { prefs -> prefs.historyViewLayout }
  val isAcceptedAgreements: Flow<Boolean> =
      userConfigFlow.map { prefs -> prefs.isAcceptedAgreements }

  suspend fun updatePasscode(passcode: String) {
    context.userConfigStore.updateData { prefs -> prefs.toBuilder().setPasscode(passcode).build() }
  }

  suspend fun updateSalt(salt: String) {
    context.userConfigStore.updateData { prefs -> prefs.toBuilder().setSalt(salt).build() }
  }

  suspend fun updateIsSelectedNeverShowAgain(isSelectedNeverShowAgain: Boolean) {
    context.userConfigStore.updateData { prefs ->
      prefs.toBuilder().setIsSelectedNeverShowAgain(isSelectedNeverShowAgain).build()
    }
  }

  suspend fun updateHideInstruction(hideInstruction: Boolean) {
    context.userConfigStore.updateData { prefs ->
      prefs.toBuilder().setHideInstruction(hideInstruction).build()
    }
  }

  suspend fun updateExportCount(exportCount: Int) {
    context.userConfigStore.updateData { prefs ->
      prefs.toBuilder().setExportCount(exportCount).build()
    }
  }

  suspend fun updateLocale(locale: String) {
    context.userConfigStore.updateData { prefs -> prefs.toBuilder().setLocale(locale).build() }
  }

  suspend fun updateUseBiometric(useBiometric: Boolean) {
    context.userConfigStore.updateData { prefs ->
      prefs.toBuilder().setUseBiometric(useBiometric).build()
    }
  }

  suspend fun updateHomeViewLayout(homeViewLayout: ViewLayout) {
    context.userConfigStore.updateData { prefs ->
      prefs.toBuilder().setHomeViewLayout(homeViewLayout).build()
    }
  }

  suspend fun updateHistoryViewLayout(historyViewLayout: ViewLayout) {
    context.userConfigStore.updateData { prefs ->
      prefs.toBuilder().setHistoryViewLayout(historyViewLayout).build()
    }
  }

  suspend fun updateIsAcceptedAgreements(isAcceptedAgreements: Boolean) {
    context.userConfigStore.updateData { prefs ->
      prefs.toBuilder().setIsAcceptedAgreements(isAcceptedAgreements).build()
    }
  }
}
