package me.mocha.economy.exception

import java.lang.RuntimeException

open class EconomyException : RuntimeException {
    constructor() : super()
    constructor(message: String) : super(message)
}