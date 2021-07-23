package net.liplum.attributes;

import net.liplum.api.weapon.Modifier;
import net.liplum.api.weapon.WeaponCore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class FixedAttrCalculator implements IAttrCalculator {
    @Nonnull
    private final WeaponCore weaponCore;
    @Nullable
    private final Modifier modifier;
    @Nullable
    private final EntityPlayer player;
    @Nullable
    private final ItemStack itemStack;

    private final boolean postEvent;

    private final boolean useSpecialValueWhenWeaponBroken;

    public FixedAttrCalculator(@Nonnull WeaponCore core, @Nullable Modifier modifier, @Nullable EntityPlayer player, @Nullable ItemStack itemStack, boolean postEvent, boolean useSpecialValueWhenWeaponBroken) {
        this.weaponCore = core;
        this.modifier = modifier;
        this.itemStack = itemStack;
        this.player = player;
        this.postEvent = postEvent;
        this.useSpecialValueWhenWeaponBroken = useSpecialValueWhenWeaponBroken;
    }

    public FixedAttrCalculator(@Nonnull WeaponCore core, boolean postEvent, boolean useSpecialValueWhenWeaponBroken) {
        this(core, null, null, null, postEvent, useSpecialValueWhenWeaponBroken);
    }

    public FixedAttrCalculator(@Nonnull WeaponCore core, @Nullable Modifier modifier, @Nullable ItemStack itemStack, boolean postEvent, boolean useSpecialValueWhenWeaponBroken) {
        this(core, modifier, null, itemStack, postEvent, useSpecialValueWhenWeaponBroken);
    }

    public FixedAttrCalculator(@Nonnull WeaponCore core, @Nullable EntityPlayer player, boolean postEvent, boolean useSpecialValueWhenWeaponBroken) {
        this(core, null, player, null, postEvent, useSpecialValueWhenWeaponBroken);
    }

    @Nonnull
    @Override
    public WeaponCore getWeaponCore() {
        return weaponCore;
    }

    @Nullable
    @Override
    public Modifier getModifier() {
        return modifier;
    }

    @Nullable
    @Override
    public EntityPlayer getPlayer() {
        return player;
    }

    @Nullable
    @Override
    public ItemStack getItemStack() {
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

    @Nonnull
    @Override
    public FinalAttrValue calcu(@Nonnull IAttribute attribute) {
        return AttrCalculator.calcuAttribute(attribute, weaponCore, modifier, player, itemStack, true, true);
    }
}
