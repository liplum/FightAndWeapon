package net.liplum.attributes;

import net.liplum.lib.math.MathUtil;
import net.liplum.masteries.AttributeAmplifier;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public class Attribute {
    @Nonnull
    private static final Map<String, Attribute> AttributesMap = new HashMap<>();

    @Nonnull
    private static final LinkedList<Attribute> BasicAttributes = new LinkedList<>();

    private boolean isBasic = false;

    @Nonnull
    private DataType dataType = DataType.Int;

    @Nonnull
    private ComputeType computeType = ComputeType.Only_Rate;

    @Nonnull
    private String registerName = "";

    private boolean shownInTooltip = true;

    private boolean hasUnit = false;

    @Nullable
    private String unit;

    @Nullable
    private String format;

    @Nonnull
    private Number defaultValue = 0;

    private boolean needMoreDetailsToShown = false;

    @Nullable
    private Number minimum;

    private boolean isStripTrailingZero = true;

    @Nonnull
    private Function<Number, Number> tooltipShownMapping = n -> n;

    @Nonnull
    private Predicate<Number> canTooltipShow = n -> {
        if (dataType == DataType.Int) {
            return n.intValue() > 0;
        }
        return n.floatValue() > 0;
    };

    @Nonnull
    private Function<String, String> I18nKeyMapping = str -> str;

    @Nonnull
    private BasicAttrValue emptyBasicAttrValue = newBasicAttrValue(defaultValue);

    @Nonnull
    private static final AttrModifier emptyAttrModifierInt = new AttrModifier(DataType.Int, 0, 0);
    @Nonnull
    private static final AttrModifier emptyAttrModifierFloat = new AttrModifier(DataType.Float, 0, 0);

    @Nonnull
    private static final AttrDelta emptyAttrDeltaInt = new AttrDelta(DataType.Int, 0);
    @Nonnull
    private static final AttrDelta emptyAttrDeltaFloat = new AttrDelta(DataType.Float, 0);

    @Nonnull
    private static final FinalAttrValue emptyFinalAttrValueInt = new FinalAttrValue(DataType.Int, 0);
    @Nonnull
    private static final FinalAttrValue emptyFinalAttrValueFloat = new FinalAttrValue(DataType.Float, 0);

    @Nullable
    public static Attribute getAttribute(@Nonnull String registerName) {
        return AttributesMap.get(registerName);
    }

    @Nonnull
    public static <T extends IBasicAttrValueBuilder> T genAllBasicAttrValue(@Nonnull T builder) {
        for (Attribute attribute : BasicAttributes) {
            builder.set(attribute, attribute.emptyBasicAttrValue());
        }
        return builder;
    }

    @Nonnull
    public static <T extends IAttrModifierBuilder> T genAllBasicAttrModifiers(@Nonnull T builder) {
        for (Attribute attribute : BasicAttributes) {
            builder.set(attribute, attribute.emptyAttrModifier());
        }
        return builder;
    }

    @Nonnull
    public static List<Attribute> getAllBasicAttributes() {
        return BasicAttributes;
    }

    @Nonnull
    public String getRegisterName() {
        return registerName;
    }

    @Nonnull
    public Attribute setRegisterName(@Nonnull String registerName) {
        String former = this.registerName;
        AttributesMap.remove(former);

        this.registerName = registerName;
        AttributesMap.put(registerName, this);
        return this;
    }

    public boolean isShownInTooltip() {
        return shownInTooltip;
    }

    @Nonnull
    public Attribute setShownInTooltip(boolean shownInTooltip) {
        this.shownInTooltip = shownInTooltip;
        return this;
    }

    @Nonnull
    public Attribute setIsStripTrailingZero(boolean isStripTrailingZero) {
        this.isStripTrailingZero = isStripTrailingZero;
        return this;
    }

    @Nonnull
    public Attribute setHowToGetI18nKey(Function<String, String> I18nKeyMapping) {
        this.I18nKeyMapping = I18nKeyMapping;
        return this;
    }

    public String getI18nKey() {
        return this.I18nKeyMapping.apply(this.registerName);
    }

    public boolean canTooltipShow(Number n) {
        return canTooltipShow.test(n);
    }

    public boolean canTooltipShow(FinalAttrValue attrValue) {
        return canTooltipShow.test(attrValue.getNumber());
    }

    public boolean hasUnit() {
        return hasUnit;
    }

    public Attribute setNeedMoreDetailsToShown() {
        this.needMoreDetailsToShown = true;
        return this;
    }

    public boolean needMoreDetailsToShown() {
        return this.needMoreDetailsToShown;
    }

    public boolean isStripTrailingZero() {
        return this.isStripTrailingZero;
    }

    @Nonnull
    public Attribute setHasUnit(@Nonnull String unit) {
        this.hasUnit = true;
        this.unit = unit;
        return this;
    }

    @Nonnull
    public Attribute setTooltipShownMapping(@Nonnull Function<Number, Number> mapping) {
        this.tooltipShownMapping = mapping;
        return this;
    }

    @Nonnull
    public Attribute setHowCanTooltipShow(@Nonnull Predicate<Number> predicate, boolean needAndPrevious) {
        if (needAndPrevious) {
            this.canTooltipShow = this.canTooltipShow.and(predicate);
        } else {
            this.canTooltipShow = predicate;
        }
        return this;
    }

    @Nonnull
    public Attribute setHowCanTooltipShow(@Nonnull Predicate<Number> predicate) {
        return setHowCanTooltipShow(predicate, true);
    }

    @Nonnull
    public Attribute setNeedIsNotDefaultValueCanTooltipShow() {
        return setHowCanTooltipShow(this::isNotDefaultValue, true);
    }

    @Nonnull
    public Attribute setMinimum(@Nonnull Number minimum) {
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

    public int getTooltipShownIntValue(Number input) {
        return tooltipShownMapping.apply(input).intValue();
    }

    public float getTooltipShownFloatValue(Number input) {
        return tooltipShownMapping.apply(input).floatValue();
    }

    public Number getTooltipShownValue(Number input) {
        return tooltipShownMapping.apply(input);
    }

    public Number getTooltipShownValue(FinalAttrValue input) {
        return tooltipShownMapping.apply(input.getNumber());
    }

    @Nullable
    public String getUnit() {
        return unit;
    }

    @Nullable
    public String getFormat() {
        return format;
    }

    @Nonnull
    public Attribute setFormat(@Nonnull String format) {
        this.format = format;
        return this;
    }

    public boolean isBasic() {
        return isBasic;
    }

    @Nonnull
    public DataType getDataType() {
        return dataType;
    }

    public Attribute setDataType(@Nonnull DataType dataType) {
        this.dataType = dataType;
        return this;
    }

    @Nonnull
    public ComputeType getComputeType() {
        return computeType;
    }

    public Attribute setComputeType(@Nonnull ComputeType computeType) {
        this.computeType = computeType;
        return this;
    }

    @Nonnull
    public Number getDefaultValue() {
        return defaultValue;
    }

    public Attribute setDefaultValue(@Nonnull Number defaultValue) {
        this.defaultValue = defaultValue;
        this.emptyBasicAttrValue = newBasicAttrValue(defaultValue);
        return this;
    }

    @Nonnull
    public BasicAttrValue emptyBasicAttrValue() {
        return emptyBasicAttrValue;
    }

    @Nonnull
    public BasicAttrValue newBasicAttrValue(@Nonnull Number value) {
        return new BasicAttrValue(dataType, value);
    }

    @Nonnull
    public AttrModifier emptyAttrModifier() {
        return dataType == DataType.Int ? emptyAttrModifierInt : emptyAttrModifierFloat;
    }

    @Nonnull
    public AttrModifier newAttrModifier(@Nonnull Number delta, float deltaRate) {
        return new AttrModifier(dataType, delta, deltaRate);
    }

    @Nonnull
    public AttributeAmplifier newAttributeAmplifier(@Nonnull Number value) {
        return new AttributeAmplifier().setAttributeName(registerName).setType(dataType).setValue(value);
    }


    @Nonnull
    public AttrDelta emptyAttrDelta() {
        return dataType == DataType.Int ? emptyAttrDeltaInt : emptyAttrDeltaFloat;
    }

    @Nonnull
    public AttrDelta newAttrDelta(@Nonnull Number delta) {
        return new AttrDelta(dataType, delta);
    }

    private static final Map<Attribute, FinalAttrValue> EmptyFinalAttrValueCache = new HashMap<>();

    @Nonnull
    public FinalAttrValue emptyFinalAttrValue() {
        return dataType == DataType.Int ? emptyFinalAttrValueInt : emptyFinalAttrValueFloat;
    }

    @Nonnull
    public FinalAttrValue newFinalAttrValue(@Nonnull Number value) {
        return new FinalAttrValue(dataType, fixMin(value));
    }

    public boolean isDefaultValue(@Nonnull Number value) {
        return value.equals(this.defaultValue);
    }

    public boolean isDefaultValue(@Nonnull FinalAttrValue value) {
        return isDefaultValue(value.getNumber());
    }

    public boolean isNotDefaultValue(@Nonnull Number value) {
        return !isDefaultValue(value);
    }

    public boolean isNotDefaultValue(@Nonnull FinalAttrValue value) {
        return isNotDefaultValue(value.getNumber());
    }

    @Nonnull
    public FinalAttrValue compute(@Nonnull BasicAttrValue base, @Nullable AttrModifier modifier, @Nullable AttrDelta master) {
        switch (computeType) {
            case Full:
                if (master == null && modifier == null) {
                    return computeOnlyBase(base);
                }
                if (master == null) {
                    return computeOnlyGemstone(base, modifier);
                }
                if (modifier == null) {
                    return computeOnlyMastery(base, master);
                }
                return computeFull(base, modifier, master);
            case Only_Rate:
                if (modifier == null) {
                    return computeOnlyBase(base);
                }
                return computeOnlyRate(base, modifier);
            case Only_Master:
                if (master == null) {
                    return computeOnlyBase(base);
                }
                return computeOnlyMastery(base, master);
            case Only_Base:
                return computeOnlyBase(base);
            default://Only_Gemstone
                if (modifier == null) {
                    return computeOnlyBase(base);
                }
                return computeOnlyGemstone(base, modifier);
        }
    }

    @Nonnull
    private FinalAttrValue computeOnlyBase(@Nonnull BasicAttrValue base) {
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

    @Nonnull
    private FinalAttrValue computeFull(@Nonnull BasicAttrValue base, @Nonnull AttrModifier modifier, @Nonnull AttrDelta master) {
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

    @Nonnull
    private FinalAttrValue computeOnlyGemstone(@Nonnull BasicAttrValue base, @Nonnull AttrModifier modifier) {
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

    @Nonnull
    private FinalAttrValue computeOnlyRate(@Nonnull BasicAttrValue base, @Nonnull AttrModifier modifier) {
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

    @Nonnull
    private FinalAttrValue computeOnlyMastery(@Nonnull BasicAttrValue base, @Nonnull AttrDelta master) {
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

    public Attribute setBasic() {
        if (!isBasic) {
            BasicAttributes.add(this);
            isBasic = true;
        }
        return this;
    }
}
