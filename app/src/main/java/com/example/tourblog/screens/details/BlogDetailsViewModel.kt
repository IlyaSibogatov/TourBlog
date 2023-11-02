package com.example.tourblog.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tourblog.data.repositories.InformationRepository
import com.example.tourblog.utils.PageState
import com.example.tourblog.utils.getImage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BlogDetailsViewModel @Inject constructor(
    private val infoRep: InformationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailsUIState())
    val uiState: StateFlow<DetailsUIState> = _uiState

    fun getInfo(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = uiState.value.copy(
                pageState = PageState.LOAD
            )
            delay(5000L)
            infoRep.getBlogDetails(id).let {
                _uiState.value = uiState.value.copy(
                    date = it.data.date,
                    title = it.data.title,
                    subtitle = it.data.subtitle,
                    content = it.data.content,
                    image = getImage(it.data.image.lg)
                )
            }
            _uiState.value = uiState.value.copy(
                pageState = PageState.READY
            )
        }
    }
}