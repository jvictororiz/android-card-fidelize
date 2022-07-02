package br.com.rorizinfo.cardFidelize.domain.usecase

const val LENGTH_CNPJ = 18

class ValidateCnpjCompanyUseCase {
    
    private val WEIGHT_CNPJ = intArrayOf(6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2)
    
    operator fun invoke(cnpj: String): Boolean {
        return cnpj.length == LENGTH_CNPJ && isValidCNPJ(
            cnpj
                .replace(".", "")
                .replace("-", "")
                .replace("/", "")
        )
    }
    
    private fun isValidCNPJ(cnpj: String?): Boolean {
        if (cnpj == null || cnpj.length != 14) return false
        val digito1: Int = calculateDigit(cnpj.substring(0, 12), WEIGHT_CNPJ)
        val digito2: Int = calculateDigit(cnpj.substring(0, 12) + digito1, WEIGHT_CNPJ)
        return cnpj == cnpj.substring(0, 12) + digito1.toString() + digito2.toString()
    }
    
    private fun calculateDigit(str: String, peso: IntArray): Int {
        var soma = 0
        var indice = str.length - 1
        var digito: Int
        while (indice >= 0) {
            digito = str.substring(indice, indice + 1).toInt()
            soma += digito * peso[peso.size - str.length + indice]
            indice--
        }
        soma = 11 - soma % 11
        return if (soma > 9) 0 else soma
    }
}