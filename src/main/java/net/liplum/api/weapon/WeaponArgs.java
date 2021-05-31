package net.liplum.api.weapon;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class WeaponArgs<This extends WeaponArgs> {
    private float strength = 0;
    private World world = null;
    private EntityPlayer player = null;
    private ItemStack itemStack = null;
    private EnumHand hand = null;

    public This setStrength(float strength) {
        this.strength = strength;
        return (This) this;
    }

    public float getStrength() {
        return strength;
    }

    public World getWorld() {
        return world;
    }

    public This setWorld(World world) {
        this.world = world;
        return (This) this;
    }

    public EntityPlayer getPlayer() {
        return player;
    }

    public This setPlayer(EntityPlayer player) {
        this.player = player;
        return (This) this;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public This setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
        return (This) this;
    }

    public EnumHand getHand() {
        return hand;
    }

    public This setHand(EnumHand hand) {
        this.hand = hand;
        return (This) this;
    }
}
