@file:Suppress("DEPRECATION")

package com.example.covid19statsapp.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

/**
 * format date
 * */
@SuppressLint("SimpleDateFormat")
fun convertMillisToFormattedDate(time: Long): String {
    val simpleDateFormat: SimpleDateFormat? = SimpleDateFormat("dd MMM,yyyy")
    val dateFormatToViewable = Date(time)
    return simpleDateFormat!!.format(dateFormatToViewable)
}


/**
 * Hide keyboard
 * */
fun hideSoftKeyboard(activity: Activity) {
    if (activity.currentFocus == null) {
        return
    }
    val inputMethodManager =
        activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
}

/**
 * Toast message
 * */
fun Context.toast(message: String) {
    Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).apply {
        show()
    }
}

/**
 * SnackBar
 * */
fun View.snackbar(msg: String) {
    Snackbar.make(this, msg, Snackbar.LENGTH_LONG).also { snackbar ->
        snackbar.setAction("ok") {
            snackbar.dismiss()
        }
    }.show()
}

/**
 * check if string is empty
 * */
fun isEmpty(string: String): Boolean {
    return string == ""
}

/**
 * Check for internet connectivity
 * */

fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetworkInfo = connectivityManager.activeNetworkInfo
    return activeNetworkInfo != null && activeNetworkInfo.isConnected
}