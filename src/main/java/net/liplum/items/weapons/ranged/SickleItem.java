package net.liplum.items.weapons.ranged;

import net.liplum.api.weapon.WeaponBaseItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class SickleItem extends WeaponBaseItem {
    @Nonnull
    private final RangedCore core;

    public SickleItem(@Nonnull RangedCore weaponCore) {
        super(weaponCore);
        this.core = weaponCore;
    }

    @Override
    public boolean onLeftClickEntity(@Nonnull ItemStack stack, @Nonnull EntityPlayer player, @Nonnull Entity entity) {
        return true;
    }

    @Nonnull
    @Override
    public RangedCore getConcreteCore() {
        return core;
    }
}
