package net.liplum.attributes;

import net.liplum.api.annotations.LongSupport;
import net.liplum.api.weapon.Modifier;
import net.liplum.api.weapon.WeaponCore;
import net.liplum.capabilities.MasteryCapability;
import net.liplum.events.AttributeAccessEvent;
import net.liplum.lib.utils.MasteryUtil;
import net.liplum.registeies.CapabilityRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@LongSupport
public class AttrCalculator implements IAttrCalculator{
    private WeaponCore weaponCore;
    @Nullable
    private Modifier modifier;
    @Nullable
    private EntityPlayer player;
    @Nullable
    private ItemStack itemStack;

    private boolean postEvent = true;

    private boolean useSpecialValueWhenWeaponBroken = true;

    public AttrCalculator() {
    }

    public AttrCalculator(WeaponCore core) {
        this.weaponCore = core;
    }

    /**
     * Calculate the final value of this attribute in this weapon.<br/>
     * NOTE: All Attributes' access must be via this.
     *
     * @param attribute         the attribute which you want to calculate in this weapon
     * @param core              the weapon core which can provide the basic value of this attribute
     * @param modifier          (optional) the modifier of this weapon which can provide the modifier value of this attribute
     * @param player            (optional) the player which can provide the mastery capability(It stands for the attacker or who need access this attribute is not a player when the parameter is null)
     * @param itemStack
     * @param postAccessedEvent Whether it posts the {@link AttributeAccessEvent}. NOTE:Set false when you subscribe this event and call this function again to prevent the recursive attribute access.
     * @return the final value(NOTE:It may be changed by the {@link AttributeAccessEvent}.)
     */
    @Nonnull
    public static FinalAttrValue calcuAttribute(@Nonnull IAttribute attribute, @Nonnull WeaponCore core, @Nullable Modifier modifier, @Nullable EntityPlayer player, @Nullable ItemStack itemStack, boolean showSpecialValueWhenWeaponBroken, boolean postAccessedEvent) {
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
            AttributeAccessEvent attributeAccessEvent = new AttributeAccessEvent(attribute, finalAttrValue, core, modifier, player, itemStack, showSpecialValueWhenWeaponBroken);
            MinecraftForge.EVENT_BUS.post(attributeAccessEvent);
            finalAttrValue = attributeAccessEvent.getFinalAttrValue();
        }
        return finalAttrValue;
    }

    @Nonnull
    @Override
    public WeaponCore getWeaponCore() {
        return weaponCore;
    }

    @Nonnull
    public AttrCalculator setWeaponCore(@Nonnull WeaponCore weaponCore) {
        this.weaponCore = weaponCore;
        return this;
    }

    @Nullable
    @Override
    public Modifier getModifier() {
        return modifier;
    }

    @Nonnull
    public AttrCalculator setModifier(@Nullable Modifier modifier) {
        this.modifier = modifier;
        return this;
    }

    @Nullable
    @Override
    public EntityPlayer getPlayer() {
        return player;
    }

    @Nonnull
    public AttrCalculator setPlayer(@Nullable EntityPlayer player) {
        this.player = player;
        return this;
    }

    @Nullable
    @Override
    public ItemStack getItemStack() {
        return itemStack;
    }

    public AttrCalculator setItemStack(@Nullable ItemStack itemStack) {
        this.itemStack = itemStack;
        return this;
    }

    @Override
    public boolean isUseSpecialValueWhenWeaponBroken() {
        return useSpecialValueWhenWeaponBroken;
    }

    public AttrCalculator setUseSpecialValueWhenWeaponBroken(boolean useSpecialValueWhenWeaponBroken) {
        this.useSpecialValueWhenWeaponBroken = useSpecialValueWhenWeaponBroken;
        return this;
    }

    /**
     * @return Whether this attribute access will post the {@link AttributeAccessEvent}
     */
    @Override
    public boolean isPostEvent() {
        return postEvent;
    }

    @Nonnull
    public AttrCalculator setNotPostEvent() {
        this.postEvent = false;
        return this;
    }

    @Nonnull
    @Override
    public FinalAttrValue calcu(@Nonnull IAttribute attribute) {
        if (weaponCore == null) {
            throw new IllegalArgumentException("Weapon Core is Null");
        }
        return calcuAttribute(attribute, weaponCore, modifier, player, itemStack, useSpecialValueWhenWeaponBroken, postEvent);
    }
}
