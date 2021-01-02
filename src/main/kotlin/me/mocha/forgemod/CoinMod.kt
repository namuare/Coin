package me.mocha.forgemod

import net.minecraft.data.DataGenerator
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraftforge.client.model.generators.ItemModelProvider
import net.minecraftforge.client.model.generators.ModelFile
import net.minecraftforge.common.data.ExistingFileHelper
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent
import net.minecraftforge.registries.ForgeRegistries
import org.apache.logging.log4j.LogManager
import thedarkcolour.kotlinforforge.forge.KDeferredRegister
import thedarkcolour.kotlinforforge.forge.MOD_BUS

@Mod(CoinMod.ID)
object CoinMod {
    const val ID = "coin"

    private val logger = LogManager.getLogger()

    private val REGISTRY = KDeferredRegister(ForgeRegistries.ITEMS, ID)
    val COIN_BRONZE = REGISTRY.registerObject(CoinBronze.ITEM_NAME) { CoinBronze() }
    val COIN_SILVER = REGISTRY.registerObject(CoinSilver.ITEM_NAME) { CoinSilver() }
    val COIN_GOLD = REGISTRY.registerObject(CoinGold.ITEM_NAME) { CoinGold() }

    init {
        logger.info("coin mod loading...")
        REGISTRY.register(MOD_BUS)
    }

}

@Mod.EventBusSubscriber(modid = CoinMod.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
object DataGenerators {
    @SubscribeEvent
    fun onGatherData(event: GatherDataEvent) {
        event.generator.addProvider(CoinModelProvider(event.generator, event.existingFileHelper))
    }
}

class CoinModelProvider(generator: DataGenerator, helper: ExistingFileHelper) : ItemModelProvider(generator, CoinMod.ID, helper) {
    override fun registerModels() {
        val itemGenerated = getExistingFile(mcLoc("item/generated"))
        builder(itemGenerated, CoinBronze.ITEM_NAME)
        builder(itemGenerated, CoinSilver.ITEM_NAME)
        builder(itemGenerated, CoinGold.ITEM_NAME)
    }

    private fun builder(itemGenerated: ModelFile, name: String) = getBuilder(name).parent(itemGenerated).texture("layer0", "item/$name")
}

abstract class CoinBase : Item(Properties().group(ItemGroup.MATERIALS))

class CoinBronze : CoinBase() {
    companion object {
        const val ITEM_NAME = "coin_bronze"
    }
}

class CoinSilver : CoinBase() {
    companion object {
        const val ITEM_NAME = "coin_silver"
    }
}

class CoinGold : CoinBase() {
    companion object {
        const val ITEM_NAME = "coin_gold"
    }
}
