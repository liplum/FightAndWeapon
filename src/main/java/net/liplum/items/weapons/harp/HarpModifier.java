package net.liplum.items.weapons.harp;

import net.liplum.Attributes;
import net.liplum.api.weapon.MagicToolModifier;
import net.liplum.attributes.Attribute;

import java.util.List;

public abstract class HarpModifier extends MagicToolModifier {
    @Override
    protected void build(ModifierBuilder builder) {

    }

    @Override
    protected void initAllAttributes(List<Attribute> attributes) {
        super.initAllAttributes(attributes);
        attributes.add(Attributes.Harp.Frequency);
        attributes.add(Attributes.Harp.MaxUseDuration);
        attributes.add(Attributes.Harp.Radius);
    }

    public boolean continueSkill(HarpCore core, ContinuousHarpArgs args) {
        return core.continueSkill(args);
    }
}
