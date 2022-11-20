package com.android.stocks

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("amt")
fun TextView.value(amt: String) {
    text = " \u20B9 $amt"
}