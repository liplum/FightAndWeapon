package net.liplum.events.skill;

import net.liplum.api.weapon.Modifier;
import net.liplum.api.weapon.WeaponCore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class WeaponSkillPostReleasedEvent extends WeaponSkillReleaseBaseEvent {

    public WeaponSkillPostReleasedEvent(@Nonnull World world, @Nonnull EntityPlayer player, @Nonnull WeaponCore weaponCore, @Nullable Modifier modifier, @Nonnull ItemStack itemStack, @Nonnull EnumHand hand) {
        super(world, player, weaponCore, modifier, itemStack, hand);
    }
}
