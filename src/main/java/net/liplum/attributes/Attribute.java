package net.liplum.attributes;

import net.liplum.api.annotations.LongSupport;
import net.liplum.api.annotations.Require;
import net.liplum.lib.math.MathUtil;
import net.liplum.masteries.AttrAmp;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

@LongSupport
public class Attribute implements IAttribute {
    @NotNull
    private static final Map<String, IAttribute> AttributesMap = new HashMap<>();
    @NotNull
    private static final LinkedList<IAttribute> BasicAttributes = new LinkedList<>();
    @NotNull
    private static final AttrModifier EmptyAttrModifierInt = new AttrModifier(DataType.Int, 0, 0);
    @NotNull
    private static final AttrModifier EmptyAttrModifierFloat = new AttrModifier(DataType.Float, 0, 0);
    @NotNull
    private static final AttrDelta EmptyAttrDeltaInt = new AttrDelta(DataType.Int, 0);
    @NotNull
    private static final AttrDelta EmptyAttrDeltaFloat = new AttrDelta(DataType.Float, 0);
    @NotNull
    private static final FinalAttrValue EmptyFinalAttrValueInt = new FinalAttrValue(DataType.Int, 0);
    @NotNull
    private static final FinalAttrValue EmptyFinalAttrValueFloat = new FinalAttrValue(DataType.Float, 0);
    private boolean isBasic = false;
    @NotNull
    private DataType dataType = DataType.Int;
    @NotNull
    private ComputeType computeType = ComputeType.Only_Rate;
    @NotNull
    private String registerName = "";
    private boolean shownInTooltip = true;
    private int displayPriority = 1;
    private boolean hasUnit = false;
    @Nullable
    private String unit;
    @Nullable
    private String format;
    @NotNull
    private Number defaultValue = 0;
    private boolean needMoreDetailsToShown = false;
    @Nullable
    private Number minimum;
    private boolean isStripTrailingZero = true;
    private boolean useSpecialValueWhenWeaponBroken = false;
    @NotNull
    private Function<Number, Number> tooltipShownMapping = n -> n;
    @NotNull
    private Predicate<Number> canTooltipShow = n -> {
        if (dataType == DataType.Int) {
            return n.intValue() > 0;
        }
        return n.floatValue() > 0;
    };
    @NotNull
    private Function<String, String> I18nKeyMapping = str -> str;
    @NotNull
    private BasicAttrValue emptyBasicAttrValue = newBasicAttrValue(defaultValue);
    @NotNull
    private Function<Number, Number> valueWhenWeaponBrokenGetter = (former) -> defaultValue;

    @LongSupport
    public static void register(IAttribute attribute) {
        AttributesMap.put(attribute.getRegisterName(), attribute);
    }

    @LongSupport
    public static void remove(String attributeName) {
        AttributesMap.remove(attributeName);
    }

    @LongSupport
    public static void setBasicAttribute(IAttribute attribute) {
        BasicAttributes.add(attribute);
    }

    @Nullable
    @LongSupport
    public static IAttribute getAttribute(@NotNull String registerName) {
        return AttributesMap.get(registerName);
    }

    @NotNull
    @LongSupport
    public static List<IAttribute> getAllBasicAttributes() {
        return BasicAttributes;
    }

    /**
     * Calculate the final value of attribute without gemstone's amplification.
     *
     * @param base
     * @param deltaMastery
     * @return the modified result or negative value if the base is less than 0 (Maybe it stands for a default value).
     */
    private static float calcu(float base, float deltaMastery) {
        return calcu(base, 0, 0, deltaMastery);

    }

    /**
     * Calculate the final value of attribute without Mastery's amplification.
     *
     * @param base
     * @param rateGem
     * @param deltaGem
     * @return the modified result or negative value if the base is less than 0 (Maybe it stands for a default value).
     */
    private static float calcu(float base, float deltaGem, float rateGem) {
        return calcu(base, deltaGem, rateGem, 0);
    }

    /**
     * @param base
     * @param deltaGem
     * @param rateGem
     * @param deltaMastery
     * @return the modified result or negative value if the base is less than 0 (Maybe it stands for a default value).
     */
    private static float calcu(float base, float deltaGem, float rateGem, float deltaMastery) {
        if (base < 0) {
            return -1;
        }
        float res = ((base + deltaMastery) + deltaGem) * (1 + rateGem);
        return MathUtil.fixMin(res, 0);
    }

