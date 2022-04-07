package net.liplum.lib.nbt;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import org.jetbrains.annotations.NotNull;

public final class NbtUtil {
    private NbtUtil() {

    }

    /**
     * @param itemStack
     * @return
     */
    @NotNull
    public static NBTTagCompound getOrCreateFrom(@NotNull ItemStack itemStack) {
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
    @NotNull
    public static NBTTagCompound getSubCompoundOrCreate(@NotNull NBTTagCompound nbt, @NotNull String subkey) {
        if (nbt.hasKey(subkey)) {
            return nbt.getCompoundTag(subkey);
        }
        NBTTagCompound newOne = new NBTTagCompound();
        nbt.setTag(subkey, newOne);
        return newOne;
    }

    @NotNull
    public static NBTTagList getSubListOrCreate(@NotNull NBTTagCompound nbt, @NotNull String subkey, int type) {
        if (nbt.hasKey(subkey)) {
            return nbt.getTagList(subkey, type);
        }
        NBTTagList newOne = new NBTTagList();
        nbt.setTag(subkey, newOne);
        return newOne;
    }

    public static void setFirstOrAdd(@NotNull NBTTagList nbtList, @NotNull NBTBase nbt) {
        if (nbtList.tagCount() == 0) {
            nbtList.appendTag(nbt);
        } else {
            nbtList.set(0, nbt);
        }
    }
}
