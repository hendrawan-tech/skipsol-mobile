package com.example.skripsol.state

class MyState {
    private var userData: String = ""

    var user: String
        get() = userData
        set(value) {
            userData = value
        }
}