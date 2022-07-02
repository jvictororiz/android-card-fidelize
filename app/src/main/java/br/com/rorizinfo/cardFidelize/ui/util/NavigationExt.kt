package br.com.rorizinfo.cardFidelize.ui.util

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import br.com.rorizinfo.cardFidelize.R

fun NavController.navigateWithAnim(id: Int, bundle: Bundle? = null) {
    val navOptions = NavOptions
        .Builder()
        .setEnterAnim(R.anim.enter_from_right)
        .setExitAnim(R.anim.exit_to_left)
        .setPopEnterAnim(R.anim.enter_from_left)
        .setPopExitAnim(R.anim.exit_to_right)
        .build()
    navigate(id, bundle, navOptions)
}

fun NavController.navigateWithAnim(id: Int, bundle: Bundle? = null, popUpTo: Int, inclusive: Boolean = false) {
    val navOptions = NavOptions
        .Builder()
        .setPopUpTo(popUpTo, inclusive)
        .setEnterAnim(R.anim.enter_from_right)
        .setExitAnim(R.anim.exit_to_left)
        .setPopEnterAnim(R.anim.enter_from_left)
        .setPopExitAnim(R.anim.exit_to_right)
        .build()
    navigate(id, bundle, navOptions)
}
