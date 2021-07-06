package net.liplum.attributes;

import net.liplum.lib.math.MathUtil;
import net.liplum.lib.utils.FawItemUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

public class Attribute {
    private static final Map<String, Attribute> AttributesMap = new HashMap<>();

    private static final Set<Attribute> BasicAttribute = new HashSet<>();

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

    public Attribute setBasic() {
        isBasic = true;
        BasicAttribute.add(this);
        return this;
    }

    public Attribute setDataType(@Nonnull DataType dataType) {
        this.dataType = dataType;
        return this;
    }

    public Attribute setDefaultValue(@Nonnull Number defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    @Nonnull
    public Attribute setRegisterName(@Nonnull String registerName) {
        this.registerName = registerName;
        AttributesMap.put(registerName, this);
        return this;
    }

    @Nullable
    public static Attribute getAttribute(@Nonnull String registerName) {
        return AttributesMap.get(registerName);
    }

    @Nonnull
    public String getRegisterName() {
        return registerName;
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

    @Nonnull
    public Attribute setHasUnit(@Nonnull String unit) {
        this.hasUnit = true;
        this.unit = unit;
        return this;
    }

    @Nonnull
    public Attribute setFormat(@Nonnull String format) {
        this.format = format;
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

    @Nullable
    public String getUnit() {
        return unit;
    }

    @Nullable
    public String getFormat() {
        return format;
    }

    public boolean isBasic() {
        return isBasic;
    }

    @Nonnull
    public DataType getDataType() {
        return dataType;
    }

    @Nonnull
    public ComputeType getComputeType() {
        return computeType;
    }

    @Nonnull
    public Number getDefaultValue() {
        return defaultValue;
    }

    public Attribute setComputeType(@Nonnull ComputeType computeType) {
        this.computeType = computeType;
        return this;
    }

    @Nonnull
    public BasicAttrValue genBasicAttrValue() {
        return new BasicAttrValue(dataType, defaultValue);
    }

    @Nonnull
    public BasicAttrValue newAttrModifier(@Nonnull Number value) {
        return new BasicAttrValue(dataType, value);
    }

    @Nonnull
    public AttrModifier genAttrModifier() {
        return new AttrModifier(dataType);
    }

    @Nonnull
    public AttrModifier newAttrModifier(@Nonnull Number delta, float deltaRate) {
        return new AttrModifier(dataType, delta, deltaRate);
    }

    @Nonnull
    public AttrDelta genAttrDelta() {
        return new AttrDelta(dataType);
    }

    @Nonnull
    public AttrDelta newAttrDelta(@Nonnull Number delta) {
        return new AttrDelta(dataType, delta);
    }

    @Nonnull
    public FinalAttrValue genFinalAttrValue() {
        return new FinalAttrValue(dataType, 0);
    }

    @Nonnull
    public FinalAttrValue newFinalAttrValue(@Nonnull Number value) {
        return new FinalAttrValue(dataType, fixMin(value));
    }

    public boolean isDefaultValue(@Nonnull FinalAttrValue value) {
        return value.getNumber().equals(this.defaultValue);
    }

    @Nonnull
    public static <T extends IBasicAttrValueBuilder> T genAllBasicAttrValue(@Nonnull T builder) {
        for (Attribute attribute : BasicAttribute) {
            builder.set(attribute, attribute.genBasicAttrValue());
        }
        return builder;
    }

    @Nonnull
    public static <T extends IAttrModifierBuilder> T genAllAttrModifiers(@Nonnull T builder) {
        for (Attribute attribute : BasicAttribute) {
            builder.set(attribute, attribute.genAttrModifier());
        }
        return builder;
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
                    return computeOnlyMaster(base, master);
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
                return computeOnlyMaster(base, master);
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
                        FawItemUtil.calcuAttribute(base.getInt(), modifier.getDelta().intValue(), modifier.getDeltaRate(), master.getDelta().intValue())
                );
            default:// Float
                return this.newFinalAttrValue(
                        FawItemUtil.calcuAttribute(base.getFloat(), modifier.getDelta().floatValue(), modifier.getDeltaRate(), master.getDelta().floatValue())
                );
        }
    }

    @Nonnull
    private FinalAttrValue computeOnlyGemstone(@Nonnull BasicAttrValue base, @Nonnull AttrModifier modifier) {
        switch (dataType) {
            case Int:
                return this.newFinalAttrValue(
                        FawItemUtil.calcuAttribute(base.getInt(), modifier.getDelta().intValue(), modifier.getDeltaRate())
                );
            default:// Float
                return this.newFinalAttrValue(
                        FawItemUtil.calcuAttribute(base.getFloat(), modifier.getDelta().floatValue(), modifier.getDeltaRate())
                );
        }
    }

    @Nonnull
    private FinalAttrValue computeOnlyRate(@Nonnull BasicAttrValue base, @Nonnull AttrModifier modifier) {
        switch (dataType) {
            case Int:
                return this.newFinalAttrValue(
                        FawItemUtil.calcuAttributeInRate(base.getInt(), modifier.getDeltaRate())
                );
            default:// Float
                return this.newFinalAttrValue(
                        FawItemUtil.calcuAttributeInRate(base.getFloat(), modifier.getDeltaRate())
                );
        }
    }

    @Nonnull
    private FinalAttrValue computeOnlyMaster(@Nonnull BasicAttrValue base, @Nonnull AttrDelta master) {
        switch (dataType) {
            case Int:
                return this.newFinalAttrValue(
                        FawItemUtil.calcuAttribute(base.getInt(), master.getDelta().intValue())
                );
            default:// Float
                return this.newFinalAttrValue(
                        FawItemUtil.calcuAttributeInRate(base.getFloat(), master.getDelta().floatValue())
                );
        }
    }
}
