package br.com.rorizinfo.cardFidelize.ui.util

import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar


fun ViewGroup.showMessage(message: String, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, message, duration).show()
}