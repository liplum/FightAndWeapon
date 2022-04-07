package net.liplum.items.weapons.sword;

import net.liplum.api.weapon.WeaponBaseItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class SwordItem extends WeaponBaseItem {
    @NotNull
    private final SwordCore core;

    public SwordItem(@NotNull SwordCore weaponCore) {
        super(weaponCore);
        this.core = weaponCore;
    }

    @Override
    public boolean onLeftClickEntity(@NotNull ItemStack stack, @NotNull EntityPlayer player, @NotNull Entity entity) {
        return true;
    }

    @NotNull
    @Override
    public SwordCore getConcreteCore() {
        return core;
    }
}
