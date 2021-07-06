package net.liplum.items.weapons.harp;

import net.liplum.Attributes;
import net.liplum.api.weapon.MagicToolModifier;
import net.liplum.attributes.Attribute;

import java.util.LinkedList;
import java.util.List;

public abstract class HarpModifier extends MagicToolModifier<HarpCore> {
    @Override
    protected void buildAttributes(AttributeBuilder builder) {

    }

    @Override
    protected List<Attribute> initAllAttributes() {
        LinkedList<Attribute> list = new LinkedList<>();
        list.add(Attributes.Harp.Frequency);
        list.add(Attributes.Harp.MaxUseDuration);
        list.add(Attributes.Harp.Radius);
        return list;
    }

    public boolean continueSkill(HarpCore core, ContinuousHarpArgs args) {
        return core.continueSkill(args);
    }

    public boolean releaseSkill(HarpCore core, SingleHarpArgs args) {
        return core.releaseSkill(args);
    }
}
