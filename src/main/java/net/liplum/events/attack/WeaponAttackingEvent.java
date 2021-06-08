package net.liplum.events.attack;

import net.liplum.api.weapon.IModifier;
import net.liplum.api.weapon.IWeaponCore;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Tuple;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.LinkedList;
import java.util.List;

public class WeaponAttackingEvent extends WeaponAttackBaseEvent {
    private final List<Tuple<DamageSource, Float>> extraDamages = new LinkedList<>();

    public WeaponAttackingEvent(World world, EntityLivingBase attacker, Entity target, IWeaponCore weaponCore, IModifier<?> modifier, ItemStack itemStack) {
        super(world, attacker, target, weaponCore, modifier, itemStack);
    }

    @Nonnull
    public List<Tuple<DamageSource, Float>> getExtraDamages() {
        return extraDamages;
    }
}
