package net.liplum.lib.utils;

import net.liplum.Vanilla;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.UUID;
import java.util.function.Predicate;

public class ItemTool {
    /**
     * Heats the weapon if player is in survival mode.
     *
     * @param player       a player who has the hot weapon
     * @param item         a item which the player holds and will be heated
     * @param coolDownTime how much time need to cool down
     * @return if player is in survival mode, it'll return ture and heat the weapon, otherwise, return false.
     */
    public static boolean heatWeaponIfSurvival(EntityPlayer player, Item item, int coolDownTime) {
        if (player.isCreative() || coolDownTime <= 0) {
            return false;
        }
        player.getCooldownTracker().setCooldown(item, coolDownTime);
        return true;
    }

    /**
     * @param item
     * @param itemStacks
     * @return
     */
    public static ItemStack getItemStack(Item item, ItemStack... itemStacks) {
        for (ItemStack itemStack : itemStacks) {
            if (item == itemStack.getItem()) {
                return itemStack;
            }
        }
        return null;
    }

    @Nullable
    public static EnumHand inWhichHand(EntityPlayer player, ItemStack itemStack) {
        if (player.getHeldItem(EnumHand.MAIN_HAND) == itemStack) {
            return EnumHand.MAIN_HAND;
        } else if (player.getHeldItem(EnumHand.OFF_HAND) == itemStack) {
            return EnumHand.OFF_HAND;
        } else {
            return null;
        }
    }

    /**
     * Get the item stack which fits the filter.<br/>
     * Main Hand will be tested first and then it'll test Off Hand.
     *
     * @param player
     * @param filter
     * @return
     */
    @Nonnull
    public static ItemStack getItemStack(EntityPlayer player, Predicate<ItemStack> filter) {
        ItemStack mainHand = player.getHeldItem(EnumHand.MAIN_HAND);
        if (filter.test(mainHand)) {
            return mainHand;
        }
        ItemStack offHand = player.getHeldItem(EnumHand.OFF_HAND);
        if (filter.test(offHand)) {
            return offHand;
        }
        return ItemStack.EMPTY;
    }

    public static void tryDropItem(@Nonnull EntityPlayer player, @Nonnull ItemStack itemStack) {
        if (itemStack != ItemStack.EMPTY) {
            player.dropItem(itemStack, false);
        }
    }

    public static AttributeModifier genAttrModifier(@Nonnull UUID uuid, @Nonnull Vanilla.AttrModifierType type, @Nonnull String modifierName, double amount) {
        return new AttributeModifier(uuid, modifierName, amount, type.type);
    }

    public static AttributeModifier genAttrModifier(@Nonnull Vanilla.AttrModifierType type, @Nonnull String modifierName, double amount) {
        return new AttributeModifier(modifierName, amount, type.type);
    }
}
