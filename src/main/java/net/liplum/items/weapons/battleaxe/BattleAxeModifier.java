package net.liplum.items.weapons.battleaxe;

import net.liplum.Attributes;
import net.liplum.api.weapon.Modifier;
import net.liplum.attributes.IAttribute;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class BattleAxeModifier extends Modifier {
    @Override
    protected void initAllAttributes(List<IAttribute> attributes) {
        super.initAllAttributes(attributes);
        attributes.add(Attributes.BattleAxe.SweepRange);
    }

    @Override
    protected void build(@NotNull ModifierBuilder builder) {
        super.build(builder);
    }
}
