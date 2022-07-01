package br.com.rorizinfo.cardFidelize.domain.usecase

import android.util.Patterns

class ValidateEmailUseCase {
    
    operator fun invoke(email: String): Boolean {
        return email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}