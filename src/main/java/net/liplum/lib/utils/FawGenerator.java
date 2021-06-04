package net.liplum.lib.utils;

import net.liplum.Tags;
import net.liplum.registeies.ItemRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;

public final class FawGenerator {
    public static ItemStack genWeaponWithGemstone(Item item, String gemstoneName) {
        ItemStack itemStack = new ItemStack(item);

        NBTTagList gemstoneList = FawNbtTool.getGemstoneList(itemStack);

        NBTTagCompound ruby = new NBTTagCompound();
        NBTTagString rubyName = new NBTTagString(gemstoneName);
        ruby.setTag(Tags.BaseSub.GemstoneObject.Gemstone, rubyName);

        gemstoneList.appendTag(ruby);
        return itemStack;
    }

}
