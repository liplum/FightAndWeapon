package net.liplum.events;

import net.liplum.api.fight.FawArgsGetter;
import net.liplum.api.weapon.Modifier;
import net.liplum.api.weapon.WeaponBaseItem;
import net.liplum.attributes.FinalAttrValue;
import net.liplum.attributes.IAttribute;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.Event;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class AttributeAccessEvent extends Event implements FawArgsGetter {
    @NotNull
    protected final IAttribute attribute;
    @NotNull
    protected final WeaponBaseItem weapon;
    @Nullable
    protected final Modifier modifier;
    @Nullable
    protected final EntityLivingBase entity;
    @Nullable
    protected final ItemStack itemStack;
    private final boolean useSpecialValueWhenWeaponBroken;
    @NotNull
    protected FinalAttrValue finalAttrValue;


    public AttributeAccessEvent(@NotNull IAttribute attribute,
                                @NotNull FinalAttrValue finalAttrValue,
                                @NotNull WeaponBaseItem weapon,
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

    @NotNull
    public IAttribute getAttribute() {
        return attribute;
    }

    @NotNull
    public FinalAttrValue getFinalAttrValue() {
        return finalAttrValue;
    }

    public void setFinalAttrValue(@NotNull FinalAttrValue finalAttrValue) {
        this.finalAttrValue = finalAttrValue;
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

    public boolean isUseSpecialValueWhenWeaponBroken() {
        return useSpecialValueWhenWeaponBroken;
    }
}
