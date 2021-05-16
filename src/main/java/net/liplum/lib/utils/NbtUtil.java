package net.liplum.lib.utils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import javax.annotation.Nonnull;

public final class NbtUtil {
    private NbtUtil() {

    }

    /**
     *
     * @param itemStack
     * @return
     */
    public static NBTTagCompound getOrCreateFrom(@Nonnull ItemStack itemStack) {
        if (itemStack.isEmpty() || !(itemStack.hasTagCompound())) {
            NBTTagCompound root = new NBTTagCompound();
            itemStack.setTagCompound(root);
            return root;
        }
        return itemStack.getTagCompound();
    }

    /**
     *
     * @param nbt current NBT
     * @param subkey a key in the NBT map
     * @return
     */
    public static NBTTagCompound getSubCompoundOrCreate(@Nonnull NBTTagCompound nbt, @Nonnull String subkey) {
        if (nbt.hasKey(subkey)) {
            return nbt.getCompoundTag(subkey);
        }
        NBTTagCompound newOne = new NBTTagCompound();
        nbt.setTag(subkey,newOne);
        return newOne;
    }

    public static NBTTagList getSubListOrCreate(@Nonnull NBTTagCompound nbt, @Nonnull String subkey, int type) {
        if (nbt.hasKey(subkey)) {
            return nbt.getTagList(subkey,type);
        }
        NBTTagList newOne = new NBTTagList();
        nbt.setTag(subkey,newOne);
        return newOne;
    }

    public static void addInto(@Nonnull NBTTagList container, @Nonnull NBTTagCompound obj) {
        container.appendTag(obj);
    }
}
