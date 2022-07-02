package br.com.rorizinfo.cardFidelize.ui.util

import android.content.Context.INPUT_METHOD_SERVICE
import android.text.Editable
import android.text.TextWatcher
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT
import android.widget.EditText
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

fun unmask(s: String): String {
    return s.replace("-", "").replace("/", "").replace(".", "")
}

fun EditText.mask(ediTxt: EditText): TextWatcher {
    var isUpdating: Boolean = false
    var mask = ""
    var old = ""
    return object : TextWatcher {
        override fun afterTextChanged(s: Editable) {
        }

        override fun beforeTextChanged(
            s: CharSequence,
            start: Int,
            count: Int,
            after: Int
        ) {
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            val str = unmask(s.toString())
            var mascara = ""
            when (str.length) {
                in 0..11 -> mask = "###.###.###-##"
                else -> mask = "##.###.###/####-##"
            }
            if (isUpdating) {
                old = str
                isUpdating = false
                return
            }
            var i = 0
            for (m in mask.toCharArray()) {
                if (m != '#' && str.length > old.length) {
                    mascara += m
                    continue
                }
                try {
                    mascara += str[i]
                } catch (e: Exception) {
                    break
                }
                i++
            }
            isUpdating = true
            ediTxt.setText(mascara)
            ediTxt.setSelection(mascara.length)
        }
    }
}
