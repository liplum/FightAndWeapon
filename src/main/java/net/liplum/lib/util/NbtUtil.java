package net.liplum.lib.util;

import net.liplum.Tags;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import javax.annotation.Nonnull;

public final class NbtUtil {
    private NbtUtil() {

    }

    public static NBTTagCompound getOrCreateFrom(@Nonnull ItemStack itemStack) {
        if (itemStack.isEmpty() || !(itemStack.hasTagCompound())) {
            return new NBTTagCompound();
        }
        return itemStack.getTagCompound();
    }

    public static NBTTagCompound getSubCompoundOrCreate(NBTTagCompound nbt, String subkey) {
        return nbt == null ? new NBTTagCompound() : nbt.getCompoundTag(subkey);
    }

    public static NBTTagList getSubListOrCreate(NBTTagCompound nbt, String subkey, int type) {
        return nbt == null ? new NBTTagList() : nbt.getTagList(subkey, type);
    }

}
