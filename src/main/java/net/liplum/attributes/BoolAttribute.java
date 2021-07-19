package net.liplum.attributes;

import net.liplum.masteries.AttributeAmplifier;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Function;

public class BoolAttribute implements IAttribute {
    public static final int FalseInt = toInt(false);
    public static final FinalAttrValue EmptyFinalAttrValue = new FinalAttrValue(DataType.Int, FalseInt);
    public static final int TrueInt = toInt(true);
    private static final AttrModifier EmptyAttrModifier = new AttrModifier(DataType.Int, FalseInt, 0);
    private static final AttrDelta EmptyAttrDelta = new AttrDelta(DataType.Int, FalseInt);
    private String registerName;
    private boolean isBasic = false;
    private ComputeType computeType;
    private int defaultValueInt = FalseInt;
    private boolean defaultValueBool = false;
    @Nonnull
    private BasicAttrValue emptyBasicAttrValue = newBasicAttrValue(FalseInt);
    private IFullCompute fullCompute;
    private IOnlyGemstoneCompute onlyGemstoneCompute;
    private IOnlyMasteryCompute onlyMasteryCompute;

    public static final BasicAttrValue TrueBasicAttrValue = genBasicAttrValue(true);
    public static final AttrDelta TrueAttrDelta = genAttrDelta(true);
    public static final AttrModifier TrueAttrModifier = genAttrModifier(true);
    public static final FinalAttrValue TrueFinalAttrValue = genFinalAttrValue(true);

    public static final BasicAttrValue FalseBasicAttrValue = genBasicAttrValue(false);
    public static final AttrModifier FalseAttrModifier = genAttrModifier(false);
    public static final AttrDelta FalseAttrDelta = genAttrDelta(false);
    public static final FinalAttrValue FalseFinalAttrValue = genFinalAttrValue(false);

    public static BasicAttrValue genBasicAttrValue(boolean b) {
        return new BasicAttrValue(DataType.Int, toInt(b));
    }

    public static AttrModifier genAttrModifier(boolean b) {
        return new AttrModifier(DataType.Int, toInt(b), 0);
    }

    public static AttrDelta genAttrDelta(boolean b) {
        return new AttrDelta(DataType.Int, toInt(b));
    }

    public static FinalAttrValue genFinalAttrValue(boolean b) {
        return new FinalAttrValue(DataType.Int, toInt(b));
    }

    public static boolean toBool(@Nonnull Number value) {
        return value.intValue() != 0;
    }

    public static boolean toBool(@Nonnull FinalAttrValue value) {
        return value.getInt() != 0;
    }

    public static int toInt(boolean bool) {
        return bool ? 1 : 0;
    }

    public static int toBoolInt(@Nonnull Number value) {
        return value.intValue() != 0 ? 1 : 0;
    }

    @Nonnull
    private static FinalAttrValue newFinalAttrValue(boolean value) {
        return new FinalAttrValue(DataType.Int, toInt(value));
    }

    @Nonnull
    public BoolAttribute setBasic() {
        this.isBasic = true;
        Attribute.setBasicAttribute(this);
        return this;
    }

    @Nonnull
    @Require(func = "getComputeType", is = "ComputeType.Full")
    public BoolAttribute setFullCompute(@Nonnull IFullCompute fullCompute) {
        this.fullCompute = fullCompute;
        return this;
    }

    @Nonnull
    @Require(func = "getComputeType", is = "ComputeType.Only_Gemstone")
    public BoolAttribute setOnlyGemstoneCompute(@Nonnull IOnlyGemstoneCompute onlyGemstoneCompute) {
        this.onlyGemstoneCompute = onlyGemstoneCompute;
        return this;
    }

    @Nonnull
    @Require(func = "getComputeType", is = "ComputeType.Only_Mastery")
    public BoolAttribute setOnlyMasteryCompute(@Nonnull IOnlyMasteryCompute onlyMasteryCompute) {
        this.onlyMasteryCompute = onlyMasteryCompute;
        return this;
    }

