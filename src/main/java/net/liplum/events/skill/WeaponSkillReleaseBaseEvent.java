package net.liplum.events.skill;

import net.liplum.api.weapon.Modifier;
import net.liplum.api.weapon.WeaponCore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.Event;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class WeaponSkillReleaseBaseEvent extends Event {
    protected final World world;
    protected final EntityPlayer player;
    protected final WeaponCore weaponCore;
    protected final Modifier<?> modifier;
    protected final ItemStack itemStack;
    protected final EnumHand hand;

    public WeaponSkillReleaseBaseEvent(@Nonnull World world, @Nonnull EntityPlayer player,
                                       @Nonnull WeaponCore weaponCore, @Nullable Modifier modifier,
                                       @Nonnull ItemStack itemStack, @Nonnull EnumHand hand) {
        this.world = world;
        this.player = player;
        this.weaponCore = weaponCore;
        this.modifier = modifier;
        this.itemStack = itemStack;
        this.hand = hand;
    }

    public World getWorld() {
        return world;
    }

    public EntityPlayer getPlayer() {
        return player;
    }

    public WeaponCore getWeaponCore() {
        return weaponCore;
    }

    public Modifier<?> getModifier() {
        return modifier;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public EnumHand getHand() {
        return hand;
    }
}
