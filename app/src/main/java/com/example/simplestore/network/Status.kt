package com.example.simplestore.network

sealed class Status {
    object Success : Status()
    object Failure : Status()
}