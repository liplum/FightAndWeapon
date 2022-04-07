package net.liplum.lib.utils;

import net.liplum.Vanilla;
import net.liplum.api.annotations.LongSupport;
import net.liplum.lib.math.MathUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.CooldownTracker;
import net.minecraft.util.EnumHand;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnegative;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@LongSupport
public final class ItemUtil {
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

    public static boolean hasAmmo(@NotNull EntityPlayer player, @NotNull Predicate<ItemStack> whatIsAmmo) {
        return !findAmmo(player, whatIsAmmo).isEmpty();
    }

    @NotNull
    public static ItemStack findAmmo(@NotNull EntityPlayer player, @NotNull Predicate<ItemStack> whatIsAmmo) {
        EnumHand hand = inWhichHand(player, whatIsAmmo, EnumHand.OFF_HAND);
        if (hand != null) {
            return player.getHeldItem(hand);
        } else {
            return getMainSlotsStream(player).filter(whatIsAmmo).findFirst().orElse(ItemStack.EMPTY);
        }
    }

    public static boolean isArrow(ItemStack stack) {
        return stack.getItem() instanceof ItemArrow;
    }

    /**
     * Heats the weapon if player is in whatever mode.
     *
     * @param player        a player who has the hot weapon
     * @param item          a item which the player holds and will be heated
     * @param coolDownTicks how much time need to cool down
     * @return if coolDownTicks is more than 0, it'll return ture and heat the weapon, otherwise, return false.
     */
    public static boolean heatItem(EntityPlayer player, Item item, int coolDownTicks) {
        if (coolDownTicks <= 0) {
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

    /**
     * It will check {@link EnumHand#MAIN_HAND} at the first.
     */
    @Nullable
    public static EnumHand inWhichHand(@NotNull EntityLivingBase entity, @NotNull ItemStack itemStack) {
        return inWhichHand(entity, itemStack, EnumHand.MAIN_HAND);
    }

    /**
     * It will check {@link EnumHand#MAIN_HAND} at the first.
     */
    @Nullable
    public static EnumHand inWhichHand(@NotNull EntityLivingBase entity, @NotNull ItemStack itemStack, @NotNull EnumHand firstChecked) {
        return inWhichHand(entity, itemStack::equals, firstChecked);
    }

    @NotNull
    public static EnumHand otherHand(@NotNull EnumHand hand) {
        return hand == EnumHand.MAIN_HAND ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;
    }

    /**
     * It will check {@link EnumHand#MAIN_HAND} at the first.
     */
    @Nullable
    public static EnumHand inWhichHand(@NotNull EntityLivingBase entity, @NotNull Predicate<ItemStack> filter) {
        return inWhichHand(entity, filter, EnumHand.MAIN_HAND);
    }

    @Nullable
    public static EnumHand inWhichHand(@NotNull EntityLivingBase entity, @NotNull Predicate<ItemStack> filter, @NotNull EnumHand firstChecked) {
        if (filter.test(entity.getHeldItem(firstChecked))) {
            return EnumHand.MAIN_HAND;
        }
        EnumHand otherHand = otherHand(firstChecked);
        if (filter.test(entity.getHeldItem(otherHand))) {
            return otherHand;
        }
        return null;
    }

    /**
     * Get the item stack which fits the filter.<br/>
     * Main Hand will be tested first and then it'll test Off Hand.
     *
     * @param player
     * @param filter
     * @return
     */
    @NotNull
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

    public static void tryDropItem(@NotNull EntityPlayer player, @NotNull ItemStack itemStack) {
        if (itemStack != ItemStack.EMPTY) {
            player.dropItem(itemStack, false);
        }
    }

    public static AttributeModifier genAttrModifier(@NotNull UUID uuid, @NotNull Vanilla.AttrModifierType type, @NotNull String modifierName, double amount) {
        return new AttributeModifier(uuid, modifierName, amount, type.type);
    }

    public static AttributeModifier genAttrModifier(@NotNull Vanilla.AttrModifierType type, @NotNull String modifierName, double amount) {
        return new AttributeModifier(modifierName, amount, type.type);
    }

    public static int getCurrentDurability(ItemStack itemStack) {
        return MathUtil.fixMin(itemStack.getMaxDamage() - itemStack.getItemDamage(), 0);
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

    //----------------------------------------------------------------------------------

    @NotNull
    public static Stream<ItemStack> getMainSlotsStream(@NotNull EntityPlayer player) {
        return player.inventory.mainInventory.stream();
    }

    @NotNull
    public static Stream<ItemStack> getArmorSlotsStream(@NotNull EntityPlayer player) {
        return player.inventory.armorInventory.stream();
    }

    @NotNull
    public static Stream<ItemStack> getOffHandSlotsStream(@NotNull EntityPlayer player) {
        return player.inventory.offHandInventory.stream();
    }

    @NotNull
    public static Stream<ItemStack> getMainAndOffHandSlotsStream(@NotNull EntityPlayer player) {
        return Utils.mergeStream(player.inventory.mainInventory, player.inventory.offHandInventory);
    }

    @NotNull
    public static Stream<ItemStack> getMainAndArmorSlotsStream(@NotNull EntityPlayer player) {
        return Utils.mergeStream(player.inventory.mainInventory, player.inventory.armorInventory);
    }

    @NotNull
    public static Stream<ItemStack> getAllSlotsStream(@NotNull EntityPlayer player) {
        return Utils.mergeStream(player.inventory.mainInventory, player.inventory.armorInventory, player.inventory.offHandInventory);
    }

    @NotNull
    public static Stream<ItemStack> getArmorAndOffHandSlotsStream(@NotNull EntityPlayer player) {
        return Utils.mergeStream(player.inventory.armorInventory, player.inventory.offHandInventory);
    }

    //----------------------------------------------------------------------------------

    @NotNull
    public static Iterable<ItemStack> getMainSlots(@NotNull EntityPlayer player) {
        return () -> getMainSlotsStream(player).iterator();
    }

    @NotNull
    public static Iterable<ItemStack> getArmorSlots(@NotNull EntityPlayer player) {
        return () -> getArmorSlotsStream(player).iterator();
    }

    @NotNull
    public static Iterable<ItemStack> getOffHandSlots(@NotNull EntityPlayer player) {
        return () -> getOffHandSlotsStream(player).iterator();
    }

    @NotNull
    public static Iterable<ItemStack> getMainAndOffHandSlots(@NotNull EntityPlayer player) {
        return () -> getMainAndOffHandSlotsStream(player).iterator();
    }

    @NotNull
    public static Iterable<ItemStack> getMainAndArmorSlots(@NotNull EntityPlayer player) {
        return () -> getMainAndArmorSlotsStream(player).iterator();
    }

    @NotNull
    public static Iterable<ItemStack> getAllSlots(@NotNull EntityPlayer player) {
        return () -> getAllSlotsStream(player).iterator();
    }

    @NotNull
    public static Iterable<ItemStack> getArmorAndOffHandSlots(@NotNull EntityPlayer player) {
        return () -> getArmorAndOffHandSlotsStream(player).iterator();
    }

    //----------------------------------------------------------------------------------

    @NotNull
    public static Iterable<ItemStack> getMainSlots(@NotNull EntityPlayer player, @NotNull Predicate<ItemStack> filter) {
        return () -> getMainSlotsStream(player).filter(filter).iterator();
    }

    @NotNull
    public static Iterable<ItemStack> getArmorSlots(@NotNull EntityPlayer player, @NotNull Predicate<ItemStack> filter) {
        return () -> getArmorSlotsStream(player).filter(filter).iterator();
    }

    @NotNull
    public static Iterable<ItemStack> getOffHandSlots(@NotNull EntityPlayer player, @NotNull Predicate<ItemStack> filter) {
        return () -> getOffHandSlotsStream(player).filter(filter).iterator();
    }

    @NotNull
    public static Iterable<ItemStack> getMainAndOffHandSlots(@NotNull EntityPlayer player, @NotNull Predicate<ItemStack> filter) {
        return () -> getMainAndOffHandSlotsStream(player).filter(filter).iterator();
    }

    @NotNull
    public static Iterable<ItemStack> getMainAndArmorSlots(@NotNull EntityPlayer player, @NotNull Predicate<ItemStack> filter) {
        return () -> getMainAndArmorSlotsStream(player).filter(filter).iterator();
    }

    @NotNull
    public static Iterable<ItemStack> getAllSlots(@NotNull EntityPlayer player, @NotNull Predicate<ItemStack> filter) {
        return () -> getAllSlotsStream(player).filter(filter).iterator();
    }

    @NotNull
    public static Iterable<ItemStack> getArmorAndOffHandSlots(@NotNull EntityPlayer player, @NotNull Predicate<ItemStack> filter) {
        return () -> getArmorAndOffHandSlotsStream(player).filter(filter).iterator();
    }
}
