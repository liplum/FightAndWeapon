package net.liplum.events.attack;

import net.liplum.api.weapon.IModifier;
import net.liplum.api.weapon.IWeaponCore;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class WeaponPreAttackEvent extends WeaponAttackBaseEvent {
    public WeaponPreAttackEvent(World world,
                                EntityLivingBase entity,
                                Entity target,
                                IWeaponCore weaponCore,
                                IModifier<?> modifier,
                                ItemStack itemStack,
                                DamageSource damageSource,
                                float finalDamage) {
        super(world, entity, target, weaponCore, modifier, itemStack);
        this.damageSource = damageSource;
        this.damage = finalDamage;
    }
}
