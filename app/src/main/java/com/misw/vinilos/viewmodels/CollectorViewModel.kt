package com.misw.vinilos.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.misw.vinilos.data.remote.models.Collector
import com.misw.vinilos.data.repository.CollectorRepository
import com.skydoves.sandwich.getOrThrow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectorsViewModel @Inject constructor(
    private val repository: CollectorRepository
) : ViewModel() {

    val collectors = mutableStateOf<List<Collector>>(emptyList())
    val isLoading = mutableStateOf(false)
    val hasError = mutableStateOf(false)

    init {
        fetchCollectors()
    }

    private fun fetchCollectors() {
        viewModelScope.launch {
            try {
                isLoading.value = true
                collectors.value = repository.getCollectors().getOrThrow()
                hasError.value = false
            } catch (e: Exception) {
                hasError.value = true
            } finally {
                isLoading.value = false
            }
        }
    }
}