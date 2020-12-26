package me.mocha.economy.event.money

import me.mocha.economy.event.EconomyEvent
import java.util.*

class MoneyChangedEvent(val playerId: UUID, val money: Int) : EconomyEvent() {
}