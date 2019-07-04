package com.topmovies.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel(context: Application, coroutineContext: CoroutineContext) : AndroidViewModel(context) {
    private val viewModelJob by lazy { Job() }
    protected val viewModelScope by lazy { CoroutineScope(coroutineContext + viewModelJob) }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}