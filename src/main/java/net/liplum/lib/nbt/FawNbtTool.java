package net.liplum.lib.nbt;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public final class FawNbtTool {
    private FawNbtTool() {

    }

    public static NBTTagCompound getFawBase(ItemStack itemStack) {
        NBTTagCompound root = NbtUtil.getOrCreateFrom(itemStack);
        return FawNbts.FawBase.getFawBase(root);
    }

    public static void setFawBaseInToItem(ItemStack itemStack, NBTTagCompound fawBase) {
        NBTTagCompound root = NbtUtil.getOrCreateFrom(itemStack);
        FawNbts.FawBase.setFawBase(root, fawBase);
    }

    public static void setGemstoneList(ItemStack itemStack, NBTTagList modifierList) {
        NBTTagCompound fawBase = getFawBase(itemStack);
        FawNbts.GemstoneList.setGemstoneList(fawBase, modifierList);
    }

    public static NBTTagList getGemstoneList(ItemStack itemStack) {
        NBTTagCompound fawBase = getFawBase(itemStack);
        return FawNbts.GemstoneList.getGemstoneList(fawBase);
    }

    public static void setGemstone(ItemStack itemStack, String gemstoneName) {
        NBTTagList gemList = getGemstoneList(itemStack);
        NBTTagCompound gemstoneObj = new NBTTagCompound();
        FawNbts.GemstoneObject.setGemstone(gemstoneObj, gemstoneName);
        NbtUtil.setFirstOrAdd(gemList, gemstoneObj);
    }

    public static void setWeaponPart(ItemStack itemStack, String weaponPartName) {
        NBTTagCompound fawBase = getFawBase(itemStack);
        FawNbts.WeaponPart.setWeaponPart(fawBase, weaponPartName);
    }

    public static String getWeaponPart(ItemStack itemStack) {
        NBTTagCompound fawBase = getFawBase(itemStack);
        return FawNbts.WeaponPart.getWeaponPart(fawBase);
    }

    //TODO:Durability
/*    public static NBTTagCompound getDurability(ItemStack itemStack){
        NBTTagCompound fawBase = getFawBaseFromItem(itemStack);
    }*/
}
