package net.liplum.lib.util;

import net.liplum.Tags;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public final class FawNbtTool {
    private FawNbtTool() {

    }

    public static NBTTagCompound getBase(NBTTagCompound root) {
        return NbtUtil.getSubCompoundOrCreate(root, Tags.Base);
    }

    public static NBTTagCompound getBase(ItemStack itemStack) {
        NBTTagCompound root = NbtUtil.getOrCreateFrom(itemStack);
        return getBase(root);
    }

    public static void setIntoBase(ItemStack itemStack, NBTTagCompound tag) {
        NBTTagCompound root = NbtUtil.getOrCreateFrom(itemStack);
        setIntoBase(root, tag);
        itemStack.setTagCompound(root);
    }

    public static void setIntoBase(NBTTagCompound root, NBTTagCompound tag) {
        root.setTag(Tags.Base, tag);
    }
}
