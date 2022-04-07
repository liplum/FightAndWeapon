package net.liplum.api.weapon;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.liplum.Names;
import net.liplum.Vanilla;
import net.liplum.api.annotations.Developing;
import net.liplum.api.annotations.LongSupport;
import net.liplum.api.fight.IPassiveSkill;
import net.liplum.api.fight.UnlockedPSkillList;
import net.liplum.attributes.*;
import net.liplum.lib.ItemProperty;
import net.liplum.lib.utils.GemUtil;
import net.liplum.lib.utils.ItemUtil;
import net.liplum.tooltips.AggregateThroughable;
import net.liplum.tooltips.TooltipPipe;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Function;

import static net.liplum.Attributes.Generic.*;
import static net.liplum.TooltipMiddlewares.*;

@LongSupport
public abstract class WeaponCore implements IAttributeProvider<BasicAttrValue> {
    @NotNull
    public static final Function<WeaponAttrModifierContext, AttributeModifier> AttackReachModifier = context -> {
        float attackSpeed = context.calculator.calcu(AttackSpeed).getFloat();
        if (AttackSpeed.isNotDefaultValue(attackSpeed)) {
            double attackSpeedDelta = attackSpeed - Vanilla.DefaultAttackSpeed;
            return ItemUtil.genAttrModifier(
                    Item.ATTACK_SPEED_MODIFIER,
                    Vanilla.AttrModifierType.DeltaAddition,
                    Names.WeaponAttributeModifier, attackSpeedDelta);
        }
        return null;
    };
    @NotNull
    private static final ILeftClickEntityBehavior DefaultLeftClickEntityBehavior = (weapon, stack, attacker, target) -> {
        AttrCalculator calculator = new AttrCalculator(weapon);
        if (BoolAttribute.toBool(calculator.calcu(SpecialAttackReachJudgment))) {
            Modifier modifier = GemUtil.getModifierFrom(stack);
            calculator.modifier(modifier)
                    .entity(attacker);
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
    @NotNull
    private static final TooltipPipe DefaultPipe = new TooltipPipe()
            .addMiddleware(AutoAddSpaceLine)
            .addMiddleware(new AggregateThroughable(ShowWeaponType, ShowGemstone, ShowWeaponSkillTip))
            .addMiddleware(ShowAttributes)
            .addMiddleware(ShowPassiveSkills);
    @NotNull
    protected final Multimap<String, Function<WeaponAttrModifierContext, AttributeModifier>> mainHandAttributeModifierMap = HashMultimap.create();
    @NotNull
    protected final Multimap<String, Function<WeaponAttrModifierContext, AttributeModifier>> offHandAttributeModifierMap = HashMultimap.create();
    @NotNull
    private final Map<IAttribute, BasicAttrValue> AttributeValueMap = new HashMap<>();
    @NotNull
    private final List<IAttribute> allAttributes = new LinkedList<>();
    @NotNull
    private final List<IPassiveSkill<?>> weaponPassiveSkills = new LinkedList<>();
    @NotNull
    private final Map<Integer, IPassiveSkill<?>> lockedPassiveSkills = new HashMap<>();
    @NotNull
    private final List<ItemProperty> itemProperties = new LinkedList<>();
    @NotNull
    private final String registerName;
    private final boolean hasWeaponSkill;
    @NotNull
    private IWeaponSkillPredicate weaponSkillPredicate;
    @NotNull
    private EnumAction rightClickUseAction = EnumAction.NONE;
    @NotNull
    private ILeftClickEntityBehavior leftClickEntityBehavior = DefaultLeftClickEntityBehavior;
    @NotNull
    private TooltipPipe tooltipPipe = TooltipPipe.Empty;

    private boolean hasContinuousEffect = false;

    @LongSupport
    public WeaponCore(@NotNull String registerName) {
        this(registerName, true);
    }

    @LongSupport
    public WeaponCore(@NotNull String registerName, boolean hasWeaponSkill) {
        this.registerName = registerName;
        this.hasWeaponSkill = hasWeaponSkill;
        weaponSkillPredicate = getWeaponType().getWeaponSkillPredicate();
        initAllAttributes(allAttributes);

        WeaponCoreBuilder builder = new WeaponCoreBuilder();
        for (IAttribute attribute : allAttributes) {
            builder.set(attribute, attribute.emptyBasicAttrValue());
        }
        build(builder);
    }

    @NotNull
    @LongSupport
    public List<IPassiveSkill<?>> getWeaponPassiveSkills() {
        return weaponPassiveSkills;
    }

    @NotNull
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

    @NotNull
    @LongSupport
    public List<IAttribute> getAllAttributes() {
        return allAttributes;
    }

    @LongSupport
    public boolean hasWeaponSkill() {
        return hasWeaponSkill;
    }

    @NotNull
    @LongSupport
    public EnumAction getRightClickUseAction() {
        return rightClickUseAction;
    }

    @NotNull
    @LongSupport
    public List<ItemProperty> getItemProperties() {
        return itemProperties;
    }

    @NotNull
    @Developing
    public Set<IPassiveSkill<?>> unlockPassiveSkills(@NotNull UnlockedPSkillList list) {
        Set<IPassiveSkill<?>> skills = new HashSet<>();
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
    @NotNull
    @Override
    public BasicAttrValue getValue(@NotNull IAttribute attribute) {
        BasicAttrValue basicAttrValue = AttributeValueMap.get(attribute);
        if (basicAttrValue == null) {
            throw new NotHasSuchAttributeException(attribute);
        }
        return basicAttrValue;
    }

    @LongSupport
    public void applyAttrModifier(@NotNull WeaponAttrModifierContext context) {
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
     * It will be called in constructor at {@link WeaponCore}.<br/>
     * NOTE:If you override it then must call {@code super.build(builder);} to construct parent's one.<br/>
     * And in its subclass, the filed initialization will start after this method call so that
     * you must be careful. Don't access any filed in subclass in this method because it must be null.
     *
     * @param builder the weapon core builder
     */
    @LongSupport
    protected void build(@NotNull WeaponCoreBuilder builder) {
        builder.addMainHand(
                        SharedMonsterAttributes.ATTACK_SPEED, AttackReachModifier)
                .set(Durability, 100)
                .set(DefaultPipe);
    }

    /**
     * Release the Weapon Skill
     *
     * @param args the parameter the skill releasing may use
     * @return whether the skill released successfully
     */
    @LongSupport
    public boolean releaseSkill(@NotNull WeaponSkillArgs args) {
        return false;
    }

    public boolean hasContinuousEffect() {
        return hasContinuousEffect;
    }

    @Developing
    public boolean onContinuousEffectStop(@NotNull WeaponSkillArgs args, int totalTicksUsed, int timeLeft) {
        return false;
    }

    @Developing
    @NotNull
    public ItemStack onContinuousEffectFinish(@NotNull WeaponSkillArgs args) {
        return args.itemStack();
    }

    @Developing
    public void onContinuousEffectTick(@NotNull WeaponSkillArgs args, int totalTicksUsed) {
    }

    @NotNull
    @LongSupport
    public IWeaponSkillPredicate getWeaponSkillPredicate() {
        return weaponSkillPredicate;
    }

    @NotNull
    public TooltipPipe getTooltipPipe() {
        return tooltipPipe;
    }

    @NotNull
    @LongSupport
    public String getRegisterName() {
        return registerName;
    }

    @NotNull
    @LongSupport
    public abstract WeaponType getWeaponType();

    @LongSupport
    protected class WeaponCoreBuilder implements IBasicAttrValueBuilder {
        @Override
        @NotNull
        public WeaponCoreBuilder set(@NotNull IAttribute attribute, @NotNull BasicAttrValue value) {
            AttributeValueMap.put(attribute, value);
            return this;
        }

        @NotNull
        @Override
        public WeaponCoreBuilder set(@NotNull IAttribute attribute, @NotNull Number value) {
            return (WeaponCoreBuilder) IBasicAttrValueBuilder.super.set(attribute, value);
        }

        @NotNull
        @LongSupport
        public WeaponCoreBuilder add(@NotNull IPassiveSkill<?> passiveSkill) {
            weaponPassiveSkills.add(passiveSkill);
            return this;
        }

        @NotNull
        @LongSupport
        public WeaponCoreBuilder set(@NotNull IWeaponSkillPredicate newWeaponSkillPredicate) {
            weaponSkillPredicate = newWeaponSkillPredicate;
            return this;
        }

        @NotNull
        @LongSupport
        public WeaponCoreBuilder addMainHand(@NotNull net.minecraft.entity.ai.attributes.IAttribute attr,
                                             @NotNull Function<WeaponAttrModifierContext, AttributeModifier> modifierGetter) {
            mainHandAttributeModifierMap.put(attr.getName(), modifierGetter);
            return this;
        }

        @NotNull
        @LongSupport
        public WeaponCoreBuilder addOffHand(@NotNull net.minecraft.entity.ai.attributes.IAttribute attr,
                                            @NotNull Function<WeaponAttrModifierContext, AttributeModifier> modifierGetter) {
            offHandAttributeModifierMap.put(attr.getName(), modifierGetter);
            return this;
        }

        @NotNull
        @LongSupport
        public WeaponCoreBuilder set(@NotNull ILeftClickEntityBehavior behavior) {
            leftClickEntityBehavior = behavior;
            return this;
        }

        @NotNull
        @LongSupport
        public WeaponCoreBuilder set(@NotNull EnumAction action) {
            rightClickUseAction = action;
            return this;
        }

        @NotNull
        @LongSupport
        public WeaponCoreBuilder set(@NotNull TooltipPipe pipe) {
            tooltipPipe = pipe;
            return this;
        }

        @NotNull
        @LongSupport
        public WeaponCoreBuilder add(int number, @NotNull IPassiveSkill<?> passiveSkill) {
            lockedPassiveSkills.put(number, passiveSkill);
            return this;
        }

        @NotNull
        @LongSupport
        public WeaponCoreBuilder add(@NotNull ItemProperty itemProperty) {
            itemProperties.add(itemProperty);
            return this;
        }

        @NotNull
        @LongSupport
        public WeaponCoreBuilder setHasContinuousEffect(boolean has) {
            hasContinuousEffect = has;
            return this;
        }
    }
}
