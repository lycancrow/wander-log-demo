package com.example.wanderlogdemo.viewModel

open class Event<out T>(private val content: T) {

    private var hasBeenHandled = false

    /** Return the content and prevent its use again. **/
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /** Return the content, even if it's already been handled. */
    fun peekContent(): T = content
}