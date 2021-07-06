package net.liplum.items.weapons.harp;

import net.liplum.Attributes;
import net.liplum.WeaponTypes;
import net.liplum.api.weapon.MagicToolCore;
import net.liplum.api.weapon.WeaponType;
import net.liplum.attributes.Attribute;

import javax.annotation.Nonnull;
import java.util.LinkedList;
import java.util.List;

public abstract class HarpCore extends MagicToolCore {

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

    public abstract boolean continueSkill(ContinuousHarpArgs args);

    public abstract boolean releaseSkill(SingleHarpArgs args);

    @Nonnull
    @Override
    public WeaponType getWeaponType() {
        return WeaponTypes.Harp;
    }
}
