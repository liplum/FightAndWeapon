package net.liplum.lib.utils;

import net.liplum.lib.items.Category;
import net.liplum.lib.items.FawItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public final class FawItemUtil {
    private FawItemUtil(){

    }
    public static boolean hasCategory(ItemStack itemStack, Category category){
        Item item = itemStack.getItem();
        if(item instanceof FawItem){
            return ((FawItem) item).isA(category);
        }
        return false;
    }
}
