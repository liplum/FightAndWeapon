package net.liplum.api.weapon;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.liplum.Names;
import net.liplum.Vanilla;
import net.liplum.api.annotations.Developing;
import net.liplum.api.annotations.LongSupport;
import net.liplum.api.fight.AggregatePassiveSkill;
import net.liplum.api.fight.IPassiveSkill;
import net.liplum.api.fight.UnlockedPSkillList;
import net.liplum.attributes.*;
import net.liplum.lib.utils.GemUtil;
import net.liplum.lib.utils.ItemTool;
import net.liplum.tooltips.AggregateThroughable;
import net.liplum.tooltips.TooltipPipe;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static net.liplum.Attributes.Generic.*;
import static net.liplum.TooltipMiddlewares.*;

@LongSupport
public abstract class WeaponCore implements IAttributeProvider<BasicAttrValue> {
    @Nonnull
    protected final Multimap<String, Function<WeaponAttrModifierContext, AttributeModifier>> mainHandAttributeModifierMap = HashMultimap.create();
    @Nonnull
    protected final Multimap<String, Function<WeaponAttrModifierContext, AttributeModifier>> offHandAttributeModifierMap = HashMultimap.create();
    @Nonnull
    private final Map<IAttribute, BasicAttrValue> AttributeValueMap = new HashMap<>();
    @Nonnull
    private final List<IAttribute> allAttributes = new LinkedList<>();
    @Nonnull
    private IWeaponSkillPredicate weaponSkillPredicate;
    @Nullable
    private AggregatePassiveSkill weaponPassiveSkills;

    private boolean hasWeaponSkill = true;
    @Nonnull
    private String registerName = "default";
    @Nonnull
    private EnumAction rightClickUseAction = EnumAction.NONE;
    @Nonnull
    private final Map<Integer, IPassiveSkill<?>> lockedPassiveSkills = new HashMap<>();
    @Nonnull
    private ILeftClickEntityBehavior leftClickEntityBehavior = (weapon, stack, attacker, target) -> {
        AttrCalculator calculator = new AttrCalculator(this);
        if (BoolAttribute.toBool(calculator.calcu(SpecialAttackReachJudgment))) {
            Modifier modifier = GemUtil.getModifierFrom(stack);
            calculator.modifier(modifier)
                    .player(attacker);
            FinalAttrValue finalAttackReach = calculator.calcu(AttackReach);
            if (AttackReach.isDefaultValue(finalAttackReach)) {
                return weapon.attackEntity(stack, attacker, target);
            } else {
                return true;
            }
        } else {
            return weapon.attackEntity(stack, attacker, target);
        }
    };
    @Nonnull
    private TooltipPipe tooltipPipe = TooltipPipe.Empty;
    @Nonnull
    private static final TooltipPipe DefaultPipe = new TooltipPipe()
            .addMiddleware(AutoAddSpaceLine)
            .addMiddleware(new AggregateThroughable(ShowWeaponType, ShowGemstone, ShowWeaponSkillTip))
            .addMiddleware(ShowAttributes)
            .addMiddleware(ShowPassiveSkills);

    @LongSupport
    public WeaponCore() {
        weaponSkillPredicate = getWeaponType().getWeaponSkillPredicate();
        initAllAttributes(allAttributes);

        WeaponCoreBuilder builder = new WeaponCoreBuilder();
        for (IAttribute attribute : allAttributes) {
            builder.set(attribute, attribute.emptyBasicAttrValue());
        }
        build(builder);
        if (weaponPassiveSkills != null) {
            weaponPassiveSkills.setBanedWhenBroken(false).setTriggerPriority(Integer.MIN_VALUE).build();
        }
    }

    @Nullable
    public AggregatePassiveSkill getWeaponPassiveSkills() {
        return weaponPassiveSkills;
    }

    @Nonnull
    @LongSupport
    public ILeftClickEntityBehavior getLeftClickEntityBehavior() {
        return leftClickEntityBehavior;
    }

    /**
     * It will be called in constructor.
     *
     * @param attributes all the attributes
     */
    protected void initAllAttributes(List<IAttribute> attributes) {
        attributes.addAll(Attribute.getAllBasicAttributes());
    }

    @Nonnull
    @LongSupport
    public List<IAttribute> getAllAttributes() {
        return allAttributes;
    }

    @LongSupport
    public boolean hasWeaponSkill() {
        return hasWeaponSkill;
    }

    @Nonnull
    @LongSupport
    public EnumAction getRightClickUseAction() {
        return rightClickUseAction;
    }

    @Nonnull
    @Developing
    public List<IPassiveSkill<?>> unlockPassiveSkills(@Nonnull UnlockedPSkillList list) {
        List<IPassiveSkill<?>> skills = new LinkedList<>();
        for (int slot : list.getSlots()) {
            IPassiveSkill<?> skill = lockedPassiveSkills.get(slot);
            if (skill != null) {
                skills.add(skill);
            }
        }
        return skills;
    }

    /**
     * Get the corresponding value via the attribute
     *
     * @param attribute the attribute
     * @return the value or delta which can be used to compute final attribute value.<br/>
     * If this didn't contain any attribute value which can match the attribute type, it would throw {@link NotHasSuchAttributeException}.
     */
    @Nonnull
    @Override
    public BasicAttrValue getValue(@Nonnull IAttribute attribute) {
        BasicAttrValue basicAttrValue = AttributeValueMap.get(attribute);
        if (basicAttrValue == null) {
            throw new NotHasSuchAttributeException(attribute);
        }
        return basicAttrValue;
    }

