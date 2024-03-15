package com.example.tomicsandroidappclone.presentation.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class BaseViewModel :ViewModel(){
    private var _height = MutableLiveData<Int>()

    val height: LiveData<Int>
        get() = _height

    init {
        _height.value = 170
    }

    fun increase() {
        _height.value = _height.value?.plus(1)
    }


    /*// 생성자에서 초기 아이콘 설정하기
    fun MyViewModel() {
        searchIcon.set(ContextCompat.getDrawable(context, R.drawable.search_icon))
    }

    // 검색 아이콘 변경 메서드 (예: 버튼 클릭 이벤트)
    fun toggleSearch() {
        isSearchEnabled.set(!isSearchEnabled.get())
        if (isSearchEnabled.get()) {
            searchIcon.set(ContextCompat.getDrawable(context, R.drawable.search_icon))
        } else {
            searchIcon.set(ContextCompat.getDrawable(context, R.drawable.search_disabled_icon))
        }
    }*/
}