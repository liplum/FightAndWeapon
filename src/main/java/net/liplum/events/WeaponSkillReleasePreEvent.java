package net.liplum.events;

import net.liplum.api.weapon.IModifier;
import net.liplum.api.weapon.IWeaponCore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * If you cancel it then the player can't release the skill this time.
 */
@Cancelable
public class WeaponSkillReleasePreEvent extends Event {
    private final World world;
    private final EntityPlayer player;
    private final IWeaponCore weaponCore;
    private final IModifier modifier;
    private final ItemStack itemStack;
    private final EnumHand hand;

    public WeaponSkillReleasePreEvent(@Nonnull World world, @Nonnull EntityPlayer player,
                                      @Nonnull IWeaponCore weaponCore, @Nullable IModifier modifier,
                                      @Nonnull ItemStack itemStack, @Nonnull EnumHand hand) {
        this.world = world;
        this.player = player;
        this.weaponCore = weaponCore;
        this.modifier = modifier;
        this.itemStack = itemStack;
        this.hand = hand;
    }
}
