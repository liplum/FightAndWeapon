package net.liplum.lib.nbt;

import net.liplum.Tags;
import net.liplum.lib.utils.Utils;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import javax.annotation.Nonnull;

//TODO:Complete these notes
public final class FawNbts {
    private FawNbts() {
    }

    /**
     * Weapon:<br/>
     * Gemstones(GemstoneList)-list(str)<br/>
     * [<br/>
     * Gemstone(Gemstone)-str<br/>
     * Gemergy(Gemergy)-int<br/>
     * ]<br/>
     * Durability(Durability)-int<br/>
     * ...<br/>
     * CastBlueprint:<br/>
     */
    public static final class FawBase {
        /**
         * Puts the Faw-base data into the item's root NBT.
         *
         * @param root    the root NBT, which comes from {@link ItemStack#getTagCompound()}
         * @param fawBase the new Faw-base data NBT.
         */
        public static void setFawBase(NBTTagCompound root, NBTTagCompound fawBase) {
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
        public static void setGemstoneList(NBTTagCompound base, NBTTagList gemstoneList) {
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
         * @param gemstoneName
         */
        public static void setGemstone(NBTTagCompound gemstoneObj, String gemstoneName) {
            Utils.notNullThenDo(gemstoneObj, obj -> obj.setString(Tags.BaseSub.GemstoneObject.Gemstone, gemstoneName));
        }

        /**
         * @param gemstoneObj
         * @return
         */
        public static String getGemstone(NBTTagCompound gemstoneObj) {
            return gemstoneObj.getString(Tags.BaseSub.GemstoneObject.Gemstone);
        }

        /**
         * @param gemstoneObj
         * @param gemergy
         */
        public static void setGemergy(NBTTagCompound gemstoneObj, int gemergy) {
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


    public static final class Mastery {

    }

    public static final class Weapon {
        /**
         * @param base
         * @param durability
         */
        public static void setDurability(@Nonnull NBTTagCompound base, int durability) {
            Utils.notNullThenDo(base, obj -> obj.setInteger(Tags.BaseSub.Durability, durability));
        }

        /**
         * Durability(Durability)-int<br/>
         */
        @Nonnull
        public static NBTTagCompound getDurability(@Nonnull NBTTagCompound base) {
            return NbtUtil.getSubCompoundOrCreate(base, Tags.BaseSub.Durability);
        }
    }

    public static final class WeaponPart {
        /**
         * @param base
         * @param weaponPart
         */
        public static void setWeaponPart(@Nonnull NBTTagCompound base, @Nonnull String weaponPart) {
            Utils.notNullThenDo(base, obj -> obj.setString(Tags.BaseSub.WeaponPart, weaponPart));
        }

        /**
         * Durability(Durability)-int<br/>
         */
        @Nonnull
        public static String getWeaponPart(@Nonnull NBTTagCompound base) {
            return base.getString(Tags.BaseSub.WeaponPart);
        }
    }
}