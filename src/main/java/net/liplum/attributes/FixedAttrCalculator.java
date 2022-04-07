package net.liplum.attributes;

import net.liplum.api.annotations.LongSupport;
import net.liplum.api.weapon.Modifier;
import net.liplum.api.weapon.WeaponBaseItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

@LongSupport
public class FixedAttrCalculator implements IAttrCalculator {
    @NotNull
    private final WeaponBaseItem weapon;
    @Nullable
    private final Modifier modifier;
    @Nullable
    private final EntityLivingBase entity;
    @Nullable
    private final ItemStack itemStack;

    private final boolean postEvent;

    private final boolean useSpecialValueWhenWeaponBroken;

    @LongSupport
    public FixedAttrCalculator(@NotNull WeaponBaseItem weapon, @Nullable Modifier modifier, @Nullable EntityLivingBase entity, @Nullable ItemStack itemStack, boolean postEvent, boolean useSpecialValueWhenWeaponBroken) {
        this.weapon = weapon;
        this.modifier = modifier;
        this.itemStack = itemStack;
        this.entity = entity;
        this.postEvent = postEvent;
        this.useSpecialValueWhenWeaponBroken = useSpecialValueWhenWeaponBroken;
    }

    @LongSupport
    public FixedAttrCalculator(@NotNull WeaponBaseItem weapon, boolean postEvent, boolean useSpecialValueWhenWeaponBroken) {
        this(weapon, null, null, null, postEvent, useSpecialValueWhenWeaponBroken);
    }

    @LongSupport
    public FixedAttrCalculator(@NotNull WeaponBaseItem weapon, @Nullable Modifier modifier, @Nullable ItemStack itemStack, boolean postEvent, boolean useSpecialValueWhenWeaponBroken) {
        this(weapon, modifier, null, itemStack, postEvent, useSpecialValueWhenWeaponBroken);
    }

    @LongSupport
    public FixedAttrCalculator(@NotNull WeaponBaseItem weapon, @Nullable EntityPlayer player, boolean postEvent, boolean useSpecialValueWhenWeaponBroken) {
        this(weapon, null, player, null, postEvent, useSpecialValueWhenWeaponBroken);
    }

    @NotNull
    @Override
    public WeaponBaseItem weapon() {
        return weapon;
    }

    @Nullable
    @Override
    public Modifier modifier() {
        return modifier;
    }

    @Nullable
    @Override
    public EntityLivingBase entity() {
        return entity;
    }

    @Nullable
    @Override
    public ItemStack itemStack() {
        return itemStack;
    }

    @Override
    public boolean isUseSpecialValueWhenWeaponBroken() {
        return useSpecialValueWhenWeaponBroken;
    }

    @Override
    public boolean isPostEvent() {
        return postEvent;
    }

    @NotNull
    @Override
    public FinalAttrValue calcu(@NotNull IAttribute attribute) {
        return AttrCalculator.calcuAttribute(attribute, weapon, modifier, player(), itemStack, true, true);
    }
}
