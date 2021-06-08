package net.liplum.events.attack;

import net.liplum.api.weapon.IModifier;
import net.liplum.api.weapon.IWeaponCore;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class WeaponPostAttackedEvent extends WeaponAttackBaseEvent {
    private boolean hitSuccessfully;

    public WeaponPostAttackedEvent(World world,
                                   EntityLivingBase entity,
                                   Entity target,
                                   IWeaponCore weaponCore,
                                   IModifier<?> modifier,
                                   ItemStack itemStack,
                                   boolean hitSuccessfully) {
        super(world, entity, target, weaponCore, modifier, itemStack);
        this.hitSuccessfully = hitSuccessfully;
    }

    public boolean isHitSuccessfully() {
        return hitSuccessfully;
    }
}
