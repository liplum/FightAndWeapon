package net.liplum.events.weapon;

import net.liplum.api.weapon.Modifier;
import net.liplum.api.weapon.WeaponBaseItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.Event;

public class FawWeaponLeftClickEvent extends Event {
    protected final EntityPlayer player;
    protected final WeaponBaseItem weapon;
    protected final Modifier modifier;
    protected final ItemStack itemStack;

    public FawWeaponLeftClickEvent(EntityPlayer player, WeaponBaseItem weapon, Modifier modifier, ItemStack itemStack) {
        this.player = player;
        this.weapon = weapon;
        this.modifier = modifier;
        this.itemStack = itemStack;
    }

    public World getWorld() {
        return player.world;
    }

    public EntityPlayer getPlayer() {
        return player;
    }

    public WeaponBaseItem getWeapon() {
        return weapon;
    }

    public Modifier getModifier() {
        return modifier;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
}
