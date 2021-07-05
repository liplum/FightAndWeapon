package net.liplum;

import net.liplum.attributes.Attribute;
import net.liplum.attributes.ComputeType;
import net.liplum.attributes.DataType;

public final class Attributes {
    public static final class Generic {
        public static final Attribute Strength = new Attribute()
                .setBasic()
                .setDataType(DataType.Float)
                .setComputeType(ComputeType.Full)
                .setDefaultValue(0);
        public static final Attribute AttackReach = new Attribute()
                .setBasic()
                .setDataType(DataType.Float)
                .setComputeType(ComputeType.Full)
                .setDefaultValue(4.5D);
        public static final Attribute AbilityPower = new Attribute()
                .setBasic()
                .setDataType(DataType.Float)
                .setComputeType(ComputeType.Full)
                .setDefaultValue(0);
        public static final Attribute EnemyBreakingTime = new Attribute()
                .setBasic()
                .setDataType(DataType.Int)
                .setComputeType(ComputeType.Only_Gemstone)
                .setDefaultValue(20);
        public static final Attribute CoolDown = new Attribute()
                .setBasic()
                .setDataType(DataType.Int)
                .setComputeType(ComputeType.Only_Rate)
                .setDefaultValue(0);
        public static final Attribute KnockbackStrength = new Attribute()
                .setBasic()
                .setDataType(DataType.Float)
                .setComputeType(ComputeType.Full)
                .setDefaultValue(1);
    }

    public static final class Lance {
        public static final Attribute SprintStrength = new Attribute()
                .setDataType(DataType.Float)
                .setComputeType(ComputeType.Full)
                .setDefaultValue(0);
    }
    public static final class Harp {
        public static final Attribute Radius = new Attribute()
                .setDataType(DataType.Float)
                .setComputeType(ComputeType.Full)
                .setDefaultValue(1);
        public static final Attribute Frequency = new Attribute()
                .setDataType(DataType.Int)
                .setComputeType(ComputeType.Full)
                .setDefaultValue(1);
        public static final Attribute MaxUseDuration = new Attribute()
                .setDataType(DataType.Int)
                .setComputeType(ComputeType.Only_Base)
                .setDefaultValue(72000);
    }
}
