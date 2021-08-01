package net.liplum.lib;

import net.liplum.Vanilla;
import net.liplum.api.annotations.Developing;
import net.liplum.api.fight.FawArgsGetter;
import net.liplum.api.weapon.Modifier;
import net.liplum.api.weapon.WeaponBaseItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Developing
public class FawDamage extends EntityDamageSource implements FawArgsGetter {
    @Nonnull
    private final WeaponBaseItem weapon;
    @Nonnull
    private final EntityLivingBase attacker;
    @Nullable
    private final Modifier modifier;
    @Nonnull
    private final ItemStack itemStack;

    private FawDamage(String damageType, @Nonnull EntityLivingBase attacker, @Nonnull WeaponBaseItem weapon, @Nullable Modifier modifier, @Nonnull ItemStack itemStack) {
        super(damageType, attacker);
        this.attacker = attacker;
        this.weapon = weapon;
        this.itemStack = itemStack;
        this.modifier = modifier;
    }

    @Nonnull
    public static FawDamage byPlayer(@Nonnull EntityPlayer playerAttacker, @Nonnull WeaponBaseItem weapon, @Nullable Modifier modifier, @Nonnull ItemStack itemStack) {
        return new FawDamage(Vanilla.DamageType.Player, playerAttacker, weapon, modifier, itemStack);
    }

    @Nonnull
    public static FawDamage byMob(@Nonnull EntityLivingBase entityAttacker, @Nonnull WeaponBaseItem weapon, @Nullable Modifier modifier, @Nonnull ItemStack itemStack) {
        return new FawDamage(Vanilla.DamageType.Mob, entityAttacker, weapon, modifier, itemStack);
    }

    @Nonnull
    @Override
    public WeaponBaseItem weapon() {
        return weapon;
    }

    @Nullable
    @Override
    public Modifier modifier() {
        return modifier;
    }

    @Nonnull
    @Override
    public ItemStack itemStack() {
        return itemStack;
    }

    @Nonnull
    @Override
    public EntityLivingBase entity() {
        return attacker;
    }

    @Nonnull
    public EntityLivingBase attacker() {
        return attacker;
    }
}
