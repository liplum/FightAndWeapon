package net.liplum.events.weapon;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.Event;

import javax.annotation.Nonnull;

public class FoundAmmoEvent extends Event {

    @Nonnull
    private ItemStack ammo;

    public boolean hasAmmo(){
        return !ammo.isEmpty();
    }



}
