package net.liplum.lib.utils;

import net.liplum.Tags;
import net.liplum.api.registeies.GemstoneRegistry;
import net.liplum.api.weapon.IGemstone;
import net.liplum.api.weapon.IModifier;
import net.liplum.lib.items.WeaponBaseItem;
import net.liplum.lib.nbt.FawNbt;
import net.liplum.lib.nbt.NbtUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import javax.annotation.Nullable;

public final class FawGemUtil {
    /**
     * @param itemStack
     * @return
     */
    @Nullable
    public static IModifier<?> getModifierFrom(ItemStack itemStack) {
        Item item = itemStack.getItem();
        if (!(item instanceof WeaponBaseItem<?>)) {
            return null;
        }
        WeaponBaseItem<?> weapon = (WeaponBaseItem<?>) item;
        IGemstone gemstone = getGemstoneFrom(itemStack);
        if (gemstone == null) {
            return null;
        }
        return gemstone.getModifierOf(weapon.getCore());
    }

    /**
     * @param itemStack
     * @return the first gemstone contained in the weapon. If this item is not a weapon then return false.
     */
    @Nullable
    public static IGemstone getGemstoneFrom(ItemStack itemStack) {
        Item item = itemStack.getItem();
        if (!(item instanceof WeaponBaseItem<?>)) {
            return null;
        }
        NBTTagCompound root = NbtUtil.getOrCreateFrom(itemStack);
        NBTTagCompound fawBase = FawNbt.FawBase.getFawBase(root);
        NBTTagList gemList = FawNbt.GemstoneList.getGemstoneList(fawBase);
        if (gemList.tagCount() == 0) {
            return null;
        }
        //It's up to how many a weapon can contain gemstones.
        NBTBase gemstoneNbt = gemList.get(0);
        NBTTagCompound gemstoneObj = (NBTTagCompound) gemstoneNbt;
        String gemstoneName = gemstoneObj.getString(Tags.BaseSub.GemstoneObject.Gemstone);
        //Gets corresponding gemstone by its name.
        return GemstoneRegistry.getGemstone(gemstoneName);
    }

    public static InlayResult inlayGemstone(ItemStack itemStack, String gemstoneName) {
        if (!GemstoneRegistry.hasGemstone(gemstoneName)) {
            return InlayResult.NoSuchGemstone;
        }
        Item item = itemStack.getItem();
        if (!(item instanceof WeaponBaseItem)) {
            return InlayResult.NotFawWeapon;
        }
        NBTTagCompound root = NbtUtil.getOrCreateFrom(itemStack);
        NBTTagCompound fawBase = FawNbt.FawBase.getFawBase(root);
        NBTTagList gemList = FawNbt.GemstoneList.getGemstoneList(fawBase);
        NBTTagCompound gemstoneObj = new NBTTagCompound();
        gemstoneObj.setString(Tags.BaseSub.GemstoneObject.Gemstone, gemstoneName);
        if (gemList.tagCount() > 0) {
            gemList.set(0, gemstoneObj);
        } else {
            gemList.appendTag(gemstoneObj);
        }
        return InlayResult.Succeed;
    }

    public static RemoveResult removeGemstone(ItemStack itemStack) {
        return removeGemstone(itemStack, 0);
    }

    public static RemoveResult removeGemstone(ItemStack itemStack, int slot) {
        Item item = itemStack.getItem();
        if (!(item instanceof WeaponBaseItem)) {
            return RemoveResult.NotFawWeapon;
        }
        NBTTagCompound root = NbtUtil.getOrCreateFrom(itemStack);
        NBTTagCompound fawBase = FawNbt.FawBase.getFawBase(root);
        NBTTagList gemList = FawNbt.GemstoneList.getGemstoneList(fawBase);
        if (gemList.tagCount() == 0) {
            return RemoveResult.NoGemstone;
        }
        gemList.removeTag(slot);
        return RemoveResult.Succeed;
    }

    public enum InlayResult {
        Succeed,
        NotFawWeapon,
        NoSuchGemstone
    }

    public enum RemoveResult {
        Succeed,
        NotFawWeapon,
        NoGemstone
    }
}
