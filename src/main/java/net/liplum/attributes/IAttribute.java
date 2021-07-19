package net.liplum.attributes;

import net.liplum.masteries.AttributeAmplifier;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface IAttribute {
    @Nonnull
    String getRegisterName();

    @Require(func = "isShownInTooltip", is = "true")
    boolean needMoreDetailsToShown();

    @Require(func = "isShownInTooltip", is = "true")
    boolean isStripTrailingZero();

    @Require(func = "isShownInTooltip", is = "true")
    boolean hasUnit();

    boolean isShownInTooltip();

    /**
     * Smaller number means front<br/>
     * Larger number means later<br/>
     * O is default.
     *
     * @return the priority of display
     */
    @Require(func = "isShownInTooltip", is = "true")
    default int getDisplayPriority() {
        return 0;
    }

    @Require(func = "isShownInTooltip", is = "true")
    default int getTooltipShownIntValue(Number input) {
        return getTooltipShownValue(input).intValue();
    }

    @Require(func = "isShownInTooltip", is = "true")
    default float getTooltipShownFloatValue(Number input) {
        return getTooltipShownValue(input).floatValue();
    }

    @Nonnull
    @Require(func = "isShownInTooltip", is = "true")
    Number getTooltipShownValue(Number input);

    @Nonnull
    @Require(func = "isShownInTooltip", is = "true")
    default Number getTooltipShownValue(FinalAttrValue input) {
        return getTooltipShownValue(input.getNumber());
    }

    @Nullable
    @Require(func = "isShownInTooltip", is = "true")
    String getUnit();

    @Nullable
    @Require(func = "isShownInTooltip", is = "true")
    String getFormat();

    @Nonnull
    @Require(func = "isShownInTooltip", is = "true")
    String getI18nKey();

    @Require(func = "isShownInTooltip", is = "true")
    boolean canTooltipShow(Number n);

    @Require(func = "isShownInTooltip", is = "true")
    default boolean canTooltipShow(FinalAttrValue attrValue) {
        return isShownInTooltip() && canTooltipShow(attrValue.getNumber());
    }

    boolean isBasic();

    @Nonnull
    DataType getDataType();

    @Nonnull
    ComputeType getComputeType();

    @Nonnull
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
    AttributeAmplifier newAttributeAmplifier(@Nonnull Number value);

    @Nonnull
    AttrDelta emptyAttrDelta();

    @Nonnull
    AttrDelta newAttrDelta(@Nonnull Number delta);

    @Nonnull
    FinalAttrValue emptyFinalAttrValue();

    @Nonnull
    FinalAttrValue newFinalAttrValue(@Nonnull Number value);


    boolean isDefaultValue(@Nonnull Number value);

    default boolean isDefaultValue(@Nonnull FinalAttrValue value) {
        return isDefaultValue(value.getNumber());
    }

    default boolean isNotDefaultValue(@Nonnull Number value) {
        return !isDefaultValue(value);
    }

    default boolean isNotDefaultValue(@Nonnull FinalAttrValue value) {
        return isNotDefaultValue(value.getNumber());
    }

    @Nonnull
    FinalAttrValue compute(@Nonnull BasicAttrValue base, @Nullable AttrModifier modifier, @Nullable AttrDelta mastery);

    @Nonnull
    @Require(func = "useSpecialValueWhenWeaponBroken", is = "true")
    FinalAttrValue getValueWhenWeaponBroken();

    boolean useSpecialValueWhenWeaponBroken();
}
