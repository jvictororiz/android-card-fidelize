package br.com.rorizinfo.cardFidelize.ui.util

import android.app.Activity
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.text.Selection.setSelection
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT
import android.widget.EditText
import android.widget.TextView
import androidx.core.text.HtmlCompat
import com.google.android.material.snackbar.Snackbar


fun ViewGroup.showMessage(message: String, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, message, duration).show()
}

fun TextView.setTextHtml(text: String) {
    setText(HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY))
}

fun EditText.showKeyBoard() {
    if (requestFocus()) {
        (context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager)
            .showSoftInput(this, SHOW_IMPLICIT)
        setSelection(text.length)
    }
}

fun ViewGroup.showKeyBoardView(editText: EditText) {
    (context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager)
        .showSoftInput(editText, SHOW_IMPLICIT)
    editText.setSelection(editText.text.length)
}