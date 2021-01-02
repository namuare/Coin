package me.mocha.forgemod;

import me.mocha.forgemod.item.CoinBronze;
import me.mocha.forgemod.item.CoinGold;
import me.mocha.forgemod.item.CoinSilver;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Coin.MOD_ID)
public class Coin {
    public static final String MOD_ID = "coin";

    private static final Logger LOGGER = LogManager.getLogger();

    DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);

    RegistryObject<Item> COIN_BRONZE = ITEMS.register(CoinBronze.ITEM_NAME, CoinBronze::new);
    RegistryObject<Item> COIN_SILVER = ITEMS.register(CoinSilver.ITEM_NAME, CoinSilver::new);
    RegistryObject<Item> COIN_GOLD = ITEMS.register(CoinGold.ITEM_NAME, CoinGold::new);

    public Coin() {
        registerItems();
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void registerItems() {
        LOGGER.debug("register items");
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ITEMS.register(modEventBus);
    }

}