    /**
     * Calculate the final value of attribute without gemstone's amplification.
     *
     * @param base
     * @param deltaMastery
     * @return the modified result or negative value if the base is less than 0 (Maybe it stands for a default value).
     */
    private static int calcu(int base, int deltaMastery) {
        return calcu(base, 0, 0, deltaMastery);

    }

    /**
     * Calculate the final value of attribute without Mastery's amplification.
     *
     * @param base
     * @param rateGem
     * @param deltaGem
     * @return the modified result or negative value if the base is less than 0 (Maybe it stands for a default value).
     */
    private static int calcu(int base, int deltaGem, float rateGem) {
        return calcu(base, deltaGem, rateGem, 0);
    }

    /**
     * @param base
     * @param deltaGem
     * @param rateGem
     * @param deltaMastery
     * @return the modified result or negative value if the base is less than 0 (Maybe it stands for a default value).
     */
    private static int calcu(int base, int deltaGem, float rateGem, int deltaMastery) {
        if (base < 0) {
            return -1;
        }
        if (deltaMastery == 0 && deltaGem == 0 && rateGem == 0) {
            return base;
        }
        int res = (int) (((base + deltaMastery) + deltaGem) * (1 + rateGem));
        return MathUtil.fixMin(res, 0);
    }

    /**
     * @param base
     * @param rate
     * @return
     */
    private static int calcuInRate(int base, float rate) {
        if (base < 0) {
            return -1;
        } else if (rate == 0) {
            return base;
        }
        return (int) (base * (1 + rate));
    }

    /**
     * @param base
     * @param rate
     * @return
     */
    private static float calcuInRate(float base, float rate) {
        if (base < 0) {
            return -1;
        } else if (rate == 0) {
            return base;
        }
        return base * (1 + rate);
    }

    @NotNull
    @Override
    public String getRegisterName() {
        return registerName;
    }

    @NotNull
    public Attribute setRegisterName(@NotNull String registerName) {
        String former = this.registerName;
        Attribute.remove(former);

        this.registerName = registerName;
        Attribute.register(this);
        return this;
    }

    @Override
    public boolean isShownInTooltip() {
        return shownInTooltip;
    }

    @NotNull
    @Require(func = "isShownInTooltip", is = "true")
    public Attribute setShownInTooltip(boolean shownInTooltip) {
        this.shownInTooltip = shownInTooltip;
        return this;
    }

    @NotNull
    @Require(func = "isShownInTooltip", is = "true")
    public Attribute setIsStripTrailingZero(boolean isStripTrailingZero) {
        this.isStripTrailingZero = isStripTrailingZero;
        return this;
    }

    @NotNull
    @Require(func = "isShownInTooltip", is = "true")
    public Attribute setHowToGetI18nKey(Function<String, String> I18nKeyMapping) {
        this.I18nKeyMapping = I18nKeyMapping;
        return this;
    }

    @NotNull
    @Override
    @Require(func = "isShownInTooltip", is = "true")
    public String getI18nKey() {
        if (!shownInTooltip) {
            return "";
        }
        return this.I18nKeyMapping.apply(this.registerName);
    }

    @Override
    @Require(func = "isShownInTooltip", is = "true")
    public boolean canTooltipShow(Number n) {
        return canTooltipShow.test(n);
    }

    @Override
    @Require(func = "isShownInTooltip", is = "true")
    public boolean hasUnit() {
        return hasUnit && unit != null;
    }

    @Require(func = "isShownInTooltip", is = "true")
    public Attribute setNeedMoreDetailsToShown() {
        this.needMoreDetailsToShown = true;
        return this;
    }

    @Override
    @Require(func = "isShownInTooltip", is = "true")
    public boolean needMoreDetailsToShown() {
        return this.needMoreDetailsToShown;
    }

    @Override
    @Require(func = "isShownInTooltip", is = "true")
    public boolean isStripTrailingZero() {
        return this.isStripTrailingZero;
    }

    @NotNull
    @Require(func = "isShownInTooltip", is = "true")
    public Attribute setHasUnit(@NotNull String unit) {
        this.hasUnit = true;
        this.unit = unit;
        return this;
    }

    @NotNull
    @Require(func = "isShownInTooltip", is = "true")
    public Attribute setTooltipShownMapping(@NotNull Function<Number, Number> mapping) {
        this.tooltipShownMapping = mapping;
        return this;
    }

