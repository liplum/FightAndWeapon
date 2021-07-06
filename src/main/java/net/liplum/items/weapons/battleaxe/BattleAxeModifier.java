package net.liplum.items.weapons.battleaxe;

import net.liplum.Attributes;
import net.liplum.api.weapon.Modifier;
import net.liplum.attributes.Attribute;

import java.util.LinkedList;
import java.util.List;

public abstract class BattleAxeModifier extends Modifier<BattleAxeCore> {
    @Override
    protected List<Attribute> initAllAttributes() {
        LinkedList<Attribute> list = new LinkedList<>();
        list.add(Attributes.BattleAxe.SweepRange);
        return list;
    }

    public boolean releaseSkill(BattleAxeCore core, BattleAxeArgs args) {
        return core.releaseSkill(args);
    }

    @Override
    protected void buildAttributes(AttributeBuilder builder) {

    }

    /* public float getSweepRangeDelta() {
        return 0;
    }

    public float getSweepRate() {
        return 0;
    }*/
}
