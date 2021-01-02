package me.mocha.forgemod

import net.minecraftforge.fml.common.Mod
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod.EventBusSubscriber
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import org.apache.logging.log4j.LogManager

const val modid = "coin"

@Mod(modid)
class CoinMod {

    companion object {
        val logger = LogManager.getLogger()
        val items = DeferredRegister.create(ForgeRegistries.ITEMS, modid)
        val coinBronze = items.register(CoinBronze.itemName) { CoinBronze() }
        val coinSilver = items.register(CoinSilver.itemName) { CoinSilver() }
        val coinGold = items.register(CoinGold.itemName) { CoinGold() }
    }

    init {
        registerItems()
        MinecraftForge.EVENT_BUS.register(this)
    }

    private fun registerItems() {
        logger.info("register items")
        val modEventBus = FMLJavaModLoadingContext.get().modEventBus
        items.register(modEventBus)
    }

    @EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
    object EventHandler {
        @JvmStatic
        @SubscribeEvent
        fun onGatherData(event: GatherDataEvent) {
            event.generator.addProvider(CoinModelProvider(event.generator, event.existingFileHelper))
        }
    }

}