package net.liplum.events.skill;

import net.liplum.api.weapon.Modifier;
import net.liplum.api.weapon.WeaponCore;
import net.liplum.attributes.Attribute;
import net.liplum.attributes.FinalAttrValue;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.Event;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AttributeAccessedEvent extends Event {
    @Nonnull
    protected final Attribute attribute;
    @Nonnull
    protected final WeaponCore weaponCore;
    @Nullable
    protected final Modifier<?> modifier;
    @Nullable
    protected final EntityPlayer player;
    @Nonnull
    protected FinalAttrValue finalAttrValue;

    public AttributeAccessedEvent(@Nonnull Attribute attribute, @Nonnull FinalAttrValue finalAttrValue, @Nonnull WeaponCore weaponCore, @Nullable Modifier<?> modifier, @Nullable EntityPlayer player) {
        this.attribute = attribute;
        this.finalAttrValue = finalAttrValue;
        this.weaponCore = weaponCore;
        this.modifier = modifier;
        this.player = player;
    }

    @Nonnull
    public Attribute getAttribute() {
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
    public Modifier<?> getModifier() {
        return modifier;
    }

    @Nullable
    public EntityPlayer getPlayer() {
        return player;
    }
}
