package net.liplum.api.weapon;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.liplum.Names;
import net.liplum.Vanilla;
import net.liplum.api.fight.AggregatePassiveSkill;
import net.liplum.attributes.*;
import net.liplum.lib.utils.GemUtil;
import net.liplum.lib.utils.ItemTool;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static net.liplum.Attributes.Generic.*;

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

    private ILeftClickEntityBehavior leftClickEntityBehavior;

    public WeaponCore() {
        weaponSkillPredicate = getWeaponType().getWeaponSkillPredicate();
        initAllAttributes(allAttributes);

        WeaponCoreBuilder builder = new WeaponCoreBuilder();
        for (IAttribute attribute : allAttributes) {
            builder.set(attribute, attribute.emptyBasicAttrValue());
        }
        build(builder);
        if (weaponPassiveSkills != null) {
            weaponPassiveSkills.build();
        }
    }

    @Nullable
    public AggregatePassiveSkill getWeaponPassiveSkills() {
        return weaponPassiveSkills;
    }

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
    public List<IAttribute> getAllAttributes() {
        return allAttributes;
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

    /**
     * It will be called in constructor.
     *
     * @param builder the weapon core builder
     */
    protected void build(WeaponCoreBuilder builder) {
        builder.addMainHand(
                SharedMonsterAttributes.ATTACK_SPEED,
                context -> {
                    float attackSpeed = context.calculator.calcu(AttackSpeed).getFloat();
                    if (AttackSpeed.isNotDefaultValue(attackSpeed)) {
                        double attackSpeedDelta = attackSpeed - Vanilla.DefaultAttackSpeed;
                        return ItemTool.genAttrModifier(
                                Item.ATTACK_SPEED_MODIFIER,
                                Vanilla.AttrModifierType.DeltaAddition,
                                Names.WeaponAttributeModifier, attackSpeedDelta);
                    }
                    return null;
                })
                .set(Durability, Durability.newBasicAttrValue(100))
                .setLeftClickEntityBehavior(
                        ((weapon, stack, attacker, target) -> {
                            if (weapon.needSpecialAttackReachJudgment()) {
                                Modifier modifier = GemUtil.getModifierFrom(stack);
                                AttrCalculator calculator = new AttrCalculator()
                                        .setWeaponCore(this)
                                        .setModifier(modifier)
                                        .setPlayer(attacker);
                                FinalAttrValue finalAttackReach = calculator.calcu(AttackReach);
                                if (AttackReach.isDefaultValue(finalAttackReach)) {
                                    return weapon.attackEntity(stack, attacker, target);
                                } else {
                                    return true;
                                }
                            } else {
                                return weapon.attackEntity(stack, attacker, target);
                            }
                        })
                );
    }

    /**
     * Release the Weapon Skill
     *
     * @param args the parameter the skill releasing may use
     * @return whether the skill released successfully
     */
    public abstract boolean releaseSkill(WeaponSkillArgs args);

    @Nonnull
    public IWeaponSkillPredicate getWeaponSkillPredicate() {
        return weaponSkillPredicate;
    }

    @Nonnull
    public abstract WeaponType getWeaponType();

    protected class WeaponCoreBuilder implements IBasicAttrValueBuilder {
        @Override
        public WeaponCoreBuilder set(@Nonnull IAttribute attribute, @Nonnull BasicAttrValue value) {
            AttributeValueMap.put(attribute, value);
            return this;
        }

        public WeaponCoreBuilder addPassiveSkills(@Nonnull AggregatePassiveSkill passiveSkills) {
            WeaponCore thisCore = WeaponCore.this;
            if (thisCore.weaponPassiveSkills == null) {
                thisCore.weaponPassiveSkills = passiveSkills;
            } else {
                thisCore.weaponPassiveSkills.aggregate(passiveSkills);
            }
            return this;
        }

        public WeaponCoreBuilder setWeaponSkillPredicate(@Nonnull IWeaponSkillPredicate newWeaponSkillPredicate) {
            weaponSkillPredicate = newWeaponSkillPredicate;
            return this;
        }

        public WeaponCoreBuilder addMainHand(net.minecraft.entity.ai.attributes.IAttribute attr, Function<WeaponAttrModifierContext, AttributeModifier> modifierGetter) {
            mainHandAttributeModifierMap.put(attr.getName(), modifierGetter);
            return this;
        }

        public WeaponCoreBuilder addOffHand(net.minecraft.entity.ai.attributes.IAttribute attr, Function<WeaponAttrModifierContext, AttributeModifier> modifierGetter) {
            offHandAttributeModifierMap.put(attr.getName(), modifierGetter);
            return this;
        }

        public WeaponCoreBuilder setLeftClickEntityBehavior(@Nonnull ILeftClickEntityBehavior behavior) {
            leftClickEntityBehavior = behavior;
            return this;
        }
    }
}
