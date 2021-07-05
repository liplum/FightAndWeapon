package net.liplum.items.weapons.harp;

import net.liplum.api.weapon.MagicToolCore;

import javax.annotation.Nonnull;

public abstract class HarpCore extends MagicToolCore {
/*
*//**
     * Gets the radius of the range
     *
     * @return the radius of the skill.
     *//*

   public abstract double getRadius();


    *//**
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
