package net.liplum.items.weapons.harp;

import net.liplum.Attributes;
import net.liplum.api.weapon.MagicToolCore;
import net.liplum.attributes.Attribute;

import javax.annotation.Nonnull;
import java.util.LinkedList;
import java.util.List;

public abstract class HarpCore extends MagicToolCore {
    /*
     */

    @Override
    protected void buildAttributes(AttributeBuilder builder) {

    }

    /**
     * Gets the radius of the range
     *
     * @return the radius of the skill.
     *//*

   public abstract double getRadius();


    */
    @Override
    protected List<Attribute> initAllAttributes() {
        LinkedList<Attribute> list = new LinkedList<>();
        list.add(Attributes.Harp.Frequency);
        list.add(Attributes.Harp.MaxUseDuration);
        list.add(Attributes.Harp.Radius);
        return list;
    }

    /**
     * The default one equals 60 minutes
     *
     * @return
     *//*
    public int getMaxUseDuration() {
        return 72000;
    }

    public abstract int getFrequency();*/
    public abstract boolean continueSkill(ContinuousHarpArgs args);

    public abstract boolean releaseSkill(SingleHarpArgs args);

    @Nonnull
    @Override
    public Class<HarpItem> getWeaponType() {
        return HarpItem.class;
    }
}
