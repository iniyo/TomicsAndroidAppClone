package com.example.tomicsandroidappclone.domain.model

import java.lang.Exception

sealed class RepoSearchResult {
    data class Success(val data: List<ToonResponse>) : RepoSearchResult()
    data class Error(val error: Exception) : RepoSearchResult()
}
