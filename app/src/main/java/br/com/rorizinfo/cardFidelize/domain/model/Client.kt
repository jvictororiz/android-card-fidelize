package br.com.rorizinfo.cardFidelize.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Client(
    var user: User,
    var name: String = ""
) : Parcelable