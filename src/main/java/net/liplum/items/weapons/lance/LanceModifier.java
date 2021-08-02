package net.liplum.items.weapons.lance;

import net.liplum.Attributes;
import net.liplum.api.weapon.Modifier;
import net.liplum.attributes.IAttribute;

import javax.annotation.Nonnull;
import java.util.List;

public abstract class LanceModifier extends Modifier {
    @Override
    protected void initAllAttributes(List<IAttribute> attributes) {
        super.initAllAttributes(attributes);
        attributes.add(Attributes.Lance.SprintStrength);
    }

    @Override
    protected void build(@Nonnull ModifierBuilder builder) {
        super.build(builder);
    }
}
