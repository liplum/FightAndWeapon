package net.liplum.lib;

import net.liplum.Tags;
import net.liplum.lib.util.NbtUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.ArrayList;
import java.util.List;

public final class FawNbt {
    private FawNbt() {
    }

    public static final class FawBase {
        public static void putIntoFawBase(NBTTagCompound root, NBTTagCompound tag) {
            if (root != null) {
                root.setTag(Tags.Base, tag);
            }
        }

        public static NBTTagCompound getFawBase(NBTTagCompound root) {
            return NbtUtil.getSubCompoundOrCreate(root, Tags.Base);
        }
    }

    public static final class ModifierList {
        public static void putIntoModifierList(NBTTagCompound root, NBTTagCompound tag) {

        }
    }
}