package net.liplum.lib.utils;

import net.liplum.Tags;
import net.liplum.lib.FawNbt;
import net.liplum.lib.items.WeaponBaseItem;
import net.liplum.lib.items.gemstone.Gemstone;
import net.liplum.lib.modifiers.Modifier;
import net.liplum.lib.registeies.GemstoneRegistry;
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
    public static Modifier getModifierFrom(ItemStack itemStack) {
        Item item = itemStack.getItem();
        if (!(item instanceof WeaponBaseItem)) {
            return null;
        }
        WeaponBaseItem weapon = (WeaponBaseItem) item;
        Gemstone gemstone = getGemstoneFrom(itemStack);
        if (gemstone == null) {
            return null;
        }
        return gemstone.getModifierOf(weapon.getCore());
    }

    /**
     *
     * @param itemStack
     * @return the first gemstone contained in the weapon. If this item is not a weapon then return false.
     */
    @Nullable
    public static Gemstone getGemstoneFrom(ItemStack itemStack) {
        Item item = itemStack.getItem();
        if (!(item instanceof WeaponBaseItem)) {
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
        return GemstoneRegistry.Instance().getGemstone(gemstoneName);
    }
}
