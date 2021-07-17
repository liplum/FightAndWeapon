package net.liplum.attributes;

import net.liplum.masteries.AttributeAmplifier;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface IAttribute {
    @Nonnull
    String getRegisterName();

    boolean needMoreDetailsToShown();

    boolean isStripTrailingZero();

    boolean hasUnit();

    boolean isShownInTooltip();

    /**
     * Smaller number means front<br/>
     * Larger number means later<br/>
     * O is default.
     *
     * @return the priority of display
     */
    default int getDisplayPriority() {
        return 0;
    }

    default int getTooltipShownIntValue(Number input) {
        return getTooltipShownValue(input).intValue();
    }

    default float getTooltipShownFloatValue(Number input) {
        return getTooltipShownValue(input).floatValue();
    }

    @Nonnull
    Number getTooltipShownValue(Number input);

    @Nonnull
    default Number getTooltipShownValue(FinalAttrValue input) {
        return getTooltipShownValue(input.getNumber());
    }

    @Nullable
    String getUnit();

    @Nullable
    String getFormat();

    @Nonnull
    String getI18nKey();

    boolean canTooltipShow(Number n);

    default boolean canTooltipShow(FinalAttrValue attrValue) {
        return canTooltipShow(attrValue.getNumber());
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

}
