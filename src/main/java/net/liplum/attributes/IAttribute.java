package net.liplum.attributes;

import net.liplum.api.annotations.Developing;
import net.liplum.api.annotations.LongSupport;
import net.liplum.api.annotations.Require;
import net.liplum.masteries.AttrAmp;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@LongSupport
public interface IAttribute {
    @Nonnull
    @LongSupport
    String getRegisterName();

    @Require(func = "isShownInTooltip", is = "true")
    @LongSupport
    boolean needMoreDetailsToShown();

    @Require(func = "isShownInTooltip", is = "true")
    @LongSupport
    boolean isStripTrailingZero();

    @Require(func = "isShownInTooltip", is = "true")
    @LongSupport
    boolean hasUnit();

    @LongSupport
    boolean isShownInTooltip();

    /**
     * Smaller number means front<br/>
     * Larger number means later<br/>
     * O is default.
     *
     * @return the priority of display
     */
    @Require(func = "isShownInTooltip", is = "true")
    @LongSupport
    default int getDisplayPriority() {
        return 0;
    }

    @Require(func = "isShownInTooltip", is = "true")
    @LongSupport
    default int getTooltipShownIntValue(Number input) {
        return getTooltipShownValue(input).intValue();
    }

    @Require(func = "isShownInTooltip", is = "true")
    @LongSupport
    default float getTooltipShownFloatValue(Number input) {
        return getTooltipShownValue(input).floatValue();
    }

    @Nonnull
    @Require(func = "isShownInTooltip", is = "true")
    @LongSupport
    Number getTooltipShownValue(Number input);

    @Nonnull
    @Require(func = "isShownInTooltip", is = "true")
    @LongSupport
    default Number getTooltipShownValue(FinalAttrValue input) {
        return getTooltipShownValue(input.getNumber());
    }

    @Nullable
    @Require(func = "isShownInTooltip", is = "true")
    @LongSupport
    String getUnit();

    @Nullable
    @Require(func = "isShownInTooltip", is = "true")
    @LongSupport
    String getFormat();

    @Nonnull
    @Require(func = "isShownInTooltip", is = "true")
    @LongSupport
    String getI18nKey();

    @Require(func = "isShownInTooltip", is = "true")
    @LongSupport
    boolean canTooltipShow(Number n);

    @Require(func = "isShownInTooltip", is = "true")
    @LongSupport
    default boolean canTooltipShow(FinalAttrValue attrValue) {
        return isShownInTooltip() && canTooltipShow(attrValue.getNumber());
    }

    @LongSupport
    boolean isBasic();

    @Nonnull
    @LongSupport
    DataType getDataType();

    @Nonnull
    @LongSupport
    ComputeType getComputeType();

    @Nonnull
    @LongSupport
    Number getDefaultValue();

    @Nonnull
    BasicAttrValue emptyBasicAttrValue();

    @Nonnull
    BasicAttrValue newBasicAttrValue(@Nonnull Number value);

    @Nonnull
    AttrModifier emptyAttrModifier();

    @Nonnull
    AttrModifier newAttrModifier(@Nonnull Number delta, float deltaRate);

    @Nonnull
    AttrAmp newAttributeAmplifier(@Nonnull Number value);

    @Nonnull
    AttrDelta emptyAttrDelta();

    @Nonnull
    AttrDelta newAttrDelta(@Nonnull Number delta);

    @Nonnull
    FinalAttrValue emptyFinalAttrValue();

    @Nonnull
    FinalAttrValue newFinalAttrValue(@Nonnull Number value);


    @LongSupport
    boolean isDefaultValue(@Nonnull Number value);

    @LongSupport
    default boolean isDefaultValue(@Nonnull FinalAttrValue value) {
        return isDefaultValue(value.getNumber());
    }

    @LongSupport
    default boolean isNotDefaultValue(@Nonnull Number value) {
        return !isDefaultValue(value);
    }

    @LongSupport
    default boolean isNotDefaultValue(@Nonnull FinalAttrValue value) {
        return isNotDefaultValue(value.getNumber());
    }

    @Nonnull
    @LongSupport
    FinalAttrValue compute(@Nonnull BasicAttrValue base, @Nullable AttrModifier modifier, @Nullable AttrDelta mastery);

    @Nonnull
    @Require(func = "useSpecialValueWhenWeaponBroken", is = "true")
    @LongSupport
    FinalAttrValue getValueWhenWeaponBroken(Number former);

    @Nonnull
    @Require(func = "useSpecialValueWhenWeaponBroken", is = "true")
    @LongSupport
    default FinalAttrValue getValueWhenWeaponBroken(FinalAttrValue former) {
        return getValueWhenWeaponBroken(former.getNumber());
    }

    @Developing
    boolean useSpecialValueWhenWeaponBroken();
}
