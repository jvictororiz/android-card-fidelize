package br.com.rorizinfo.cardFidelize.ui.util

import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.HtmlCompat
import com.google.android.material.snackbar.Snackbar


fun ViewGroup.showMessage(message: String, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, message, duration).apply {
        findViewById<TextView>(com.google.android.material.R.id.snackbar_text).apply {
            setTextMaxLines(4)
        }
    }.show()
}

fun TextView.setTextHtml(text: String) {
    setText(HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY))
}