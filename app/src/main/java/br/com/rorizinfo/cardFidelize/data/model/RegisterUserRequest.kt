package br.com.rorizinfo.cardFidelize.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RegisterUserRequest(
    var id: String = "",
    val name: String = "",
    val email: String = "",
    val password: String = "",
): Parcelable