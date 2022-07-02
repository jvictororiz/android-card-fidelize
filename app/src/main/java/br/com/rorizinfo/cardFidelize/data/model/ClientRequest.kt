package br.com.rorizinfo.cardFidelize.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class ClientRequest(
    val idClient: String,
    val emailClient: String,
    val name: String,
) : Parcelable