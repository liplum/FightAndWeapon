package net.liplum.itemgroups;

import net.liplum.MetaData;
import net.liplum.registeies.ItemRegistry;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class FawItemGroup extends ItemGroup {

    public FawItemGroup(){
        super(MetaData.ITEM_GROUP_KEY);
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(ItemRegistry.RUBY_ITEM.get());
    }
}
