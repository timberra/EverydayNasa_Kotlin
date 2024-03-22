package com.ligagriezne.nasaeveryday

import android.view.View

fun View.setVisibleOrGone(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}