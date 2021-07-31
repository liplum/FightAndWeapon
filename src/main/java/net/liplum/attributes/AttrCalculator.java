package net.liplum.attributes;

import net.liplum.api.annotations.LongSupport;
import net.liplum.api.fight.FawArgsGetter;
import net.liplum.api.fight.FawArgsSetter;
import net.liplum.api.weapon.Modifier;
import net.liplum.api.weapon.WeaponBaseItem;
import net.liplum.api.weapon.WeaponCore;
import net.liplum.events.AttributeAccessEvent;
import net.liplum.masteries.IMasteryDetail;
import net.liplum.masteries.MasteryDetail;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@LongSupport
public class AttrCalculator implements IAttrCalculator, FawArgsGetter, FawArgsSetter<AttrCalculator> {
    private WeaponBaseItem weapon;
    @Nullable
    private Modifier modifier;
    @Nullable
    private EntityLivingBase entity;
    @Nullable
    private ItemStack itemStack;

    private boolean postEvent = true;

    private boolean useSpecialValueWhenWeaponBroken = true;

    @LongSupport
    public AttrCalculator() {
    }

    @LongSupport
    public AttrCalculator(WeaponBaseItem weapon) {
        this.weapon = weapon;
    }

    /**
     * Calculate the final value of this attribute in this weapon.<br/>
     * NOTE: All Attributes' access must be via this.
     *
     * @param attribute         the attribute which you want to calculate in this weapon
     * @param weapon            the weapon core which can provide the basic value of this attribute
     * @param modifier          (optional) the modifier of this weapon which can provide the modifier value of this attribute
     * @param player            (optional) the player which can provide the mastery capability(It stands for the attacker or who need access this attribute is not a player when the parameter is null)
     * @param itemStack
     * @param postAccessedEvent Whether it posts the {@link AttributeAccessEvent}. NOTE:Set false when you subscribe this event and call this function again to prevent the recursive attribute access.
     * @return the final value(NOTE:It may be changed by the {@link AttributeAccessEvent}.)
     */
    @Nonnull
    public static FinalAttrValue calcuAttribute(@Nonnull IAttribute attribute,
                                                @Nonnull WeaponBaseItem weapon,
                                                @Nullable Modifier modifier,
                                                @Nullable EntityPlayer player,
                                                @Nullable ItemStack itemStack,
                                                boolean showSpecialValueWhenWeaponBroken, boolean postAccessedEvent) {
        ComputeType computeType = attribute.getComputeType();
        WeaponCore core = weapon.getCore();
        BasicAttrValue baseAttrValue = core.getValue(attribute);
        //Mastery
        AttrDelta masteryValue = null;
        if (computeType.computeMastery) {
            IMasteryDetail mastery = MasteryDetail.create(player);
            masteryValue = mastery.getAttrAmpValue(weapon.getWeaponType(), attribute);
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
            AttributeAccessEvent attributeAccessEvent = new AttributeAccessEvent(attribute, finalAttrValue, weapon, modifier, player, itemStack, showSpecialValueWhenWeaponBroken);
            MinecraftForge.EVENT_BUS.post(attributeAccessEvent);
            finalAttrValue = attributeAccessEvent.getFinalAttrValue();
        }
        return finalAttrValue;
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
    public AttrCalculator weapon(@Nonnull WeaponBaseItem weapon) {
        this.weapon = weapon;
        return this;
    }

    @Nonnull
    @Override
    public AttrCalculator modifier(@Nullable Modifier modifier) {
        this.modifier = modifier;
        return this;
    }

    @Nullable
    @Override
    public EntityLivingBase entity() {
        return entity;
    }

    @Nonnull
    @Override
    public AttrCalculator entity(@Nullable EntityLivingBase entity) {
        this.entity = entity;
        return this;
    }

    @Nullable
    @Override
    public ItemStack itemStack() {
        return itemStack;
    }

    @Override
    @Nonnull
    public AttrCalculator itemStack(@Nullable ItemStack itemStack) {
        this.itemStack = itemStack;
        return this;
    }

    @Override
    public boolean isUseSpecialValueWhenWeaponBroken() {
        return useSpecialValueWhenWeaponBroken;
    }

    @Nonnull
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
        if (weapon == null) {
            throw new IllegalArgumentException("Weapon Core is Null");
        }
        return calcuAttribute(attribute, weapon, modifier, this.player(), itemStack, useSpecialValueWhenWeaponBroken, postEvent);
    }
}
