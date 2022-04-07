package net.liplum.api.weapon;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public interface IWeaponSkillPredicate {
    boolean canRelease(@NotNull World world, EntityPlayer player, @NotNull ItemStack weapon);

    default IWeaponSkillPredicate and(IWeaponSkillPredicate other) {
        return (world, player, weapon) ->
                this.canRelease(world, player, weapon) && other.canRelease(world, player, weapon);
    }

    default IWeaponSkillPredicate or(IWeaponSkillPredicate other) {
        return (world, player, weapon) ->
                this.canRelease(world, player, weapon) || other.canRelease(world, player, weapon);
    }

    default IWeaponSkillPredicate xor(IWeaponSkillPredicate other) {
        return (world, player, weapon) ->
                this.canRelease(world, player, weapon) ^ other.canRelease(world, player, weapon);
    }

    default IWeaponSkillPredicate not() {
        return (world, player, weapon) ->
                !this.canRelease(world, player, weapon);
    }
}
