package net.liplum.items.weapons.harp;

import net.liplum.api.weapon.MagicToolModifier;
import net.liplum.attributes.IAttribute;

import java.util.List;

import static net.liplum.Attributes.Generic.MaxUseDuration;
import static net.liplum.Attributes.Harp.Frequency;
import static net.liplum.Attributes.Harp.Radius;


public abstract class HarpModifier extends MagicToolModifier {
    @Override
    protected void build(ModifierBuilder builder) {
        super.build(builder);
    }

    @Override
    protected void initAllAttributes(List<IAttribute> attributes) {
        super.initAllAttributes(attributes);
        attributes.add(Frequency);
        attributes.add(MaxUseDuration);
        attributes.add(Radius);
    }

    public boolean continueSkill(HarpCore core, ContinuousHarpArgs args) {
        return core.continueSkill(args);
    }
}
