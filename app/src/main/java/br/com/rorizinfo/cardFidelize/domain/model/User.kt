package br.com.rorizinfo.cardFidelize.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var id: String = "",
    var email: String = "",
    var password: String = "",
) : Parcelable