    @Override
    public boolean isBasic() {
        return isBasic;
    }

    @Nonnull
    @Override
    public String getRegisterName() {
        return registerName;
    }

    @Nonnull
    public BoolAttribute setRegisterName(@Nonnull String registerName) {
        String former = this.registerName;
        Attribute.remove(former);

        this.registerName = registerName;
        Attribute.register(this);
        return this;
    }

    @Nonnull
    @Override
    public ComputeType getComputeType() {
        return computeType;
    }

    @Nonnull
    public BoolAttribute setComputeType(ComputeType type) {
        ComputeType finalType = type;
        switch (type) {
            case Full:
                break;
            case Only_Rate:
                finalType = ComputeType.Only_Gemstone;
            case Only_Mastery:
                break;
            case Only_Gemstone:
                break;
            case Only_Base:
                break;
        }
        this.computeType = finalType;
        return this;
    }

    @Nonnull
    @Override
    public Number getDefaultValue() {
        return 0;
    }

    @Nonnull
    public BoolAttribute setDefaultValue(boolean defaultValue) {
        this.defaultValueBool = defaultValue;
        this.defaultValueInt = toInt(defaultValue);
        this.emptyBasicAttrValue = newBasicAttrValue(this.defaultValueInt);
        return this;
    }

    @Nonnull
    @Override
    public BasicAttrValue emptyBasicAttrValue() {
        return emptyBasicAttrValue;
    }

    @Nonnull
    @Override
    public BasicAttrValue newBasicAttrValue(@Nonnull Number value) {
        return new BasicAttrValue(DataType.Int, toBoolInt(value));
    }

    @Nonnull
    @Override
    public AttrModifier emptyAttrModifier() {
        return EmptyAttrModifier;
    }

    @Nonnull
    @Override
    public AttrModifier newAttrModifier(@Nonnull Number delta, float deltaRate) {
        return new AttrModifier(DataType.Int, toBoolInt(delta), 0);
    }

    @Nonnull
    @Override
    public AttributeAmplifier newAttributeAmplifier(@Nonnull Number value) {
        return new AttributeAmplifier().setAttributeName(registerName).setType(DataType.Int).setValue(toBoolInt(value));
    }

    @Nonnull
    @Override
    public AttrDelta emptyAttrDelta() {
        return EmptyAttrDelta;
    }

    @Nonnull
    @Override
    public AttrDelta newAttrDelta(@Nonnull Number delta) {
        return new AttrDelta(DataType.Int, toBoolInt(delta));
    }

    @Nonnull
    @Override
    public FinalAttrValue emptyFinalAttrValue() {
        return EmptyFinalAttrValue;
    }

    @Nonnull
    @Override
    public FinalAttrValue newFinalAttrValue(@Nonnull Number value) {
        return new FinalAttrValue(DataType.Int, toBoolInt(value));
    }

    @Override
    public boolean isDefaultValue(@Nonnull Number value) {
        return defaultValueBool == toBool(value);
    }

