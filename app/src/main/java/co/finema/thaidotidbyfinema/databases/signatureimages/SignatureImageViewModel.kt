package co.finema.thaidotidbyfinema.databases.signatureimages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SignatureImageViewModel(private val signatureImageDao: SignatureImageDao) : ViewModel() {
    private val _signatureImage = MutableStateFlow<List<SignatureImage>>(emptyList())
    val signatureImage: StateFlow<List<SignatureImage>>
        get() = _signatureImage

    init {
        viewModelScope.launch { loadSignatureImages() }
    }

    suspend fun loadSignatureImages() {
        viewModelScope.launch { _signatureImage.value = signatureImageDao.getAllSignatureImage() }.join()
    }

    suspend fun addSignatureImage(fileName: String, dateCreated: String, dateLastUsed: String) {
        viewModelScope
         .launch {
             signatureImageDao.insertSignatureImage(SignatureImage(fileName = fileName, dateCreated = dateCreated, dateLastUsed = dateLastUsed))
             loadSignatureImages()
         }
         .join()
    }

    suspend fun newDateLastUsed(id: Int, dateLastUsed: String) {
        viewModelScope
         .launch {
             signatureImageDao.updateLastUsed(id = id, dateLastUsed = dateLastUsed)
             loadSignatureImages()
         }
         .join()
    }

    suspend fun removeSignatureImage(id: Int) {
        viewModelScope
         .launch {
             signatureImageDao.deleteSignatureImage(id = id)
             loadSignatureImages()
         }
         .join()
    }
}
