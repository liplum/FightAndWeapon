package net.liplum.api.weapon;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public interface IWeaponSkillPredicate {
    boolean canRelease(@Nonnull World world, EntityPlayer player, @Nonnull ItemStack weapon);
}