    @Nonnull
    @Override
    public FinalAttrValue compute(@Nonnull BasicAttrValue base, @Nullable AttrModifier modifier, @Nullable AttrDelta mastery) {
        boolean boolBase = toBool(base.getNumber());
        switch (computeType) {
            case Full:
                if (mastery == null && modifier == null) {
                    return newFinalAttrValue(boolBase);
                }
                if (mastery == null) {
                    boolean boolModifier = toBool(modifier.getDelta());
                    return newFinalAttrValue(onlyGemstoneCompute.compute(boolBase, boolModifier));
                }
                if (modifier == null) {
                    boolean boolMastery = toBool(mastery.getDelta());
                    return newFinalAttrValue(onlyMasteryCompute.compute(boolBase, boolMastery));
                }
            {
                boolean boolModifier = toBool(modifier.getDelta());
                boolean boolMastery = toBool(mastery.getDelta());
                return newFinalAttrValue(fullCompute.compute(boolBase, boolModifier, boolMastery));
            }
            case Only_Mastery:
                if (mastery == null) {
                    return newFinalAttrValue(boolBase);
                }
            {
                boolean boolMastery = toBool(mastery.getDelta());
                return newFinalAttrValue(onlyMasteryCompute.compute(boolBase, boolMastery));
            }
            case Only_Base:
                return newFinalAttrValue(boolBase);
            case Only_Rate:
            case Only_Gemstone:
                if (modifier == null) {
                    return newFinalAttrValue(boolBase);
                }
            {
                boolean boolModifier = toBool(modifier.getDelta());
                return newFinalAttrValue(onlyGemstoneCompute.compute(boolBase, boolModifier));
            }
        }
        return emptyFinalAttrValue();
    }

    @Override
    @Require(func = "isShownInTooltip", is = "true")
    public boolean needMoreDetailsToShown() {
        return false;
    }

    @Override
    @Require(func = "isShownInTooltip", is = "true")
    public boolean isStripTrailingZero() {
        return false;
    }

    @Override
    @Require(func = "isShownInTooltip", is = "true")
    public boolean hasUnit() {
        return false;
    }

    private boolean useSpecialValueWhenWeaponBroken = false;

    @Nonnull
    private Function<Boolean, Boolean> valueWhenWeaponBrokenGetter = (former) -> defaultValueBool;

    /*
     * The default is returning the default value of this attribute.
     */
    @Nonnull
    @Require(func = "useSpecialValueWhenWeaponBroken", is = "true")
    public BoolAttribute setSpecialValueWhenWeaponBroken(boolean boolWhenWeaponBroken) {
        this.valueWhenWeaponBrokenGetter = (former) -> boolWhenWeaponBroken;
        return this;
    }

    /**
     * The default is returning the default value of this attribute.
     *
     * @param former
     * @return the final value when weapon was broken
     */
    @Nonnull
    @Override
    @Require(func = "useSpecialValueWhenWeaponBroken", is = "true")
    public FinalAttrValue getValueWhenWeaponBroken(Number former) {
        return newFinalAttrValue(valueWhenWeaponBrokenGetter.apply(toBool(former)));
    }

    /**
     * The default is false.
     */
    @Override
    public boolean useSpecialValueWhenWeaponBroken() {
        return useSpecialValueWhenWeaponBroken;
    }

    /**
     * The default is false.
     */
    @Nonnull
    public BoolAttribute setUseSpecialValueWhenWeaponBroken() {
        this.useSpecialValueWhenWeaponBroken = true;
        return this;
    }

    ///------------------------------------------------------------------------------

    @Override
    public boolean isShownInTooltip() {
        return false;
    }

    @Nonnull
    @Override
    @Require(func = "isShownInTooltip", is = "true")
    public Number getTooltipShownValue(Number input) {
        return 0;
    }

    @Nullable
    @Override
    @Require(func = "isShownInTooltip", is = "true")
    public String getUnit() {
        return null;
    }

    @Nullable
    @Override
    @Require(func = "isShownInTooltip", is = "true")
    public String getFormat() {
        return null;
    }

    @Nonnull
    @Override
    public String getI18nKey() {
        return "";
    }

    @Override
    @Require(func = "isShownInTooltip", is = "true")
    public boolean canTooltipShow(Number n) {
        return false;
    }

    @Nonnull
    @Override
    public DataType getDataType() {
        return DataType.Int;
    }

    public interface IFullCompute {
        boolean compute(boolean base, boolean modifier, boolean mastery);
    }

    public interface IOnlyGemstoneCompute {
        boolean compute(boolean base, boolean modifier);
    }

    public interface IOnlyMasteryCompute {
        boolean compute(boolean base, boolean mastery);
    }
}
