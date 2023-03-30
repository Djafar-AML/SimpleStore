package com.example.simplestore.extensions

fun String.capitalizeFirstChar(): String {

    return this.replaceFirstChar {

        if (it.isLowerCase()) {
            it.titlecase()
        } else {
            it.toString()
        }
    }

}