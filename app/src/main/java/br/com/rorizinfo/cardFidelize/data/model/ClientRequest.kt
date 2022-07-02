package br.com.rorizinfo.cardFidelize.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class ClientRequest(
    val userRequest: RegisterUserRequest,
    val name: String,
) : Parcelable