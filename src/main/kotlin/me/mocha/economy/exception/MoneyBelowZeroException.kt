package me.mocha.economy.exception


class MoneyBelowZeroException : EconomyException("could not process money below zero") {
    override val messagePath: String = "errors.moneybelowzero"
}