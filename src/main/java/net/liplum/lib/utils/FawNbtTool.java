package net.liplum.lib.utils;

import net.liplum.Tags;
import net.liplum.lib.FawNbt;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public final class FawNbtTool {
    private FawNbtTool() {

    }

    public static NBTTagCompound getFawBaseFromItem(ItemStack itemStack) {
        NBTTagCompound root = NbtUtil.getOrCreateFrom(itemStack);
        NBTTagCompound fawBase = FawNbt.FawBase.getFawBase(root);
        return fawBase;
    }

    public static void setFawBaseInToItem(ItemStack itemStack, NBTTagCompound fawBase) {
        NBTTagCompound root = NbtUtil.getOrCreateFrom(itemStack);
        FawNbt.FawBase.putFawBase(root, fawBase);
    }

    public static void setModifierList(ItemStack itemStack, NBTTagList modifierList) {
        NBTTagCompound fawBase = getFawBaseFromItem(itemStack);
        FawNbt.ModifierList.putModifierList(fawBase, modifierList);
    }

    public static NBTTagList getModifierList(ItemStack itemStack) {
        NBTTagCompound fawBase = getFawBaseFromItem(itemStack);
        NBTTagList modifierList = FawNbt.ModifierList.getFawBase(fawBase);
        return modifierList;
    }

    //TODO:Durability
/*    public static NBTTagCompound getDurability(ItemStack itemStack){
        NBTTagCompound fawBase = getFawBaseFromItem(itemStack);
    }*/
}
