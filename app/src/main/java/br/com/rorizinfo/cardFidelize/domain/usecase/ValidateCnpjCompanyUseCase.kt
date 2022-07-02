package br.com.rorizinfo.cardFidelize.domain.usecase

import android.content.Context
import br.com.rorizinfo.cardFidelize.R

const val LENGTH_CNPJ = 14

class ValidateCnpjCompanyUseCase(private val context: Context) {

    fun validateCnpj(cnpj: String) {
        if (cnpj.length == LENGTH_CNPJ) {
            if (checkCnpj(cnpj)) {
                Result.success(null)
            } else {
                Result.failure(Exception(context.getString(R.string.error_cnpj_lenght)))
            }
        }
    }

    fun checkCnpj(et: String): Boolean {
        var str = et.replace("-", "").replace("/", "").replace(".", "")
        var calc: Int
        var num = 5
        var sum = 0
        for (x in 0..11) {
            calc = str[x].toString().toInt() * num
            sum += calc
            --num
            if (num == 1) num = 9
        }
        var rest = sum % 11
        var test = 11 - rest
        if (test < 2) test = 0
        if (test != str[12].toString().toInt()) return false
        num = 6
        sum = 0
        for (x in 0..12) {
            calc = str[x].toString().toInt() * num
            sum += calc
            --num
            if (num == 1) num = 9
        }
        rest = sum % 11
        test = 11 - rest
        if (test < 2) test = 0
        if (test != str[13].toString().toInt()) return false
        return true
    }
}