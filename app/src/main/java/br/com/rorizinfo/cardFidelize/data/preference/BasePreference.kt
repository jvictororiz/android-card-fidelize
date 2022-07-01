package br.com.rorizinfo.cardFidelize.data.preference

sealed class BasePreference(val key: String) {
    object PasswordUser : BasePreference("PasswordUser")
    object EmailUser : BasePreference("LoginUser")
}