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
class CollectorDetailsViewModel @Inject constructor(
    private val repository: CollectorRepository
) : ViewModel() {

    val collectorDetails = mutableStateOf<Collector?>(null)
    val isLoading = mutableStateOf(false)
    val hasError = mutableStateOf(false)

    fun fetchCollectorDetail(collectorId: Int) {
        viewModelScope.launch {
            try {
                isLoading.value = true
                collectorDetails.value = repository.getCollectorDetail(collectorId).getOrThrow()
                hasError.value = false
            } catch (e: Exception) {
                hasError.value = true
            } finally {
                isLoading.value = false
            }
        }
    }
}