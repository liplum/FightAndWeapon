package net.liplum.attributes;

import net.liplum.api.annotations.Developing;
import net.liplum.api.annotations.LongSupport;
import net.liplum.api.annotations.Require;
import net.liplum.masteries.AttrAmp;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

@LongSupport
public interface IAttribute {
    @NotNull
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

    @NotNull
    @Require(func = "isShownInTooltip", is = "true")
    @LongSupport
    Number getTooltipShownValue(Number input);

    @NotNull
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

    @NotNull
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

    @NotNull
    @LongSupport
    DataType getDataType();

    @NotNull
    @LongSupport
    ComputeType getComputeType();

    @NotNull
    @LongSupport
    Number getDefaultValue();

    @NotNull
    BasicAttrValue emptyBasicAttrValue();

    @NotNull
    BasicAttrValue newBasicAttrValue(@NotNull Number value);

    @NotNull
    AttrModifier emptyAttrModifier();

    @NotNull
    AttrModifier newAttrModifier(@NotNull Number delta, float deltaRate);

    @NotNull
    AttrAmp newAttributeAmplifier(@NotNull Number value);

    @NotNull
    AttrDelta emptyAttrDelta();

    @NotNull
    AttrDelta newAttrDelta(@NotNull Number delta);

    @NotNull
    FinalAttrValue emptyFinalAttrValue();

    @NotNull
    FinalAttrValue newFinalAttrValue(@NotNull Number value);


    @LongSupport
    boolean isDefaultValue(@NotNull Number value);

    @LongSupport
    default boolean isDefaultValue(@NotNull FinalAttrValue value) {
        return isDefaultValue(value.getNumber());
    }

    @LongSupport
    default boolean isNotDefaultValue(@NotNull Number value) {
        return !isDefaultValue(value);
    }

    @LongSupport
    default boolean isNotDefaultValue(@NotNull FinalAttrValue value) {
        return isNotDefaultValue(value.getNumber());
    }

    @NotNull
    @LongSupport
    FinalAttrValue compute(@NotNull BasicAttrValue base, @Nullable AttrModifier modifier, @Nullable AttrDelta mastery);

    @NotNull
    @Require(func = "useSpecialValueWhenWeaponBroken", is = "true")
    @LongSupport
    FinalAttrValue getValueWhenWeaponBroken(Number former);

    @NotNull
    @Require(func = "useSpecialValueWhenWeaponBroken", is = "true")
    @LongSupport
    default FinalAttrValue getValueWhenWeaponBroken(FinalAttrValue former) {
        return getValueWhenWeaponBroken(former.getNumber());
    }

    @Developing
    boolean useSpecialValueWhenWeaponBroken();
}
