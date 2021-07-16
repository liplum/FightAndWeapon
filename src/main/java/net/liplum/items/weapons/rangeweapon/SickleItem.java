package net.liplum.items.weapons.rangeweapon;

import net.liplum.api.weapon.WeaponBaseItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class SickleItem extends WeaponBaseItem {
    private RangedWeaponCore core;

    public SickleItem(@Nonnull RangedWeaponCore weaponCore) {
        super(weaponCore);
        this.core = weaponCore;
    }

    @Override
    public boolean onLeftClickEntity(@Nonnull ItemStack stack, @Nonnull EntityPlayer player, @Nonnull Entity entity) {
        return true;
    }

    @Override
    public boolean needSpecialAttackReachJudgment() {
        return false;
    }
}
