package com.example.simplestore.redux.store

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class Store<T>(initialState: T) {

    private val _stateFlow = MutableStateFlow(initialState)
    val stateFlow = _stateFlow.asStateFlow()

    private val mutex = Mutex()

    suspend fun update(updateBlock: (T) -> T) = mutex.withLock {

        val newState = updateBlock(_stateFlow.value)
        _stateFlow.value = newState

    }

    suspend fun read(readBlock: (T) -> Unit) = mutex.withLock {
        readBlock(_stateFlow.value)
    }

}