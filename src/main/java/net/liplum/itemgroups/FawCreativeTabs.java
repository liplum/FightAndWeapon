package net.liplum.itemgroups;

import net.liplum.ObjectNames;
import net.liplum.registeies.ItemRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class FawCreativeTabs extends CreativeTabs {

    public FawCreativeTabs(){
        super(ObjectNames.Item_Group_Key);
    }


    @Override
    public ItemStack getTabIconItem() {
        return  new ItemStack(ItemRegistry.Ruby_Item);
    }
}
