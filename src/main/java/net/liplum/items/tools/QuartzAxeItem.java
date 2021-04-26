package net.liplum.items.tools;

import net.liplum.registeies.ItemGroupRegistries;
import net.liplum.registeies.TierRegistries;
import net.minecraft.item.AxeItem;
import net.minecraftforge.common.ToolType;

public class QuartzAxeItem extends AxeItem {
    public QuartzAxeItem(){
        super(TierRegistries.QUARTZ_TIER,
                4,
                -2.4F,
                new Properties()
                        .addToolType(ToolType.AXE,3)
                        .tab(ItemGroupRegistries.FawItemGroup));
    }
}
