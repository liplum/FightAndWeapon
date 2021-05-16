package net.liplum.lib;

import net.liplum.Tags;
import net.liplum.lib.utils.JavaUtil;
import net.liplum.lib.utils.NbtUtil;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagString;

//TODO:Complete these notes
public final class FawNbt {
    private FawNbt() {
    }

    /**
     * Modifiers(ModifierList)-list(str)<br/>
     *     [<br/>
     *         Modifier(Modifier)-str<br/>
     *         Gemergy(Gemergy)-int<br/>
     *     ]<br/>
     * Durability(Durability)-int<br/>
     * ...<br/>
     */
    public static final class FawBase {
        /**
         * Puts the Faw-base data into the item's root NBT.
         * @param root the root NBT, which comes from {@link ItemStack#getTagCompound()}
         * @param fawBase the new Faw-base data NBT.
         */
        public static void putFawBase(NBTTagCompound root, NBTTagCompound fawBase) {
            JavaUtil.notNullThenDo(root, r -> r.setTag(Tags.Base, fawBase));
        }

        /**
         * Gets the Faw-base from the item's root NBT
         * @param root the root NBT, which comes from {@link ItemStack#getTagCompound()}
         * @return the Faw-base date
         */
        public static NBTTagCompound getFawBase(NBTTagCompound root) {
            return NbtUtil.getSubCompoundOrCreate(root, Tags.Base);
        }
    }
    /**
     * [<br/>
     *     Modifier(Modifier)-str<br/>
     *     Gemergy(Gemergy)-int<br/>
     *     ...<br/>
     * ]<br/>
     */
    public static final class ModifierList {
        /**
         * Puts the modifier-list data into the Faw-base data NBT.
         * @param base the Faw-base data NBT, which comes from {@link FawBase#getFawBase(NBTTagCompound)}
         * @param modifierList the modifier-list data
         */
        public static void putModifierList(NBTTagCompound base, NBTTagList modifierList) {
            JavaUtil.notNullThenDo(base, b -> b.setTag(Tags.BaseSub.ModifierList, modifierList));
        }

        /**
         *
         * @param base
         * @return
         */
        public static NBTTagList getFawBase(NBTTagCompound base) {
            return NbtUtil.getSubListOrCreate(base, Tags.BaseSub.ModifierList, Tags.Type_String);
        }
    }

    /**
     * Modifier(Modifier)-str<br/>
     * Gemergy(Gemergy)-int<br/>
     * ...<br/>
     */
    public static final class ModifierObject {
        /**
         *
         * @param modifierObj
         * @param modifier
         */
        public static void putModifier(NBTTagCompound modifierObj, NBTTagString modifier) {
            JavaUtil.notNullThenDo(modifierObj, obj -> obj.setTag(Tags.BaseSub.ModifierObject.Modifier, modifier));
        }

        /**
         *
         * @param modifierObj
         * @return
         */
        public static NBTTagCompound getModifier(NBTTagCompound modifierObj) {
            return NbtUtil.getSubCompoundOrCreate(modifierObj, Tags.BaseSub.ModifierObject.Modifier);
        }

        /**
         *
         * @param modifierObj
         * @param gemergy
         */
        public static void putGemergy(NBTTagCompound modifierObj, int gemergy) {
            JavaUtil.notNullThenDo(modifierObj, obj -> obj.setInteger(Tags.BaseSub.ModifierObject.Gemergy, gemergy));
        }

        /**
         *
         * @param modifierObj
         * @return
         */
        public static NBTTagCompound getGemergy(NBTTagCompound modifierObj) {
            return NbtUtil.getSubCompoundOrCreate(modifierObj, Tags.BaseSub.ModifierObject.Gemergy);
        }
    }

    /**
     * Durability(Durability)-int<br/>
     */
    public static final class Durability {
        /**
         *
         * @param base
         * @param durability
         */
        public static void putDurability(NBTTagCompound base, int durability) {
            JavaUtil.notNullThenDo(base, obj -> obj.setInteger(Tags.BaseSub.Durability, durability));
        }

        /**
         *
         * @param base
         * @return
         */
        public static NBTTagCompound getDurability(NBTTagCompound base) {
            return NbtUtil.getSubCompoundOrCreate(base, Tags.BaseSub.Durability);
        }
    }
}