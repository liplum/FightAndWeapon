package net.liplum.lib.utils;

import net.liplum.I18ns;
import net.liplum.api.registeies.GemstoneRegistry;
import net.liplum.api.weapon.IGemstone;
import net.liplum.api.weapon.Modifier;
import net.liplum.api.weapon.WeaponCore;
import net.liplum.lib.items.WeaponBaseItem;
import net.liplum.lib.nbt.FawNbtTool;
import net.liplum.lib.nbt.FawNbts;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import javax.annotation.Nullable;

public final class GemUtil {

    /**
     * @param itemStack
     * @return
     */
    @Nullable
    public static Modifier<?> getModifierFrom(ItemStack itemStack) {
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

    public static String getNameI18nKey(IGemstone gemstone) {
        return I18ns.endWithName(I18ns.prefixItem(gemstone.getRegisterName()));
    }

    public static boolean hasGemstone(ItemStack itemStack) {
        Item item = itemStack.getItem();
        if (!(item instanceof WeaponBaseItem<?>)) {
            return false;
        }
        NBTTagList gemList = FawNbtTool.getGemstoneList(itemStack);
        if (gemList.tagCount() == 0) {
            return false;
        }
        NBTBase gemstoneNbt = gemList.get(0);
        if (gemstoneNbt instanceof NBTTagCompound) {
            String gemstoneName = FawNbts.GemstoneObject.getGemstone((NBTTagCompound) gemstoneNbt);
            return !gemstoneName.isEmpty();
        }
        return false;
    }

    public static boolean canInlayGemstone(WeaponCore core, IGemstone gemstone) {
        return gemstone.hasAnyAmplifier(core);
    }

    public static boolean canInlayGemstone(WeaponCore core, String gemstoneName) {
        IGemstone gemstone = GemstoneRegistry.getGemstone(gemstoneName);
        if (gemstone == null) {
            return false;
        }
        return gemstone.hasAnyAmplifier(core);
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
        NBTTagList gemList = FawNbtTool.getGemstoneList(itemStack);
        if (gemList.tagCount() == 0) {
            return null;
        }
        //It's up to how many a weapon can contain gemstones.
        NBTBase gemstoneNbt = gemList.get(0);
        //Gets corresponding gemstone by its name.
        if (gemstoneNbt instanceof NBTTagCompound) {
            String gemstoneName = FawNbts.GemstoneObject.getGemstone((NBTTagCompound) gemstoneNbt);
            return GemstoneRegistry.getGemstone(gemstoneName);
        }
        return null;
    }

    public static InlayResult inlayGemstone(ItemStack itemStack, IGemstone gemstone) {
        return inlayGemstone(itemStack, gemstone.getRegisterName());
    }

    public static InlayResult inlayGemstone(ItemStack itemStack, String gemstoneName) {
        if (!GemstoneRegistry.hasGemstone(gemstoneName)) {
            return InlayResult.NoSuchGemstone;
        }
        Item item = itemStack.getItem();
        if (!(item instanceof WeaponBaseItem)) {
            return InlayResult.NotFawWeapon;
        }
        FawNbtTool.setGemstone(itemStack, gemstoneName);
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
        NBTTagList gemList = FawNbtTool.getGemstoneList(itemStack);
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
