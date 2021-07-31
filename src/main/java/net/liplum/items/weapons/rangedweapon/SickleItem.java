package net.liplum.items.weapons.rangedweapon;

import net.liplum.api.weapon.WeaponBaseItem;
import net.liplum.api.weapon.WeaponCore;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class SickleItem extends WeaponBaseItem {
    @Nonnull
    private final RangedWeaponCore core;

    public SickleItem(@Nonnull RangedWeaponCore weaponCore) {
        super(weaponCore);
        this.core = weaponCore;
    }

    @Override
    public boolean onLeftClickEntity(@Nonnull ItemStack stack, @Nonnull EntityPlayer player, @Nonnull Entity entity) {
        return true;
    }

    @Nonnull
    @Override
    public RangedWeaponCore getCore() {
        return core;
    }
}
