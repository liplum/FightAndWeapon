package net.liplum.events.skill;

import net.liplum.api.weapon.Modifier;
import net.liplum.api.weapon.WeaponBaseItem;
import net.liplum.api.weapon.WeaponCore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * If you cancel it then the player can't release the skill this time.
 */

@Cancelable
public class WeaponSkillPreReleaseEvent extends WeaponSkillReleaseBaseEvent {

    public WeaponSkillPreReleaseEvent(@Nonnull World world, @Nonnull EntityPlayer player, @Nonnull WeaponBaseItem weapon, @Nullable Modifier modifier, @Nonnull ItemStack itemStack, @Nonnull EnumHand hand) {
        super(world, player, weapon, modifier, itemStack, hand);
    }
}
