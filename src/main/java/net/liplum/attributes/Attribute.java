package net.liplum.attributes;

import net.liplum.lib.utils.FawItemUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

public class Attribute {
    private static final Set<Attribute> BasicAttribute = new HashSet<>();

    private boolean isBasic = false;
    @Nonnull
    private DataType dataType = DataType.Int;
    @Nonnull
    private ComputeType computeType = ComputeType.Only_Rate;

    @Nonnull
    private Number defaultValue = 0;

    public Attribute setBasic() {
        isBasic = true;
        BasicAttribute.add(this);
        return this;
    }

    public Attribute setDataType(@Nonnull DataType dataType) {
        this.dataType = dataType;
        return this;
    }

    public Attribute setDefaultValue(Number defaultValue) {
        this.defaultValue = defaultValue;
        return this;
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

    public BasicAttrValue genBasicAttrValue() {
        return new BasicAttrValue(defaultValue, dataType);
    }

    public AttrModifier genAttrModifier() {
        return new AttrModifier(dataType, 0, 0);
    }

    public static <T extends IBasicAttrValueBuilder> T genAllBasicAttrValue(T builder) {
        for (Attribute attribute : BasicAttribute) {
            builder.add(attribute, attribute.genBasicAttrValue());
        }
        return builder;
    }

    public static <T extends IAttrModifierBuilder> T genAllAttrModifiers(T builder) {
        for (Attribute attribute : BasicAttribute) {
            builder.add(attribute, attribute.genAttrModifier());
        }
        return builder;
    }

    public FinalAttrValue compute(@Nonnull BasicAttrValue base, @Nullable AttrModifier modifier, @Nullable AttrDelta master) {
        switch (computeType) {
            case Full:
                assert master != null;
                assert modifier != null;
                return computeFull(base, modifier, master);
            case Only_Rate:
                assert modifier != null;
                return computeOnlyRate(base, modifier);
            case Only_Master:
                assert master != null;
                return computeOnlyMaster(base, master);
            case Only_Base:
                return computeOnlyBase(base);
            default://Only_Gemstone
                assert modifier != null;
                return computeOnlyGemstone(base, modifier);
        }
    }

    private FinalAttrValue computeOnlyBase(@Nonnull BasicAttrValue base) {
        switch (dataType) {
            case Int:
                return new FinalAttrValue(
                        base.getInt()
                        , dataType);
            default:// Float
                return new FinalAttrValue(
                        base.getFloat()
                        , DataType.Float);
        }
    }

    private FinalAttrValue computeFull(@Nonnull BasicAttrValue base, @Nonnull AttrModifier modifier, @Nonnull AttrDelta master) {
        switch (dataType) {
            case Int:
                return new FinalAttrValue(
                        FawItemUtil.calcuAttribute(base.getInt(), modifier.getDelta().intValue(), modifier.getRate(), master.getDelta().intValue())
                        , dataType);
            default:// Float
                return new FinalAttrValue(
                        FawItemUtil.calcuAttribute(base.getFloat(), modifier.getDelta().floatValue(), modifier.getRate(), master.getDelta().floatValue())
                        , DataType.Float);
        }
    }

    private FinalAttrValue computeOnlyGemstone(@Nonnull BasicAttrValue base, @Nonnull AttrModifier modifier) {
        switch (dataType) {
            case Int:
                return new FinalAttrValue(
                        FawItemUtil.calcuAttribute(base.getInt(), modifier.getDelta().intValue(), modifier.getRate())
                        , dataType);
            default:// Float
                return new FinalAttrValue(
                        FawItemUtil.calcuAttribute(base.getFloat(), modifier.getDelta().floatValue(), modifier.getRate())
                        , dataType);
        }
    }

    private FinalAttrValue computeOnlyRate(@Nonnull BasicAttrValue base, @Nonnull AttrModifier modifier) {
        switch (dataType) {
            case Int:
                return new FinalAttrValue(
                        FawItemUtil.calcuAttributeInRate(base.getInt(), modifier.getRate())
                        , dataType);
            default:// Float
                return new FinalAttrValue(
                        FawItemUtil.calcuAttributeInRate(base.getFloat(), modifier.getRate())
                        , dataType);
        }
    }

    private FinalAttrValue computeOnlyMaster(@Nonnull BasicAttrValue base, @Nonnull AttrDelta master) {
        switch (dataType) {
            case Int:
                return new FinalAttrValue(
                        FawItemUtil.calcuAttribute(base.getInt(), master.getDelta().intValue())
                        , dataType);
            default:// Float
                return new FinalAttrValue(
                        FawItemUtil.calcuAttributeInRate(base.getFloat(), master.getDelta().floatValue())
                        , dataType);
        }
    }
}
