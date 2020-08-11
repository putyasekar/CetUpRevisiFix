package com.sitiaisyah.idn.cetupapp.model

class Token {
    private var token: String = ""

    constructor() {}

    constructor(token: String) {
        this.token = token
    }

    // getter and setter

    fun getToken(): String? {
        return token
    }

    fun setToken(token: String?) {
        this.token = token!!
    }
}