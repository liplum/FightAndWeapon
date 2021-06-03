package net.liplum.lib;

import net.liplum.Tags;
import net.liplum.lib.utils.NbtUtil;
import net.liplum.lib.utils.Utils;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;

//TODO:Complete these notes
public final class FawNbt {
    private FawNbt() {
    }

    /**
     * Gemstones(GemstoneList)-list(str)<br/>
     * [<br/>
     * Gemstone(Gemstone)-str<br/>
     * Gemergy(Gemergy)-int<br/>
     * ]<br/>
     * Durability(Durability)-int<br/>
     * ...<br/>
     */
    public static final class FawBase {
        /**
         * Puts the Faw-base data into the item's root NBT.
         *
         * @param root    the root NBT, which comes from {@link ItemStack#getTagCompound()}
         * @param fawBase the new Faw-base data NBT.
         */
        public static void putFawBase(NBTTagCompound root, NBTTagCompound fawBase) {
            Utils.notNullThenDo(root, r -> r.setTag(Tags.Base, fawBase));
        }

        /**
         * Gets the Faw-base from the item's root NBT
         *
         * @param root the root NBT, which comes from {@link ItemStack#getTagCompound()}
         * @return the Faw-base date
         */
        public static NBTTagCompound getFawBase(NBTTagCompound root) {
            return NbtUtil.getSubCompoundOrCreate(root, Tags.Base);
        }
    }

    /**
     * [<br/>
     * Modifier(Modifier)-str<br/>
     * Gemergy(Gemergy)-int<br/>
     * ...<br/>
     * ]<br/>
     */
    public static final class GemstoneList {
        /**
         * Puts the gemstone-list data into the Faw-base data NBT.
         *
         * @param base         the Faw-base data NBT, which comes from {@link FawBase#getFawBase(NBTTagCompound)}
         * @param gemstoneList the gemstone-list data
         */
        public static void putGemstoneList(NBTTagCompound base, NBTTagList gemstoneList) {
            Utils.notNullThenDo(base, b -> b.setTag(Tags.BaseSub.GemstoneList, gemstoneList));
        }

        /**
         * @param base
         * @return
         */
        public static NBTTagList getGemstoneList(NBTTagCompound base) {
            return NbtUtil.getSubListOrCreate(base, Tags.BaseSub.GemstoneList, Tags.Type_NbtCompound);
        }
    }

    /**
     * Gemstone(Gemstone)-str<br/>
     * Gemergy(Gemergy)-int<br/>
     * ...<br/>
     */
    public static final class GemstoneObject {
        /**
         * @param gemstoneObj
         * @param gemstone
         */
        public static void putGemstone(NBTTagCompound gemstoneObj, NBTTagString gemstone) {
            Utils.notNullThenDo(gemstoneObj, obj -> obj.setTag(Tags.BaseSub.GemstoneObject.Gemstone, gemstone));
        }

        /**
         * @param gemstoneObj
         * @return
         */
        public static NBTTagCompound getGemstone(NBTTagCompound gemstoneObj) {
            return NbtUtil.getSubCompoundOrCreate(gemstoneObj, Tags.BaseSub.GemstoneObject.Gemstone);
        }

        /**
         * @param gemstoneObj
         * @param gemergy
         */
        public static void putGemergy(NBTTagCompound gemstoneObj, int gemergy) {
            Utils.notNullThenDo(gemstoneObj, obj -> obj.setInteger(Tags.BaseSub.GemstoneObject.Gemergy, gemergy));
        }

        /**
         * @param gemstoneObj
         * @return
         */
        public static NBTTagCompound getGemergy(NBTTagCompound gemstoneObj) {
            return NbtUtil.getSubCompoundOrCreate(gemstoneObj, Tags.BaseSub.GemstoneObject.Gemergy);
        }
    }

    /**
     * Durability(Durability)-int<br/>
     */
    public static final class Durability {
        /**
         * @param base
         * @param durability
         */
        public static void putDurability(NBTTagCompound base, int durability) {
            Utils.notNullThenDo(base, obj -> obj.setInteger(Tags.BaseSub.Durability, durability));
        }

        /**
         * @param base
         * @return
         */
        public static NBTTagCompound getDurability(NBTTagCompound base) {
            return NbtUtil.getSubCompoundOrCreate(base, Tags.BaseSub.Durability);
        }
    }

    public static final class Master {

    }
}