package br.com.rorizinfo.cardFidelize.domain.usecase

import android.content.Context
import br.com.rorizinfo.cardFidelize.R
import java.lang.Exception

class ValidateNameCompanyUseCase(private val context: Context) {

    operator fun invoke(name: String): Boolean {
        return name.length >= MIN_CHARACTER_NAME
    }

    private companion object {
        private const val MIN_CHARACTER_NAME = 5
    }
}