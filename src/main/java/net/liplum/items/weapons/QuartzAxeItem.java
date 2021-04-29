package net.liplum.items.weapons;

import net.liplum.registeies.ItemGroupRegistries;
import net.liplum.registeies.TierRegistries;
import net.minecraft.item.AxeItem;
import net.minecraftforge.common.ToolType;

public class QuartzAxeItem extends AxeItem {
    public QuartzAxeItem(Properties properties){
        super(TierRegistries.QUARTZ_TIER,
                4,
                -2.4F,
                properties
                        .addToolType(ToolType.AXE,3)
        );
    }
}
