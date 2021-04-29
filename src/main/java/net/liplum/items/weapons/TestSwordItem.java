package net.liplum.items.weapons;

import net.liplum.registeies.ItemGroupRegistries;
import net.liplum.registeies.TierRegistries;
import net.minecraft.item.ItemTier;
import net.minecraft.item.SwordItem;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class TestSwordItem extends SwordItem {
    @SubscribeEvent
    public static void On(){

    }
    public TestSwordItem(Properties properties) {
        super(ItemTier.IRON,
                3,
                -2.4F,
                properties);
    }
}
