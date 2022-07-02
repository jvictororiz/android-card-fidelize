package br.com.rorizinfo.cardFidelize.domain.usecase

import android.content.Context
import br.com.rorizinfo.cardFidelize.R

class ConfirmPasswordUseCase(private val context: Context) {

    fun confirmPassword(currentPassword: String, password: String): Result<Any?> {
        return when {
            password.isEmpty() -> Result.failure<String>(java.lang.Exception(context.getString(R.string.empty_confirm_password_error)))
            currentPassword == password -> Result.success(null)
            else -> Result.failure(Exception(context.getString(R.string.message_password_not_equals)))
        }
    }
}