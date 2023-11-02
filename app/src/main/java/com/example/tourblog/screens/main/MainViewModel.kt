package com.example.tourblog.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tourblog.data.repositories.InformationRepository
import com.example.tourblog.utils.Constants.BASE_URL
import com.example.tourblog.utils.PageState
import com.example.tourblog.utils.getImage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val infoRep: InformationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUIState())
    val uiState: StateFlow<MainUIState> = _uiState

    init {
        viewModelScope.launch {
            _uiState.value = uiState.value.copy(
                pageState = PageState.LOAD
            )
            infoRep.getMain().let {
                _uiState.value = uiState.value.copy(
                    buttons = it.data.buttons
                        .map { button ->
                            Button(
                                title = button.title, type = button.color
                            )
                        })
                it.data.content
                    .onEach {
                        getBlogs(it.title, it.url.removePrefix(BASE_URL), it.template.obj)
                    }
            }
            _uiState.value = uiState.value.copy(
                pageState = PageState.READY
            )
        }
    }

    private suspend fun getBlogs(
        title: String,
        url: String,
        template: String
    ) {
        val list = uiState.value.categories.toMutableList()
        val blogList: MutableList<Blog> = mutableListOf()
        infoRep.getBlogs(url).data.map {
            blogList.add(
                Blog(
                    id = it.id,
                    title = it.title,
                    subtitle = it.subtitle,
                    getImage(it.image.sm),
                    price = it.price,
                    location = it.location,
                    countTourist = it.countTourist
                )
            )
        }
        list.add(
            Category(
                name = title,
                blogList = blogList,
                showedList = blogList.take(6),
                template = template
            )
        )
        _uiState.value = uiState.value.copy(
            categories = list,
        )
    }

//    fun updateShowed(name: String) {
//        val categories = _uiState.value.categories
//        _uiState.value.categories.find { it.name == name }?.let { category ->
//            categories.find { it.name == name }?.showedList = category.blogList
//        }
//        _uiState.value = uiState.value.copy(
//            categories = categories
//        )
//    }
}