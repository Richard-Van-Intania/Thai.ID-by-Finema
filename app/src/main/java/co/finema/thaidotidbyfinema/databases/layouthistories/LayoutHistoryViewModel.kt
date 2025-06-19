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
}
