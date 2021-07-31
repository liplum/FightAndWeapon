package net.liplum;

import net.liplum.api.annotations.LongSupport;
import net.liplum.attributes.*;

@LongSupport
public final class Attributes {
    @LongSupport
    public static final class Generic {
        public static final IAttribute Durability = new Attribute()
                .setRegisterName(Names.Attribute.Generic.Durability)
                .setBasic()
                .setDataType(DataType.Int)
                .setComputeType(ComputeType.Only_Base)
                .setDefaultValue(0)
                .setShownInTooltip(false)
                .setMinimum(0);
        public static final IAttribute Strength = new Attribute()
                .setRegisterName(Names.Attribute.Generic.Strength)
                .setHowToGetI18nKey(I18ns.Attribute::Generic)
                .setBasic()
                .setDataType(DataType.Float)
                .setFormat("%.1f")
                .setComputeType(ComputeType.Full)
                .setDisplayPriority(-100)
                .setDefaultValue(0)
                .setUseSpecialValueWhenWeaponBroken();
        public static final IAttribute AttackReach = new Attribute()
                .setRegisterName(Names.Attribute.Generic.AttackReach)
                .setHowToGetI18nKey(I18ns.Attribute::Generic)
                .setBasic()
                .setDataType(DataType.Float)
                .setFormat("%.1f")
                .setComputeType(ComputeType.Full)
                .setHasUnit(I18ns.Tooltip.Unit.Unit)
                .setDefaultValue(4.5F)
                .setDisplayPriority(100)
                .setNeedMoreDetailsToShown()
                .setNeedIsNotDefaultValueCanTooltipShow();

        /**
         * Gets the basic attack speed and it can be changed by modifier.
         * Range : (0,no limit]. A smaller value stands for a slower speed.(Normal items are 4)
         */
        public static final IAttribute AttackSpeed = new Attribute()
                .setRegisterName(Names.Attribute.Generic.AttackSpeed)
                .setBasic()
                .setDataType(DataType.Float)
                .setComputeType(ComputeType.Only_Gemstone)
                .setDefaultValue(4F)
                .setShownInTooltip(false)
                .setMinimum(0F);

        public static final IAttribute AbilityPower = new Attribute()
                .setRegisterName(Names.Attribute.Generic.AbilityPower)
                .setHowToGetI18nKey(I18ns.Attribute::Generic)
                .setBasic()
                .setDataType(DataType.Float)
                .setFormat("%.1f")
                .setComputeType(ComputeType.Full)
                .setDisplayPriority(-50)
                .setDefaultValue(0)
                .setUseSpecialValueWhenWeaponBroken();
        /**
         * Gets the upcoming invincible time of a enemy who was attacked by this weapon.(unit:tick)
         */
        public static final IAttribute EnemyBreakingTime = new Attribute()
                .setRegisterName(Names.Attribute.Generic.EnemyBreakingTime)
                .setBasic()
                .setDataType(DataType.Int)
                .setComputeType(ComputeType.Only_Gemstone)
                .setShownInTooltip(false)
                .setDefaultValue(20);
        /**
         * Gets the cool down time of weapon.(unit:tick)
         */
        public static final IAttribute CoolDown = new Attribute()
                .setRegisterName(Names.Attribute.Generic.CoolDown)
                .setHowToGetI18nKey(I18ns.Attribute::Generic)
                .setBasic()
                .setDataType(DataType.Int)
                .setFormat("%.1f")
                .setComputeType(ComputeType.Only_Base)
                .setHasUnit(I18ns.Tooltip.Unit.Second)
                .setNeedMoreDetailsToShown()
                .setTooltipShownMapping(Vanilla::PerSecond)
                .setDisplayPriority(-25)
                .setDefaultValue(0);
        /**
         * Gets the knock back. The basic is 1.0F.
         */
        public static final IAttribute KnockbackStrength = new Attribute()
                .setRegisterName(Names.Attribute.Generic.KnockbackStrength)
                .setBasic()
                .setDataType(DataType.Float)
                .setComputeType(ComputeType.Full)
                .setShownInTooltip(false)
                .setDefaultValue(1);

