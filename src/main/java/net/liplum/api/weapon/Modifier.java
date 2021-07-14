package net.liplum.api.weapon;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.liplum.attributes.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public abstract class Modifier implements IAttributeProvider<AttrModifier> {
    @Nonnull
    protected final Multimap<String, Function<WeaponAttrModifierContext, AttributeModifier>> mainHandAttributeModifierMap = HashMultimap.create();
    @Nonnull
    protected final Multimap<String, Function<WeaponAttrModifierContext, AttributeModifier>> offHandAttributeModifierMap = HashMultimap.create();
    @Nonnull
    private final Map<IAttribute, AttrModifier> AttributeModifierMap = new HashMap<>();
    @Nonnull
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

    public void applyAttrModifier(@Nonnull WeaponCore weaponCore,
                                  @Nonnull WeaponAttrModifierContext context) {
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
    public AttrModifier getValue(@Nonnull IAttribute attribute) {
        return AttributeModifierMap.get(attribute);
    }

    protected abstract void build(ModifierBuilder builder);

    public boolean releaseSkill(WeaponCore core, WeaponSkillArgs args) {
        return core.releaseSkill(args);
    }

    public abstract WeaponCore getCore();

    protected class ModifierBuilder implements IAttrModifierBuilder {
        @Override
        public ModifierBuilder set(@Nonnull IAttribute attribute, @Nonnull AttrModifier modifier) {
            AttributeModifierMap.put(attribute, modifier);
            return this;
        }

        public ModifierBuilder addMainHand(net.minecraft.entity.ai.attributes.IAttribute attr, Function<WeaponAttrModifierContext, AttributeModifier> modifierGetter) {
            mainHandAttributeModifierMap.put(attr.getName(), modifierGetter);
            return this;
        }

        public ModifierBuilder addOffHand(net.minecraft.entity.ai.attributes.IAttribute attr, Function<WeaponAttrModifierContext, AttributeModifier> modifierGetter) {
            offHandAttributeModifierMap.put(attr.getName(), modifierGetter);
            return this;
        }

        public ModifierBuilder setNotApplyCoreAttrModifier() {
            applyCoreAttrModifier = false;
            return this;
        }
    }
}
