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
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Developing
public class FawDamage extends EntityDamageSource implements FawArgsGetter {
    @NotNull
    private final WeaponBaseItem weapon;
    @NotNull
    private final EntityLivingBase attacker;
    @Nullable
    private final Modifier modifier;
    @NotNull
    private final ItemStack itemStack;

    private FawDamage(String damageType, @NotNull EntityLivingBase attacker, @NotNull WeaponBaseItem weapon, @Nullable Modifier modifier, @NotNull ItemStack itemStack) {
        super(damageType, attacker);
        this.attacker = attacker;
        this.weapon = weapon;
        this.itemStack = itemStack;
        this.modifier = modifier;
    }

    @NotNull
    public static FawDamage byPlayer(@NotNull EntityPlayer playerAttacker, @NotNull WeaponBaseItem weapon, @Nullable Modifier modifier, @NotNull ItemStack itemStack) {
        return new FawDamage(Vanilla.DamageType.Player, playerAttacker, weapon, modifier, itemStack);
    }

    @NotNull
    public static FawDamage byMob(@NotNull EntityLivingBase entityAttacker, @NotNull WeaponBaseItem weapon, @Nullable Modifier modifier, @NotNull ItemStack itemStack) {
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

    @NotNull
    @Override
    public ItemStack itemStack() {
        return itemStack;
    }

    @NotNull
    @Override
    public EntityLivingBase entity() {
        return attacker;
    }

    @NotNull
    public EntityLivingBase attacker() {
        return attacker;
    }
}
