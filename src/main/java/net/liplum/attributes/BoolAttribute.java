package net.liplum.attributes;

import net.liplum.api.annotations.Require;
import net.liplum.masteries.AttrAmp;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.function.Function;

public class BoolAttribute implements IAttribute {
    public static final int FalseInt = toInt(false);
    public static final FinalAttrValue EmptyFinalAttrValue = new FinalAttrValue(DataType.Int, FalseInt);
    public static final int TrueInt = toInt(true);
    public static final BasicAttrValue TrueBasicAttrValue = genBasicAttrValue(true);
    public static final AttrDelta TrueAttrDelta = genAttrDelta(true);
    public static final AttrModifier TrueAttrModifier = genAttrModifier(true);
    public static final FinalAttrValue TrueFinalAttrValue = genFinalAttrValue(true);
    public static final BasicAttrValue FalseBasicAttrValue = genBasicAttrValue(false);
    public static final AttrModifier FalseAttrModifier = genAttrModifier(false);
    public static final AttrDelta FalseAttrDelta = genAttrDelta(false);
    public static final FinalAttrValue FalseFinalAttrValue = genFinalAttrValue(false);
    private static final AttrModifier EmptyAttrModifier = new AttrModifier(DataType.Int, FalseInt, 0);
    private static final AttrDelta EmptyAttrDelta = new AttrDelta(DataType.Int, FalseInt);
    private String registerName;
    private boolean isBasic = false;
    private ComputeType computeType;
    private int defaultValueInt = FalseInt;
    private boolean defaultValueBool = false;
    @NotNull
    private BasicAttrValue emptyBasicAttrValue = newBasicAttrValue(FalseInt);
    private IFullCompute fullCompute;
    private IOnlyGemstoneCompute onlyGemstoneCompute;
    private IOnlyMasteryCompute onlyMasteryCompute;
    private boolean useSpecialValueWhenWeaponBroken = false;
    @NotNull
    private Function<Boolean, Boolean> valueWhenWeaponBrokenGetter = (former) -> defaultValueBool;

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

    public static boolean toBool(@NotNull Number value) {
        return value.intValue() != 0;
    }

    public static boolean toBool(@NotNull FinalAttrValue value) {
        return value.getInt() != 0;
    }

    public static int toInt(boolean bool) {
        return bool ? 1 : 0;
    }

    public static int toBoolInt(@NotNull Number value) {
        return value.intValue() != 0 ? 1 : 0;
    }

    @NotNull
    private static FinalAttrValue newFinalAttrValue(boolean value) {
        return new FinalAttrValue(DataType.Int, toInt(value));
    }

    @NotNull
    public BoolAttribute setBasic() {
        this.isBasic = true;
        Attribute.setBasicAttribute(this);
        return this;
    }

    @NotNull
    @Require(func = "getComputeType", is = "ComputeType.Full")
    public BoolAttribute setFullCompute(@NotNull IFullCompute fullCompute) {
        this.fullCompute = fullCompute;
        return this;
    }

    @NotNull
    @Require(func = "getComputeType", is = "ComputeType.Only_Gemstone")
    public BoolAttribute setOnlyGemstoneCompute(@NotNull IOnlyGemstoneCompute onlyGemstoneCompute) {
        this.onlyGemstoneCompute = onlyGemstoneCompute;
        return this;
    }

    @NotNull
    @Require(func = "getComputeType", is = "ComputeType.Only_Mastery")
    public BoolAttribute setOnlyMasteryCompute(@NotNull IOnlyMasteryCompute onlyMasteryCompute) {
        this.onlyMasteryCompute = onlyMasteryCompute;
        return this;
    }

    @Override
    public boolean isBasic() {
        return isBasic;
    }

    @NotNull
    @Override
    public String getRegisterName() {
        return registerName;
    }

    @NotNull
    public BoolAttribute setRegisterName(@NotNull String registerName) {
        String former = this.registerName;
        Attribute.remove(former);

        this.registerName = registerName;
        Attribute.register(this);
        return this;
    }

    @NotNull
    @Override
    public ComputeType getComputeType() {
        return computeType;
    }

    @NotNull
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

    @NotNull
    @Override
    public Number getDefaultValue() {
        return 0;
    }

    @NotNull
    public BoolAttribute setDefaultValue(boolean defaultValue) {
        this.defaultValueBool = defaultValue;
        this.defaultValueInt = toInt(defaultValue);
        this.emptyBasicAttrValue = newBasicAttrValue(this.defaultValueInt);
        return this;
    }

    @NotNull
    @Override
    public BasicAttrValue emptyBasicAttrValue() {
        return emptyBasicAttrValue;
    }

    @NotNull
    @Override
    public BasicAttrValue newBasicAttrValue(@NotNull Number value) {
        return new BasicAttrValue(DataType.Int, toBoolInt(value));
    }

    @NotNull
    @Override
    public AttrModifier emptyAttrModifier() {
        return EmptyAttrModifier;
    }

    @NotNull
    @Override
    public AttrModifier newAttrModifier(@NotNull Number delta, float deltaRate) {
        return new AttrModifier(DataType.Int, toBoolInt(delta), 0);
    }

    @NotNull
    @Override
    public AttrAmp newAttributeAmplifier(@NotNull Number value) {
        return AttrAmp.create(this, value);
    }

    @NotNull
    @Override
    public AttrDelta emptyAttrDelta() {
        return EmptyAttrDelta;
    }

    @NotNull
    @Override
    public AttrDelta newAttrDelta(@NotNull Number delta) {
        return new AttrDelta(DataType.Int, toBoolInt(delta));
    }

    @NotNull
    @Override
    public FinalAttrValue emptyFinalAttrValue() {
        return EmptyFinalAttrValue;
    }

    @NotNull
    @Override
    public FinalAttrValue newFinalAttrValue(@NotNull Number value) {
        return new FinalAttrValue(DataType.Int, toBoolInt(value));
    }

    @Override
    public boolean isDefaultValue(@NotNull Number value) {
        return defaultValueBool == toBool(value);
    }

    @NotNull
    @Override
    public FinalAttrValue compute(@NotNull BasicAttrValue base, @Nullable AttrModifier modifier, @Nullable AttrDelta mastery) {
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

    /*
     * The default is returning the default value of this attribute.
     */
    @NotNull
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
    @NotNull
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
    @NotNull
    public BoolAttribute setUseSpecialValueWhenWeaponBroken() {
        this.useSpecialValueWhenWeaponBroken = true;
        return this;
    }

    ///------------------------------------------------------------------------------

    @Override
    public boolean isShownInTooltip() {
        return false;
    }

    @NotNull
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

    @NotNull
    @Override
    public String getI18nKey() {
        return "";
    }

    @Override
    @Require(func = "isShownInTooltip", is = "true")
    public boolean canTooltipShow(Number n) {
        return false;
    }

    @NotNull
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
