package net.liplum.api.weapon;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class WeaponArgs<This extends WeaponArgs> {
    private float strength = 0;
    private World world = null;
    private EntityPlayer player = null;
    private ItemStack itemStack = null;
    private IModifier modifier = null;
    private EnumHand hand = null;

    /**
     * @return the modifier or null if this weapon didn't have a modifier.
     */
    @Nullable
    public IModifier getModifier() {
        return modifier;
    }

    @Nonnull
    public This setModifier(@Nullable IModifier modifier) {
        this.modifier = modifier;
        return (This) this;
    }

    @Nonnull
    public This setStrength(float strength) {
        this.strength = strength;
        return (This) this;
    }

    @Nonnull
    public float getStrength() {
        return strength;
    }

    @Nonnull
    public World getWorld() {
        return world;
    }

    @Nonnull
    public This setWorld(@Nonnull World world) {
        this.world = world;
        return (This) this;
    }

    @Nonnull
    public EntityPlayer getPlayer() {
        return player;
    }

    @Nonnull
    public This setPlayer(@Nonnull EntityPlayer player) {
        this.player = player;
        return (This) this;
    }

    @Nonnull
    public ItemStack getItemStack() {
        return itemStack;
    }

    @Nonnull
    public This setItemStack(@Nonnull ItemStack itemStack) {
        this.itemStack = itemStack;
        return (This) this;
    }

    @Nonnull
    public EnumHand getHand() {
        return hand;
    }

    @Nonnull
    public This setHand(@Nonnull EnumHand hand) {
        this.hand = hand;
        return (This) this;
    }
}
