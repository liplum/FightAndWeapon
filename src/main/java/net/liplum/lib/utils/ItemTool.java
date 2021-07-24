package net.liplum.lib.utils;

import net.liplum.Vanilla;
import net.liplum.lib.math.MathUtil;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.CooldownTracker;
import net.minecraft.util.EnumHand;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ItemTool {
    /**
     * Heats the weapon if player is in survival mode.
     *
     * @param player        a player who has the hot weapon
     * @param item          a item which the player holds and will be heated
     * @param coolDownTicks how much time need to cool down
     * @return if player is in survival mode, it'll return ture and heat the weapon, otherwise, return false.
     */
    public static boolean heatItemIfSurvival(EntityPlayer player, Item item, int coolDownTicks) {
        if (player.isCreative() || coolDownTicks <= 0) {
            return false;
        }
        player.getCooldownTracker().setCooldown(item, coolDownTicks);
        return true;
    }

    public static boolean reduceItemCoolDown(EntityPlayer player, Item item, int decrementTicks) {
        if (decrementTicks <= 0) {
            return false;
        }
        CooldownTracker tracker = player.getCooldownTracker();
        float restRate = tracker.getCooldown(item, 0.0F);
        int fullCoolDownTicks = getFullCoolDownTicks(player, item);
        int restTicks = (int) (restRate * fullCoolDownTicks);
        if (restTicks > 0) {
            int newCoolDown = MathUtil.fixMin(restTicks - decrementTicks, 0);
            if (newCoolDown == 0) {
                tracker.removeCooldown(item);
            } else {
                tracker.setCooldown(item, newCoolDown);
            }
            return true;
        }
        return false;
    }

    public static int getFullCoolDownTicks(EntityPlayer player, Item item) {
        CooldownTracker cooldownTracker = player.getCooldownTracker();
        //By AT
        CooldownTracker.Cooldown cooldownObj = cooldownTracker.cooldowns.get(item);
        if (cooldownObj != null) {
            return Math.abs(cooldownObj.expireTicks - cooldownObj.createTicks);
        }
        return 0;
    }

    /**
     * @param item
     * @param itemStacks
     * @return
     */
    public static ItemStack getItemStacks(Item item, ItemStack... itemStacks) {
        for (ItemStack itemStack : itemStacks) {
            if (item == itemStack.getItem()) {
                return itemStack;
            }
        }
        return null;
    }

    public static List<ItemStack> getItemStacks(Predicate<ItemStack> filter, ItemStack... itemStacks) {
        return Arrays.stream(itemStacks).filter(filter).collect(Collectors.toList());
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
    public static ItemStack getItemStacks(EntityPlayer player, Predicate<ItemStack> filter) {
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

    public static int getCurrentDurability(ItemStack itemStack) {
        return itemStack.getMaxDamage() - itemStack.getItemDamage();
    }

    public static void decreaseItemDurability(ItemStack itemStack, @Nonnegative int amount) {
        //Damage
        int finalDamage = MathUtil.fixMax(amount, getCurrentDurability(itemStack));
        if (finalDamage != 0) {
            itemStack.setItemDamage(itemStack.getItemDamage() + finalDamage);
        }
    }

    public static void increaseItemDurability(ItemStack itemStack, @Nonnegative int amount) {
        //Heal
        int finalHeal = MathUtil.fixMax(amount, itemStack.getItemDamage());
        if (finalHeal != 0) {
            itemStack.setItemDamage(itemStack.getItemDamage() - finalHeal);
        }
    }
}
