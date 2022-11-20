package com.android.stocks.domain


sealed class OutCome<out R> {

    data class Success<out T>(val data: T) : OutCome<T>()
    data class Failed(val message: String) : OutCome<Nothing>()
    object Loading : OutCome<Nothing>()
    object NetworkError : OutCome<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Failed -> "Error[exception=$message]"
            is NetworkError -> "Network Error"
            Loading -> "Loading"
        }
    }
}