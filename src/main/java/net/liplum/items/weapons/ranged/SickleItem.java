package net.liplum.items.weapons.ranged;

import net.liplum.api.weapon.WeaponBaseItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class SickleItem extends WeaponBaseItem {
    @NotNull
    private final RangedCore core;

    public SickleItem(@NotNull RangedCore weaponCore) {
        super(weaponCore);
        this.core = weaponCore;
    }

    @Override
    public boolean onLeftClickEntity(@NotNull ItemStack stack, @NotNull EntityPlayer player, @NotNull Entity entity) {
        return true;
    }

    @NotNull
    @Override
    public RangedCore getConcreteCore() {
        return core;
    }
}
