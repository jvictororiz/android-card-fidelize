package br.com.rorizinfo.cardFidelize.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Client(
    var idClient: String = "",
    var emailUser:String,
    var name: String = ""
) : Parcelable