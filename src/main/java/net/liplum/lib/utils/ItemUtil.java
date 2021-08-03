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

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
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

    public static boolean hasAmmo(@Nonnull EntityPlayer player, @Nonnull Predicate<ItemStack> whatIsAmmo) {
        return !findAmmo(player, whatIsAmmo).isEmpty();
    }

    @Nonnull
    public static ItemStack findAmmo(@Nonnull EntityPlayer player, @Nonnull Predicate<ItemStack> whatIsAmmo) {
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
    public static EnumHand inWhichHand(@Nonnull EntityLivingBase entity, @Nonnull ItemStack itemStack) {
        return inWhichHand(entity, itemStack, EnumHand.MAIN_HAND);
    }

    /**
     * It will check {@link EnumHand#MAIN_HAND} at the first.
     */
    @Nullable
    public static EnumHand inWhichHand(@Nonnull EntityLivingBase entity, @Nonnull ItemStack itemStack, @Nonnull EnumHand firstChecked) {
        return inWhichHand(entity, itemStack::equals, firstChecked);
    }

    @Nonnull
    public static EnumHand otherHand(@Nonnull EnumHand hand) {
        return hand == EnumHand.MAIN_HAND ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;
    }

    /**
     * It will check {@link EnumHand#MAIN_HAND} at the first.
     */
    @Nullable
    public static EnumHand inWhichHand(@Nonnull EntityLivingBase entity, @Nonnull Predicate<ItemStack> filter) {
        return inWhichHand(entity, filter, EnumHand.MAIN_HAND);
    }

    @Nullable
    public static EnumHand inWhichHand(@Nonnull EntityLivingBase entity, @Nonnull Predicate<ItemStack> filter, @Nonnull EnumHand firstChecked) {
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

    @Nonnull
    public static Stream<ItemStack> getMainSlotsStream(@Nonnull EntityPlayer player) {
        return player.inventory.mainInventory.stream();
    }

    @Nonnull
    public static Stream<ItemStack> getArmorSlotsStream(@Nonnull EntityPlayer player) {
        return player.inventory.armorInventory.stream();
    }

    @Nonnull
    public static Stream<ItemStack> getOffHandSlotsStream(@Nonnull EntityPlayer player) {
        return player.inventory.offHandInventory.stream();
    }

    @Nonnull
    public static Stream<ItemStack> getMainAndOffHandSlotsStream(@Nonnull EntityPlayer player) {
        return Utils.mergeStream(player.inventory.mainInventory, player.inventory.offHandInventory);
    }

    @Nonnull
    public static Stream<ItemStack> getMainAndArmorSlotsStream(@Nonnull EntityPlayer player) {
        return Utils.mergeStream(player.inventory.mainInventory, player.inventory.armorInventory);
    }

    @Nonnull
    public static Stream<ItemStack> getAllSlotsStream(@Nonnull EntityPlayer player) {
        return Utils.mergeStream(player.inventory.mainInventory, player.inventory.armorInventory, player.inventory.offHandInventory);
    }

    @Nonnull
    public static Stream<ItemStack> getArmorAndOffHandSlotsStream(@Nonnull EntityPlayer player) {
        return Utils.mergeStream(player.inventory.armorInventory, player.inventory.offHandInventory);
    }

    //----------------------------------------------------------------------------------

    @Nonnull
    public static Iterable<ItemStack> getMainSlots(@Nonnull EntityPlayer player) {
        return () -> getMainSlotsStream(player).iterator();
    }

    @Nonnull
    public static Iterable<ItemStack> getArmorSlots(@Nonnull EntityPlayer player) {
        return () -> getArmorSlotsStream(player).iterator();
    }

    @Nonnull
    public static Iterable<ItemStack> getOffHandSlots(@Nonnull EntityPlayer player) {
        return () -> getOffHandSlotsStream(player).iterator();
    }

    @Nonnull
    public static Iterable<ItemStack> getMainAndOffHandSlots(@Nonnull EntityPlayer player) {
        return () -> getMainAndOffHandSlotsStream(player).iterator();
    }

    @Nonnull
    public static Iterable<ItemStack> getMainAndArmorSlots(@Nonnull EntityPlayer player) {
        return () -> getMainAndArmorSlotsStream(player).iterator();
    }

    @Nonnull
    public static Iterable<ItemStack> getAllSlots(@Nonnull EntityPlayer player) {
        return () -> getAllSlotsStream(player).iterator();
    }

    @Nonnull
    public static Iterable<ItemStack> getArmorAndOffHandSlots(@Nonnull EntityPlayer player) {
        return () -> getArmorAndOffHandSlotsStream(player).iterator();
    }

    //----------------------------------------------------------------------------------

    @Nonnull
    public static Iterable<ItemStack> getMainSlots(@Nonnull EntityPlayer player, @Nonnull Predicate<ItemStack> filter) {
        return () -> getMainSlotsStream(player).filter(filter).iterator();
    }

    @Nonnull
    public static Iterable<ItemStack> getArmorSlots(@Nonnull EntityPlayer player, @Nonnull Predicate<ItemStack> filter) {
        return () -> getArmorSlotsStream(player).filter(filter).iterator();
    }

    @Nonnull
    public static Iterable<ItemStack> getOffHandSlots(@Nonnull EntityPlayer player, @Nonnull Predicate<ItemStack> filter) {
        return () -> getOffHandSlotsStream(player).filter(filter).iterator();
    }

    @Nonnull
    public static Iterable<ItemStack> getMainAndOffHandSlots(@Nonnull EntityPlayer player, @Nonnull Predicate<ItemStack> filter) {
        return () -> getMainAndOffHandSlotsStream(player).filter(filter).iterator();
    }

    @Nonnull
    public static Iterable<ItemStack> getMainAndArmorSlots(@Nonnull EntityPlayer player, @Nonnull Predicate<ItemStack> filter) {
        return () -> getMainAndArmorSlotsStream(player).filter(filter).iterator();
    }

    @Nonnull
    public static Iterable<ItemStack> getAllSlots(@Nonnull EntityPlayer player, @Nonnull Predicate<ItemStack> filter) {
        return () -> getAllSlotsStream(player).filter(filter).iterator();
    }

    @Nonnull
    public static Iterable<ItemStack> getArmorAndOffHandSlots(@Nonnull EntityPlayer player, @Nonnull Predicate<ItemStack> filter) {
        return () -> getArmorAndOffHandSlotsStream(player).filter(filter).iterator();
    }
}