        public static final IAttribute DropsFireproof = new BoolAttribute()
                .setRegisterName(Names.Attribute.Generic.DropsFireproof)
                .setBasic()
                .setComputeType(ComputeType.Only_Gemstone)
                .setOnlyGemstoneCompute((base, modifier) -> base || modifier)
                .setDefaultValue(false);

        public static final IAttribute SpecialAttackReachJudgment = new BoolAttribute()
                .setRegisterName(Names.Attribute.Generic.SpecialAttackReachJudgment)
                .setBasic()
                .setComputeType(ComputeType.Only_Base)
                .setDefaultValue(true);

        public static final IAttribute MaxUseDuration = new Attribute()
                .setRegisterName(Names.Attribute.Generic.MaxUseDuration)
                .setBasic()
                .setDataType(DataType.Int)
                .setComputeType(ComputeType.Only_Base)
                .setShownInTooltip(false)
                .setDefaultValue(0);
    }

    public static final class Lance {
        /**
         * The float value of this is the Strength of a sprint.
         */
        public static final IAttribute SprintStrength = new Attribute()
                .setRegisterName(Names.Attribute.Lance.SprintStrength)
                .setHowToGetI18nKey(I18ns.Attribute::Lance)
                .setDataType(DataType.Float)
                .setFormat("%.1f")
                .setComputeType(ComputeType.Full)
                .setDisplayPriority(50)
                .setDefaultValue(0);
    }

    public static final class BattleAxe {
        public static final IAttribute SweepRange = new Attribute()
                .setRegisterName(Names.Attribute.BattleAxe.SweepRange)
                .setHowToGetI18nKey(I18ns.Attribute::BattleAxe)
                .setDataType(DataType.Float)
                .setFormat("%.1f")
                .setComputeType(ComputeType.Full)
                .setDisplayPriority(50)
                .setDefaultValue(0F);
    }

    @LongSupport
    public static final class Harp {
        /**
         * Gets the radius of the range
         */
        public static final IAttribute Radius = new Attribute()
                .setRegisterName(Names.Attribute.Harp.Radius)
                .setHowToGetI18nKey(I18ns.Attribute::Harp)
                .setDataType(DataType.Float)
                .setFormat("%.1f")
                .setHasUnit(I18ns.Tooltip.Unit.Unit)
                .setComputeType(ComputeType.Full)
                .setDisplayPriority(50)
                .setDefaultValue(1);
        /**
         * The default one equals 60 minutes
         */
        public static final IAttribute Frequency = new Attribute()
                .setRegisterName(Names.Attribute.Harp.Frequency)
                .setHowToGetI18nKey(I18ns.Attribute::Harp)
                .setDataType(DataType.Int)
                .setFormat("%.1f")
                .setHasUnit(I18ns.Tooltip.Unit.TriggerPerSecond)
                .setNeedMoreDetailsToShown()
                .setComputeType(ComputeType.Only_Rate)
                .setTooltipShownMapping(Vanilla::PerSecond)
                .setDefaultValue(72000)
                .setDisplayPriority(60)
                .setMinimum(1);
    }

    @LongSupport
    public static final class Sword {
        public static final IAttribute Sweep = new Attribute()
                .setRegisterName(Names.Attribute.Sword.Sweep)
                .setHowToGetI18nKey(I18ns.Attribute::Sword)
                .setDataType(DataType.Float)
                .setFormat("%.1f")
                .setComputeType(ComputeType.Full)
                .setDisplayPriority(50)
                .setDefaultValue(0F);
    }

    @LongSupport
    public static final class Bow {
        public static final IAttribute ProjectileVelocity = new Attribute()
                .setRegisterName(Names.Attribute.Bow.ProjectileVelocity)
                .setDataType(DataType.Float)
                .setDefaultValue(1F)
                .setComputeType(ComputeType.Full)
                .setMinimum(0F)
                .setShownInTooltip(false);
    }

    //You must call it to load this class and all the static fields.
    public static void load() {
    }
}
