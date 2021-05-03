package net.liplum.items.weapons;

import net.liplum.registeies.TierRegistry;
import net.minecraft.item.AxeItem;
import net.minecraftforge.common.ToolType;

public class QuartzAxeItem extends AxeItem {
    public QuartzAxeItem(Properties properties){
        super(TierRegistry.QUARTZ_TIER,
                4,
                -2.4F,
                properties
                        .addToolType(ToolType.AXE,3)
        );
    }
}
