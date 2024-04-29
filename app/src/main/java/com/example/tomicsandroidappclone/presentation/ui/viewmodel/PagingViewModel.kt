package com.example.tomicsandroidappclone.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.tomicsandroidappclone.data.repository.PagingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PagingViewModel @Inject constructor(
    private val repository: PagingRepository
) : ViewModel() {

    val pagingData = repository.getPagingData().cachedIn(viewModelScope)
}