    @NotNull
    @Require(func = "isShownInTooltip", is = "true")
    public Attribute setHowCanTooltipShow(@NotNull Predicate<Number> predicate, boolean needAndPrevious) {
        if (needAndPrevious) {
            this.canTooltipShow = this.canTooltipShow.and(predicate);
        } else {
            this.canTooltipShow = predicate;
        }
        return this;
    }

    @NotNull
    @Require(func = "isShownInTooltip", is = "true")
    public Attribute setHowCanTooltipShow(@NotNull Predicate<Number> predicate) {
        return setHowCanTooltipShow(predicate, true);
    }

    @NotNull
    @Require(func = "isShownInTooltip", is = "true")
    public Attribute setNeedIsNotDefaultValueCanTooltipShow() {
        return setHowCanTooltipShow(this::isNotDefaultValue, true);
    }

    @NotNull
    public Attribute setMinimum(@NotNull Number minimum) {
        this.minimum = minimum;
        this.defaultValue = fixMin(this.defaultValue);
        return this;
    }

    private Number fixMin(Number value) {
        if (minimum == null) {
            return value;
        }
        switch (dataType) {
            case Int:
                return MathUtil.fixMin(value.intValue(), minimum.intValue());
            case Float:
                return MathUtil.fixMin(value.floatValue(), minimum.floatValue());
        }
        return value;
    }

    /**
     * Smaller number means front<br/>
     * Larger number means later<br/>
     * O is default.
     *
     * @return the priority of display
     */
    @Override
    @Require(func = "isShownInTooltip", is = "true")
    public int getDisplayPriority() {
        return displayPriority;
    }

    /**
     * Smaller number means front<br/>
     * Larger number means later<br/>
     * O is default.
     */
    @Require(func = "isShownInTooltip", is = "true")
    public Attribute setDisplayPriority(int displayPriority) {
        this.displayPriority = displayPriority;
        return this;
    }

    @NotNull
    @Override
    @Require(func = "isShownInTooltip", is = "true")
    public Number getTooltipShownValue(Number input) {
        return tooltipShownMapping.apply(input);
    }

    @Nullable
    @Override
    @Require(func = "isShownInTooltip", is = "true")
    public String getUnit() {
        return unit;
    }

    @Nullable
    @Override
    @Require(func = "isShownInTooltip", is = "true")
    public String getFormat() {
        return format;
    }

    @NotNull
    @Require(func = "isShownInTooltip", is = "true")
    public Attribute setFormat(@NotNull String format) {
        this.format = format;
        return this;
    }

    @Override
    public boolean isBasic() {
        return isBasic;
    }

    @NotNull
    @Override
    public DataType getDataType() {
        return dataType;
    }

    public Attribute setDataType(@NotNull DataType dataType) {
        this.dataType = dataType;
        return this;
    }

    @NotNull
    @Override
    public ComputeType getComputeType() {
        return computeType;
    }

    public Attribute setComputeType(@NotNull ComputeType computeType) {
        this.computeType = computeType;
        return this;
    }

    @NotNull
    @Override
    public Number getDefaultValue() {
        return defaultValue;
    }

    @NotNull
    public Attribute setDefaultValue(@NotNull Number defaultValue) {
        this.defaultValue = defaultValue;
        this.emptyBasicAttrValue = newBasicAttrValue(defaultValue);
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
        return new BasicAttrValue(dataType, value);
    }

    @NotNull
    @Override
    public AttrModifier emptyAttrModifier() {
        return dataType == DataType.Int ? EmptyAttrModifierInt : EmptyAttrModifierFloat;
    }

    @NotNull
    @Override
    public AttrModifier newAttrModifier(@NotNull Number delta, float deltaRate) {
        return new AttrModifier(dataType, delta, deltaRate);
    }

    @NotNull
    @Override
    public AttrAmp newAttributeAmplifier(@NotNull Number value) {
        return AttrAmp.create(this, value);
    }

    @NotNull
    @Override
    public AttrDelta emptyAttrDelta() {
        return dataType == DataType.Int ? EmptyAttrDeltaInt : EmptyAttrDeltaFloat;
    }

    @NotNull
    @Override
    public AttrDelta newAttrDelta(@NotNull Number delta) {
        return new AttrDelta(dataType, delta);
    }

    @NotNull
    @Override
    public FinalAttrValue emptyFinalAttrValue() {
        return dataType == DataType.Int ? EmptyFinalAttrValueInt : EmptyFinalAttrValueFloat;
    }

