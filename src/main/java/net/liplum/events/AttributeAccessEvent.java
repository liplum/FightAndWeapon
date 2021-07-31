package net.liplum.events;

import net.liplum.api.fight.FawArgsGetter;
import net.liplum.api.weapon.Modifier;
import net.liplum.api.weapon.WeaponBaseItem;
import net.liplum.attributes.FinalAttrValue;
import net.liplum.attributes.IAttribute;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.Event;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AttributeAccessEvent extends Event implements FawArgsGetter {
    @Nonnull
    protected final IAttribute attribute;
    @Nonnull
    protected final WeaponBaseItem weapon;
    @Nullable
    protected final Modifier modifier;
    @Nullable
    protected final EntityLivingBase entity;
    @Nullable
    protected final ItemStack itemStack;
    private final boolean useSpecialValueWhenWeaponBroken;
    @Nonnull
    protected FinalAttrValue finalAttrValue;


    public AttributeAccessEvent(@Nonnull IAttribute attribute,
                                @Nonnull FinalAttrValue finalAttrValue,
                                @Nonnull WeaponBaseItem weapon,
                                @Nullable Modifier modifier,
                                @Nullable EntityLivingBase entity,
                                @Nullable ItemStack itemStack,
                                boolean useSpecialValueWhenWeaponBroken) {
        this.attribute = attribute;
        this.finalAttrValue = finalAttrValue;
        this.weapon = weapon;
        this.modifier = modifier;
        this.entity = entity;
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

    public boolean isUseSpecialValueWhenWeaponBroken() {
        return useSpecialValueWhenWeaponBroken;
    }
}
