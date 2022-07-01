package br.com.rorizinfo.cardFidelize.ui.util

import androidx.lifecycle.MutableLiveData

open class MultipleLiveState<T> : MutableLiveData<T>() {
    private val listEvents = mutableListOf<T>()
    private var isActive: Boolean = false

    override fun onActive() {
        isActive = true
        while (listEvents.isNotEmpty()) {
            setValue(listEvents.first())
            listEvents.removeFirst()
        }
    }

    override fun onInactive() {
        isActive = false
    }

    override fun setValue(value: T) {
        if (isActive) {
            super.setValue(value)
        } else {
            listEvents.add(value)
        }
    }
}