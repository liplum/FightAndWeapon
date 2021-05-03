package net.liplum.items.weapons;

import net.liplum.registeies.TierRegistry;
import net.minecraft.item.ItemTier;
import net.minecraft.item.SwordItem;

public class TestSwordItem extends SwordItem {

    public TestSwordItem(Properties properties) {
        super(ItemTier.IRON,
                3,
                -2.4F,
                properties);
    }
}
