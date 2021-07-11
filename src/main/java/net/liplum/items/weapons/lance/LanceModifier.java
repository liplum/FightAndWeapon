package net.liplum.items.weapons.lance;

import net.liplum.Attributes;
import net.liplum.api.weapon.Modifier;
import net.liplum.api.weapon.WeaponCore;
import net.liplum.api.weapon.WeaponSkillArgs;
import net.liplum.attributes.Attribute;

import java.util.List;

public abstract class LanceModifier extends Modifier {
    @Override
    protected void initAllAttributes(List<Attribute> attributes) {
        super.initAllAttributes(attributes);
        attributes.add(Attributes.Lance.SprintStrength);
    }

    @Override
    protected void build(ModifierBuilder builder) {

    }
}
