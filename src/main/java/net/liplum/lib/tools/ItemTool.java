package net.liplum.lib.tools;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemTool {
    /**
     * Heats the weapon if player is in survival mode.
     *
     * @param player       a player who has the hot weapon
     * @param item         a item which the player holds and will be heated
     * @param coolDownTime how much time need to cool down
     * @return if player is in survival mode, it'll return ture and heat the weapon, otherwise, return false.
     */
    public static boolean HeatWeaponIfSurvival(EntityPlayer player, Item item, int coolDownTime) {
        if (player.isCreative()) {
            return false;
        }
        player.getCooldownTracker().setCooldown(item, coolDownTime);
        return true;
    }

    /**
     *
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
}
