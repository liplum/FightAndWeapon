package net.liplum.api.weapon;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.liplum.attributes.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public abstract class Modifier implements IAttributeProvider<AttrModifier> {
    @NotNull
    protected final Multimap<String, Function<WeaponAttrModifierContext, AttributeModifier>> mainHandAttributeModifierMap = HashMultimap.create();
    @NotNull
    protected final Multimap<String, Function<WeaponAttrModifierContext, AttributeModifier>> offHandAttributeModifierMap = HashMultimap.create();
    @NotNull
    private final Map<IAttribute, AttrModifier> AttributeModifierMap = new HashMap<>();
    @NotNull
    protected List<IAttribute> allAttributes = new LinkedList<>();
    private boolean applyCoreAttrModifier = true;

    public Modifier() {
        initAllAttributes(allAttributes);

        ModifierBuilder builder = new ModifierBuilder();
        for (IAttribute attribute : allAttributes) {
            builder.set(attribute, attribute.emptyAttrModifier());
        }
        build(builder);
    }

    protected void initAllAttributes(List<IAttribute> attributes) {
        attributes.addAll(Attribute.getAllBasicAttributes());
    }

    public void applyAttrModifier(@NotNull WeaponCore weaponCore,
                                  @NotNull WeaponAttrModifierContext context) {
        EntityEquipmentSlot slot = context.slot;
        Multimap<String, AttributeModifier> map = context.attrModifierMap;
        if (applyCoreAttrModifier) {
            weaponCore.applyAttrModifier(context);
        }
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
     * Get the corresponding value via the attribute
     *
     * @param attribute the attribute
     * @return the value or delta which can be used to compute final attribute value.<br/>
     * If this didn't contain any attribute value which can match the attribute type, it would return null.
     */
    @Nullable
    @Override
    public AttrModifier getValue(@NotNull IAttribute attribute) {
        return AttributeModifierMap.get(attribute);
    }

    protected void build(@NotNull ModifierBuilder builder) {

    }

    public boolean releaseSkill(@NotNull WeaponCore core, @NotNull WeaponSkillArgs args) {
        return core.releaseSkill(args);
    }


    public boolean onStopUsing(@NotNull WeaponCore core, @NotNull WeaponSkillArgs args, int totalTimeUsed, int timeLeft) {
        return core.onContinuousEffectStop(args, totalTimeUsed, timeLeft);
    }

    public void onUsingEveryTick(@NotNull WeaponCore core, @NotNull WeaponSkillArgs args, int usedDuration) {
        core.onContinuousEffectTick(args, usedDuration);
    }

    public abstract WeaponCore getCore();

    protected class ModifierBuilder implements IAttrModifierBuilder {
        @NotNull
        @Override
        public ModifierBuilder set(@NotNull IAttribute attribute, @NotNull AttrModifier modifier) {
            AttributeModifierMap.put(attribute, modifier);
            return this;
        }

        @NotNull
        @Override
        public ModifierBuilder set(@NotNull IAttribute attribute, @NotNull Number delta, float rate) {
            return (ModifierBuilder) IAttrModifierBuilder.super.set(attribute, delta, rate);
        }

        @NotNull
        public ModifierBuilder addMainHand(net.minecraft.entity.ai.attributes.IAttribute attr, Function<WeaponAttrModifierContext, AttributeModifier> modifierGetter) {
            mainHandAttributeModifierMap.put(attr.getName(), modifierGetter);
            return this;
        }

        @NotNull
        public ModifierBuilder addOffHand(net.minecraft.entity.ai.attributes.IAttribute attr, Function<WeaponAttrModifierContext, AttributeModifier> modifierGetter) {
            offHandAttributeModifierMap.put(attr.getName(), modifierGetter);
            return this;
        }

        @NotNull
        public ModifierBuilder setNotApplyCoreAttrModifier() {
            applyCoreAttrModifier = false;
            return this;
        }
    }
}