    @LongSupport
    public void applyAttrModifier(@Nonnull WeaponAttrModifierContext context) {
        EntityEquipmentSlot slot = context.slot;
        Multimap<String, AttributeModifier> map = context.attrModifierMap;
        switch (slot) {
            case MAINHAND:
                for (Map.Entry<String, Function<WeaponAttrModifierContext, AttributeModifier>> entry : mainHandAttributeModifierMap.entries()) {
                    AttributeModifier modifier = entry.getValue().apply(context);
                    if (modifier != null) {
                        map.put(entry.getKey(), modifier);
                    }
                }
                break;
            case OFFHAND:
                for (Map.Entry<String, Function<WeaponAttrModifierContext, AttributeModifier>> entry : offHandAttributeModifierMap.entries()) {
                    AttributeModifier modifier = entry.getValue().apply(context);
                    if (modifier != null) {
                        map.put(entry.getKey(), modifier);
                    }
                }
        }
    }

    @Nonnull
    public static final Function<WeaponAttrModifierContext, AttributeModifier> AttackReachModifier = context -> {
        float attackSpeed = context.calculator.calcu(AttackSpeed).getFloat();
        if (AttackSpeed.isNotDefaultValue(attackSpeed)) {
            double attackSpeedDelta = attackSpeed - Vanilla.DefaultAttackSpeed;
            return ItemTool.genAttrModifier(
                    Item.ATTACK_SPEED_MODIFIER,
                    Vanilla.AttrModifierType.DeltaAddition,
                    Names.WeaponAttributeModifier, attackSpeedDelta);
        }
        return null;
    };

    /**
     * It will be called in constructor at {@link WeaponCore}.<br/>
     * NOTE:If you override it then must call {@code super.build(builder);} to construct parent's one.<br/>
     * And in its subclass, the filed initialization will start after this method call so that
     * you must be careful. Don't access any filed in subclass in this method because it must be null.
     *
     * @param builder the weapon core builder
     */
    @LongSupport
    protected void build(@Nonnull WeaponCoreBuilder builder) {
        builder.addMainHand(
                SharedMonsterAttributes.ATTACK_SPEED, AttackReachModifier)
                .set(Durability, Durability.newBasicAttrValue(100))
                .set(DefaultPipe);
    }

    /**
     * Release the Weapon Skill
     *
     * @param args the parameter the skill releasing may use
     * @return whether the skill released successfully
     */
    @LongSupport
    public abstract boolean releaseSkill(WeaponSkillArgs args);

    @Nonnull
    @LongSupport
    public IWeaponSkillPredicate getWeaponSkillPredicate() {
        return weaponSkillPredicate;
    }

    @Nonnull
    public TooltipPipe getTooltipPipe() {
        return tooltipPipe;
    }

    @Nonnull
    @LongSupport
    public String getRegisterName() {
        return registerName;
    }

    @Nonnull
    @LongSupport
    public abstract WeaponType getWeaponType();

    @LongSupport
    protected class WeaponCoreBuilder implements IBasicAttrValueBuilder {
        @Override
        @Nonnull
        @LongSupport
        public WeaponCoreBuilder set(@Nonnull IAttribute attribute, @Nonnull BasicAttrValue value) {
            AttributeValueMap.put(attribute, value);
            return this;
        }

        @Nonnull
        @LongSupport
        public WeaponCoreBuilder add(@Nonnull AggregatePassiveSkill passiveSkills) {
            WeaponCore thisCore = WeaponCore.this;
            if (thisCore.weaponPassiveSkills == null) {
                thisCore.weaponPassiveSkills = passiveSkills;
            } else {
                thisCore.weaponPassiveSkills.aggregate(passiveSkills);
            }
            return this;
        }

        @Nonnull
        public WeaponCoreBuilder set(@Nonnull IWeaponSkillPredicate newWeaponSkillPredicate) {
            weaponSkillPredicate = newWeaponSkillPredicate;
            return this;
        }

        @Nonnull
        public WeaponCoreBuilder addMainHand(@Nonnull net.minecraft.entity.ai.attributes.IAttribute attr, Function<WeaponAttrModifierContext, AttributeModifier> modifierGetter) {
            mainHandAttributeModifierMap.put(attr.getName(), modifierGetter);
            return this;
        }

        @Nonnull
        public WeaponCoreBuilder addOffHand(@Nonnull net.minecraft.entity.ai.attributes.IAttribute attr, Function<WeaponAttrModifierContext, AttributeModifier> modifierGetter) {
            offHandAttributeModifierMap.put(attr.getName(), modifierGetter);
            return this;
        }

        @Nonnull
        public WeaponCoreBuilder set(@Nonnull ILeftClickEntityBehavior behavior) {
            leftClickEntityBehavior = behavior;
            return this;
        }

        @Nonnull
        public WeaponCoreBuilder set(EnumAction action) {
            rightClickUseAction = action;
            return this;
        }

        @Nonnull
        public WeaponCoreBuilder set(@Nonnull TooltipPipe pipe) {
            tooltipPipe = pipe;
            return this;
        }

        @Nonnull
        public WeaponCoreBuilder set(boolean has) {
            hasWeaponSkill = has;
            return this;
        }

        @Nonnull
        @LongSupport
        public WeaponCoreBuilder set(@Nonnull String name) {
            registerName = name;
            return this;
        }

        @Nonnull
        @Developing
        public WeaponCoreBuilder set(int number, IPassiveSkill<?> passiveSkill) {
            lockedPassiveSkills.put(number, passiveSkill);
            return this;
        }
    }
}