    @NotNull
    @Override
    public FinalAttrValue newFinalAttrValue(@NotNull Number value) {
        return new FinalAttrValue(dataType, fixMin(value));
    }

    @Override
    public boolean isDefaultValue(@NotNull Number value) {
        return value.equals(this.defaultValue);
    }

    @NotNull
    @Override
    public FinalAttrValue compute(@NotNull BasicAttrValue base, @Nullable AttrModifier modifier, @Nullable AttrDelta mastery) {
        switch (computeType) {
            case Full:
                if (mastery == null && modifier == null) {
                    return computeOnlyBase(base);
                }
                if (mastery == null) {
                    return computeOnlyGemstone(base, modifier);
                }
                if (modifier == null) {
                    return computeOnlyMastery(base, mastery);
                }
                return computeFull(base, modifier, mastery);
            case Only_Rate:
                if (modifier == null) {
                    return computeOnlyBase(base);
                }
                return computeOnlyRate(base, modifier);
            case Only_Mastery:
                if (mastery == null) {
                    return computeOnlyBase(base);
                }
                return computeOnlyMastery(base, mastery);
            case Only_Base:
                return computeOnlyBase(base);
            case Only_Gemstone:
                if (modifier == null) {
                    return computeOnlyBase(base);
                }
                return computeOnlyGemstone(base, modifier);
        }
        return emptyFinalAttrValue();
    }

    /*
     * The default is returning the default value of this attribute.
     */
    @Require(func = "useSpecialValueWhenWeaponBroken", is = "true")
    public Attribute setSpecialValueWhenWeaponBroken(@NotNull Number valueWhenWeaponBroken) {
        this.valueWhenWeaponBrokenGetter = (former) -> valueWhenWeaponBroken;
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
        return newFinalAttrValue(valueWhenWeaponBrokenGetter.apply(former));
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
    public Attribute setUseSpecialValueWhenWeaponBroken() {
        this.useSpecialValueWhenWeaponBroken = true;
        return this;
    }

    @NotNull
    private FinalAttrValue computeOnlyBase(@NotNull BasicAttrValue base) {
        switch (dataType) {
            case Int:
                return this.newFinalAttrValue(
                        base.getInt()
                );
            default:// Float
                return this.newFinalAttrValue(
                        base.getFloat()
                );
        }
    }

    @NotNull
    private FinalAttrValue computeFull(@NotNull BasicAttrValue base, @NotNull AttrModifier modifier, @NotNull AttrDelta master) {
        switch (dataType) {
            case Int:
                return this.newFinalAttrValue(
                        calcu(base.getInt(), modifier.getDelta().intValue(), modifier.getDeltaRate(), master.getDelta().intValue())
                );
            default:// Float
                return this.newFinalAttrValue(
                        calcu(base.getFloat(), modifier.getDelta().floatValue(), modifier.getDeltaRate(), master.getDelta().floatValue())
                );
        }
    }

    @NotNull
    private FinalAttrValue computeOnlyGemstone(@NotNull BasicAttrValue base, @NotNull AttrModifier modifier) {
        switch (dataType) {
            case Int:
                return this.newFinalAttrValue(
                        calcu(base.getInt(), modifier.getDelta().intValue(), modifier.getDeltaRate())
                );
            default:// Float
                return this.newFinalAttrValue(
                        calcu(base.getFloat(), modifier.getDelta().floatValue(), modifier.getDeltaRate())
                );
        }
    }

    @NotNull
    private FinalAttrValue computeOnlyRate(@NotNull BasicAttrValue base, @NotNull AttrModifier modifier) {
        switch (dataType) {
            case Int:
                return this.newFinalAttrValue(
                        calcuInRate(base.getInt(), modifier.getDeltaRate())
                );
            default:// Float
                return this.newFinalAttrValue(
                        calcuInRate(base.getFloat(), modifier.getDeltaRate())
                );
        }
    }

    @NotNull
    private FinalAttrValue computeOnlyMastery(@NotNull BasicAttrValue base, @NotNull AttrDelta master) {
        switch (dataType) {
            case Int:
                return this.newFinalAttrValue(
                        calcu(base.getInt(), master.getDelta().intValue())
                );
            default:// Float
                return this.newFinalAttrValue(
                        calcu(base.getFloat(), master.getDelta().floatValue())
                );
        }
    }

    @NotNull
    public Attribute setBasic() {
        if (!isBasic) {
            Attribute.setBasicAttribute(this);
            isBasic = true;
        }
        return this;
    }
}
