package me.mocha.forgemod.data.client;

import me.mocha.forgemod.Coin;
import me.mocha.forgemod.item.CoinBronze;
import me.mocha.forgemod.item.CoinGold;
import me.mocha.forgemod.item.CoinSilver;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class CoinItemModelProvider extends ItemModelProvider {

    public CoinItemModelProvider(DataGenerator generator, ExistingFileHelper fileHelper) {
        super(generator, Coin.MOD_ID, fileHelper);
    }

    @Override
    protected void registerModels() {
        ModelFile itemGenerated = getExistingFile(mcLoc("item/generated"));
        builder(itemGenerated, CoinBronze.ITEM_NAME);
        builder(itemGenerated, CoinSilver.ITEM_NAME);
        builder(itemGenerated, CoinGold.ITEM_NAME);
    }

    private void builder(ModelFile itemGenerated, String name) {
        getBuilder(name).parent(itemGenerated).texture("layer0", "item/" + name);
    }
}
