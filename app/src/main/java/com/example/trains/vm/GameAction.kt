package com.example.trains.vm

sealed interface GameAction {

    fun releaseFinger()

    fun commit()
}