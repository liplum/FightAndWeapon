package net.liplum;

import net.liplum.attributes.Attribute;
import net.liplum.attributes.ComputeType;
import net.liplum.attributes.DataType;

public final class Attributes {
    public static final class Generic {
        public static final Attribute Strength = new Attribute()
                .setRegisterName(Names.Attribute.Generic.Strength)
                .setHowToGetI18nKey(I18ns.Attribute::Generic)
                .setBasic()
                .setDataType(DataType.Float)
                .setFormat("%.1f")
                .setComputeType(ComputeType.Full)
                .setDefaultValue(0);
        public static final Attribute AttackReach = new Attribute()
                .setRegisterName(Names.Attribute.Generic.AttackReach)
                .setHowToGetI18nKey(I18ns.Attribute::Generic)
                .setBasic()
                .setDataType(DataType.Float)
                .setFormat("%.1f")
                .setComputeType(ComputeType.Full)
                .setHasUnit(I18ns.Tooltip.Unit.Unit)
                .setDefaultValue(4.5F)
                .setNeedIsNotDefaultValueCanTooltipShow();

        /**
         * Gets the basic attack speed and it can be changed by modifier.
         * Range : (0,no limit]. A smaller value stands for a slower speed.(Normal items are 4)
         */
        public static final Attribute AttackSpeed = new Attribute()
                .setRegisterName(Names.Attribute.Generic.AttackSpeed)
                .setHowToGetI18nKey(I18ns.Attribute::Generic)
                .setBasic()
                .setDataType(DataType.Float)
                .setFormat("%.1f")
                .setComputeType(ComputeType.Only_Gemstone)
                .setDefaultValue(4F)
                .setNeedIsNotDefaultValueCanTooltipShow();

        public static final Attribute AbilityPower = new Attribute()
                .setRegisterName(Names.Attribute.Generic.AbilityPower)
                .setHowToGetI18nKey(I18ns.Attribute::Generic)
                .setBasic()
                .setDataType(DataType.Float)
                .setFormat("%.1f")
                .setComputeType(ComputeType.Full)
                .setDefaultValue(0);
        /**
         * Gets the upcoming invincible time of a enemy who was attacked by this weapon.(unit:tick)
         */
        public static final Attribute EnemyBreakingTime = new Attribute()
                .setRegisterName(Names.Attribute.Generic.EnemyBreakingTime)
                .setHowToGetI18nKey(I18ns.Attribute::Generic)
                .setBasic()
                .setDataType(DataType.Int)
                .setComputeType(ComputeType.Only_Gemstone)
                .setShownInTooltip(false)
                .setDefaultValue(20);
        /**
         * Gets the cool down time of weapon.(unit:tick)
         */
        public static final Attribute CoolDown = new Attribute()
                .setRegisterName(Names.Attribute.Generic.CoolDown)
                .setHowToGetI18nKey(I18ns.Attribute::Generic)
                .setBasic()
                .setDataType(DataType.Int)
                .setFormat("%.1f")
                .setComputeType(ComputeType.Only_Rate)
                .setHasUnit(I18ns.Tooltip.Unit.Second)
                .setNeedMoreDetailsToShown()
                .setTooltipShownMapping(Vanilla::PerSecond)
                .setDefaultValue(0);
        /**
         * Gets the knock back. The basic is 1.0F.
         */
        public static final Attribute KnockbackStrength = new Attribute()
                .setRegisterName(Names.Attribute.Generic.KnockbackStrength)
                .setHowToGetI18nKey(I18ns.Attribute::Generic)
                .setBasic()
                .setDataType(DataType.Float)
                .setComputeType(ComputeType.Full)
                .setShownInTooltip(false)
                .setDefaultValue(1);
    }

    public static final class Lance {
        /**
         * The float value of this is the Strength of a sprint.
         */
        public static final Attribute SprintStrength = new Attribute()
                .setRegisterName(Names.Attribute.Lance.SprintStrength)
                .setHowToGetI18nKey(I18ns.Attribute::Lance)
                .setDataType(DataType.Float)
                .setFormat("%.1f")
                .setComputeType(ComputeType.Full)
                .setDefaultValue(0);
    }

    public static final class BattleAxe {
        public static final Attribute SweepRange = new Attribute()
                .setRegisterName(Names.Attribute.BattleAxe.SweepRange)
                .setHowToGetI18nKey(I18ns.Attribute::BattleAxe)
                .setDataType(DataType.Float)
                .setFormat("%.1f")
                .setComputeType(ComputeType.Full)
                .setDefaultValue(1);
    }

    public static final class Harp {
        /**
         * Gets the radius of the range
         */
        public static final Attribute Radius = new Attribute()
                .setRegisterName(Names.Attribute.Harp.Radius)
                .setHowToGetI18nKey(I18ns.Attribute::Harp)
                .setDataType(DataType.Float)
                .setFormat("%.1f")
                .setHasUnit(I18ns.Tooltip.Unit.Unit)
                .setComputeType(ComputeType.Full)
                .setDefaultValue(1);
        /**
         * The default one equals 60 minutes
         */
        public static final Attribute Frequency = new Attribute()
                .setRegisterName(Names.Attribute.Harp.Frequency)
                .setHowToGetI18nKey(I18ns.Attribute::Harp)
                .setDataType(DataType.Int)
                .setFormat("%.1f")
                .setHasUnit(I18ns.Tooltip.Unit.TriggerPerSecond)
                .setNeedMoreDetailsToShown()
                .setComputeType(ComputeType.Only_Rate)
                .setTooltipShownMapping(Vanilla::PerSecond)
                .setDefaultValue(72000)
                .setMinimum(1);
        /**
         * The default one equals 60 minutes
         */
        public static final Attribute MaxUseDuration = new Attribute()
                .setRegisterName(Names.Attribute.Harp.MaxUseDuration)
                .setHowToGetI18nKey(I18ns.Attribute::Harp)
                .setDataType(DataType.Int)
                .setComputeType(ComputeType.Only_Base)
                .setShownInTooltip(false)
                .setDefaultValue(72000);
    }
}
