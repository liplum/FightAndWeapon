package net.liplum.items.weapons.sword;

import net.liplum.api.weapon.WeaponBaseItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

import javax.annotation.Nonnull;

public class SwordItem extends WeaponBaseItem {
    @Nonnull
    private final SwordCore core;

    public SwordItem(@Nonnull SwordCore weaponCore) {
        super(weaponCore);
        this.core = weaponCore;
    }

    @Override
    public boolean onLeftClickEntity(@Nonnull ItemStack stack, @Nonnull EntityPlayer player, @Nonnull Entity entity) {
        return true;
    }
}
