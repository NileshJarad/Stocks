package com.android.stocks

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("amt")
fun TextView.value(amt: String) {
    text = " \u20B9 $amt"
}


@BindingAdapter("viewVisibility")
fun View.viewVisibility(visible: Boolean) {
    visibility = if (visible) {
        View.VISIBLE
    } else {
        View.GONE
    }
}