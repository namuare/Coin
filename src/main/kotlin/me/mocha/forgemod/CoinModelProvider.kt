package me.mocha.forgemod

import net.minecraft.data.DataGenerator
import net.minecraftforge.client.model.generators.ItemModelProvider
import net.minecraftforge.client.model.generators.ModelFile
import net.minecraftforge.common.data.ExistingFileHelper

class CoinModelProvider(generator: DataGenerator, helper: ExistingFileHelper) : ItemModelProvider(generator, modid, helper) {
    override fun registerModels() {
        val itemGenerated = getExistingFile(mcLoc("item/generated"))
        builder(itemGenerated, CoinBronze.itemName)
        builder(itemGenerated, CoinSilver.itemName)
        builder(itemGenerated, CoinGold.itemName)
    }

    private fun builder(itemGenerated: ModelFile, name: String) = getBuilder(name).parent(itemGenerated).texture("layer0", "item/$name")
}