package net.liplum.events;

import net.liplum.api.weapon.Modifier;
import net.liplum.api.weapon.WeaponCore;
import net.liplum.attributes.FinalAttrValue;
import net.liplum.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.Event;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AttributeAccessEvent extends Event {
    @Nonnull
    protected final IAttribute attribute;
    @Nonnull
    protected final WeaponCore weaponCore;
    @Nullable
    protected final Modifier modifier;
    @Nullable
    protected final EntityPlayer player;
    @Nullable
    protected final ItemStack itemStack;
    @Nonnull
    protected FinalAttrValue finalAttrValue;

    private final boolean useSpecialValueWhenWeaponBroken;


    public AttributeAccessEvent(@Nonnull IAttribute attribute, @Nonnull FinalAttrValue finalAttrValue, @Nonnull WeaponCore weaponCore, @Nullable Modifier modifier, @Nullable EntityPlayer player, @Nullable ItemStack itemStack, boolean useSpecialValueWhenWeaponBroken) {
        this.attribute = attribute;
        this.finalAttrValue = finalAttrValue;
        this.weaponCore = weaponCore;
        this.modifier = modifier;
        this.player = player;
        this.itemStack = itemStack;
        this.useSpecialValueWhenWeaponBroken = useSpecialValueWhenWeaponBroken;
    }

    @Nonnull
    public IAttribute getAttribute() {
        return attribute;
    }

    @Nonnull
    public FinalAttrValue getFinalAttrValue() {
        return finalAttrValue;
    }

    public void setFinalAttrValue(@Nonnull FinalAttrValue finalAttrValue) {
        this.finalAttrValue = finalAttrValue;
    }

    @Nonnull
    public WeaponCore getWeaponCore() {
        return weaponCore;
    }

    @Nullable
    public Modifier getModifier() {
        return modifier;
    }

    @Nullable
    public EntityPlayer getPlayer() {
        return player;
    }

    @Nullable
    public ItemStack getItemStack() {
        return itemStack;
    }

    public boolean isUseSpecialValueWhenWeaponBroken() {
        return useSpecialValueWhenWeaponBroken;
    }
}
