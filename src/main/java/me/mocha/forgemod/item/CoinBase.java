package me.mocha.forgemod.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class CoinBase extends Item {

    public CoinBase() {
        super(new Item.Properties()
                .group(ItemGroup.MATERIALS)
                .setNoRepair()
        );
    }

}
