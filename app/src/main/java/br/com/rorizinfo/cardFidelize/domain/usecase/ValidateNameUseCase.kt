package br.com.rorizinfo.cardFidelize.domain.usecase

class ValidateNameUseCase {
    
    operator fun invoke(name: String): Boolean {
        return name.length >= MIN_CHARACTER_NAME
    }
    
    private companion object {
        private const val MIN_CHARACTER_NAME = 3
    }
}