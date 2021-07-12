package net.liplum.attributes;

import net.liplum.api.weapon.Modifier;
import net.liplum.api.weapon.WeaponCore;
import net.liplum.capabilities.MasteryCapability;
import net.liplum.events.AttributeAccessedEvent;
import net.liplum.lib.utils.MasteryUtil;
import net.liplum.registeies.CapabilityRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AttrCalculator {
    private WeaponCore weaponCore;
    @Nullable
    private Modifier modifier;
    @Nullable
    private EntityPlayer player;
    private boolean postEvent = true;

    public AttrCalculator() {
    }

    public AttrCalculator(WeaponCore core) {
        this.weaponCore = core;
    }

    @Nonnull
    public AttrCalculator setWeaponCore(@Nonnull WeaponCore weaponCore) {
        this.weaponCore = weaponCore;
        return this;
    }

    @Nonnull
    public WeaponCore getWeaponCore() {
        return weaponCore;
    }

    @Nullable
    public Modifier getModifier() {
        return modifier;
    }

    @Nonnull
    public AttrCalculator setModifier(@Nullable Modifier modifier) {
        this.modifier = modifier;
        return this;
    }

    @Nullable
    public EntityPlayer getPlayer() {
        return player;
    }

    @Nonnull
    public AttrCalculator setPlayer(@Nullable EntityPlayer player) {
        this.player = player;
        return this;
    }

    /**
     * @return Whether this attribute access will post the {@link AttributeAccessedEvent}
     */
    public boolean isPostEvent() {
        return postEvent;
    }

    @Nonnull
    public AttrCalculator setNotPostEvent() {
        this.postEvent = false;
        return this;
    }

    @Nonnull
    public FinalAttrValue calcu(@Nonnull Attribute attribute) {
        if (weaponCore == null) {
            throw new IllegalArgumentException("Weapon Core is Null");
        }
        return calcuAttribute(attribute, weaponCore, modifier, player, postEvent);
    }

    /**
     * Calculate the final value of this attribute in this weapon.<br/>
     * NOTE: All Attributes' access must be via this.
     *
     * @param attribute         the attribute which you want to calculate in this weapon
     * @param core              the weapon core which can provide the basic value of this attribute
     * @param modifier          (optional) the modifier of this weapon which can provide the modifier value of this attribute
     * @param player            (optional) the player which can provide the mastery capability(It stands for the attacker or who need access this attribute is not a player when the parameter is null)
     * @param postAccessedEvent Whether it posts the {@link AttributeAccessedEvent}. NOTE:Set false when you subscribe this event and call this function again to prevent the recursive attribute access.
     * @return the final value(NOTE:It may be changed by the {@link AttributeAccessedEvent}.)
     */
    @Nonnull
    private static FinalAttrValue calcuAttribute(@Nonnull Attribute attribute, @Nonnull WeaponCore core, @Nullable Modifier modifier, @Nullable EntityPlayer player, boolean postAccessedEvent) {
        ComputeType computeType = attribute.getComputeType();
        BasicAttrValue baseAttrValue = core.getValue(attribute);
        //Mastery
        MasteryCapability mastery = null;
        AttrDelta masteryValue = null;
        if (computeType.computeMastery) {
            if (player != null) {
                mastery = player.getCapability(CapabilityRegistry.Mastery_Capability, null);
            }
            if (mastery != null) {
                masteryValue = MasteryUtil.getAttributeValue(mastery, core.getWeaponType(), attribute);
            }
        }
        //Modifier
        AttrModifier attrModifier = null;
        if (computeType.computeModifier) {
            if (modifier != null) {
                attrModifier = modifier.getValue(attribute);
            }
        }

        FinalAttrValue finalAttrValue = attribute.compute(baseAttrValue, attrModifier, masteryValue);
        if (postAccessedEvent) {
            AttributeAccessedEvent attributeAccessedEvent = new AttributeAccessedEvent(attribute, finalAttrValue, core, modifier, player);
            MinecraftForge.EVENT_BUS.post(attributeAccessedEvent);
            finalAttrValue = attributeAccessedEvent.getFinalAttrValue();
        }
        return finalAttrValue;
    }
}
