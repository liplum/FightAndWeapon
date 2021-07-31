package net.liplum.items.weapons.magickwand;

import net.liplum.api.weapon.WeaponBaseItem;
import net.liplum.api.weapon.WeaponCore;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class MagickWandItem extends WeaponBaseItem {
    @Nonnull
    private final MagickWandCore core;

    public MagickWandItem(@Nonnull MagickWandCore weaponCore) {
        super(weaponCore);
        this.core = weaponCore;
    }

    @Override
    public boolean onLeftClickEntity(@Nonnull ItemStack stack, @Nonnull EntityPlayer player, @Nonnull Entity entity) {
        return true;
    }

    @Nonnull
    @Override
    public MagickWandCore getConcreteCore() {
        return core;
    }

}
