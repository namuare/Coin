package me.mocha.forgemod

import net.minecraft.item.Item
import net.minecraft.item.ItemGroup

abstract class CoinBase : Item(Properties().group(ItemGroup.MATERIALS))

class CoinBronze : CoinBase() {
    companion object {
        const val itemName = "coin_bronze"
    }
}

class CoinSilver : CoinBase() {
    companion object {
        const val itemName = "coin_silver"
    }
}

class CoinGold : CoinBase() {
    companion object {
        const val itemName = "coin_gold"
    }
}