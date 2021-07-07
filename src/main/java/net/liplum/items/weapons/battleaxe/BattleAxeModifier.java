package net.liplum.items.weapons.battleaxe;

import net.liplum.Attributes;
import net.liplum.api.weapon.Modifier;
import net.liplum.attributes.Attribute;

import java.util.List;

public abstract class BattleAxeModifier extends Modifier<BattleAxeCore> {
    @Override
    protected void initAllAttributes(List<Attribute> attributes) {
        super.initAllAttributes(attributes);
        attributes.add(Attributes.BattleAxe.SweepRange);
    }

    public boolean releaseSkill(BattleAxeCore core, BattleAxeArgs args) {
        return core.releaseSkill(args);
    }

    @Override
    protected void buildAttributes(AttributeBuilder builder) {

    }
}
