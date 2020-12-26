package me.mocha.economy.exception

import java.lang.RuntimeException

open class EconomyException(message: String) : RuntimeException(message) {
    open val messagePath: String = "errors.unknown"
}