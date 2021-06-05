package net.liplum.lib.nbt;

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

    public static void setGemstoneList(ItemStack itemStack, NBTTagList modifierList) {
        NBTTagCompound fawBase = getFawBaseFromItem(itemStack);
        FawNbt.GemstoneList.putGemstoneList(fawBase, modifierList);
    }

    public static NBTTagList getGemstoneList(ItemStack itemStack) {
        NBTTagCompound fawBase = getFawBaseFromItem(itemStack);
        NBTTagList modifierList = FawNbt.GemstoneList.getGemstoneList(fawBase);
        return modifierList;
    }

    //TODO:Durability
/*    public static NBTTagCompound getDurability(ItemStack itemStack){
        NBTTagCompound fawBase = getFawBaseFromItem(itemStack);
    }*/
}
