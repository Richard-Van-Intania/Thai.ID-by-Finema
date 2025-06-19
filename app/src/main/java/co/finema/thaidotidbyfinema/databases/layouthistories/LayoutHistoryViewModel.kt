package co.finema.thaidotidbyfinema.databases.layouthistories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LayoutHistoryViewModel(private val layoutHistoryDao: LayoutHistoryDao) : ViewModel() {
    private val _layoutHistory = MutableStateFlow<List<LayoutHistory>>(emptyList())
    val layoutHistory: StateFlow<List<LayoutHistory>>
        get() = _layoutHistory

    init {
        loadLayoutHistory()
    }

    fun loadLayoutHistory() {
        viewModelScope.launch { _layoutHistory.value = layoutHistoryDao.getAllLayoutHistory() }
    }

    fun addLayoutHistory(
        documentLayout: String,
        dateCreated: String,
        dateLastUsed: String,
        layoutRawImagefileName0: String?,
        layoutRawImagefileName1: String?,
        layoutRawImagefileName2: String?,
        userDefinedName: String?,
    ) {
        viewModelScope.launch {
            layoutHistoryDao.insertLayoutHistory(
                LayoutHistory(
                    documentLayout = documentLayout,
                    dateCreated = dateCreated,
                    dateLastUsed = dateLastUsed,
                    layoutRawImagefileName0 = layoutRawImagefileName0,
                    layoutRawImagefileName1 = layoutRawImagefileName1,
                    layoutRawImagefileName2 = layoutRawImagefileName2,
                    userDefinedName = userDefinedName,
                )
            )
            loadLayoutHistory()
        }
    }

    fun newDateLastUsed(id: Int, dateLastUsed: String) {
        viewModelScope.launch {
            layoutHistoryDao.updateLastUsed(id = id, dateLastUsed = dateLastUsed)
            loadLayoutHistory()
        }
    }
}
