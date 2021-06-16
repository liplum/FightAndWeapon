package net.liplum.lib.nbt;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import javax.annotation.Nonnull;

public final class NbtUtil {
    private NbtUtil() {

    }

    /**
     * @param itemStack
     * @return
     */
    @Nonnull
    public static NBTTagCompound getOrCreateFrom(@Nonnull ItemStack itemStack) {
        NBTTagCompound tag = itemStack.getTagCompound();
        if (tag == null) {
            NBTTagCompound root = new NBTTagCompound();
            itemStack.setTagCompound(root);
            return root;
        }
        return tag;
    }

    /**
     * @param nbt    current NBT
     * @param subkey a key in the NBT map
     * @return
     */
    @Nonnull
    public static NBTTagCompound getSubCompoundOrCreate(@Nonnull NBTTagCompound nbt, @Nonnull String subkey) {
        if (nbt.hasKey(subkey)) {
            return nbt.getCompoundTag(subkey);
        }
        NBTTagCompound newOne = new NBTTagCompound();
        nbt.setTag(subkey, newOne);
        return newOne;
    }

    @Nonnull
    public static NBTTagList getSubListOrCreate(@Nonnull NBTTagCompound nbt, @Nonnull String subkey, int type) {
        if (nbt.hasKey(subkey)) {
            return nbt.getTagList(subkey, type);
        }
        NBTTagList newOne = new NBTTagList();
        nbt.setTag(subkey, newOne);
        return newOne;
    }

    public static void setFirstOrAdd(@Nonnull NBTTagList nbtList, @Nonnull NBTBase nbt) {
        if (nbtList.tagCount() == 0) {
            nbtList.appendTag(nbt);
        } else {
            nbtList.set(0, nbt);
        }
    }
}
