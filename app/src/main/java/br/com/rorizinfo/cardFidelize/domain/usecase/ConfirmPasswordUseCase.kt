package br.com.rorizinfo.cardFidelize.domain.usecase

import android.content.Context
import br.com.rorizinfo.cardFidelize.R

class ConfirmPasswordUseCase(private val context: Context) {

    fun confirmPassword(currentPassword: String, password: String): Result<Nothing?> {
        return if (currentPassword == password) {
            Result.success(null)
        } else {
            Result.failure(Exception(context.getString(R.string.message_password_not_equals)))
        }
    }
}