package br.com.rorizinfo.cardFidelize.domain.usecase

import android.content.Context
import br.com.rorizinfo.cardFidelize.R
import java.lang.Exception

const val LENGTH_PASSWORD = 6
class ValidatePasswordUseCase(private val context: Context) {

    fun validatePassword(password: String): Result<Any?> {
        return when {
            password.isEmpty() -> {
                Result.failure<String>(Exception(context.getString(R.string.empty_password_error)))
            }
            password.length < LENGTH_PASSWORD -> {
                Result.failure<Any>(Exception(context.getString(R.string.length_password_error)))
            }
            else -> Result.success(null)
        }
    }
}