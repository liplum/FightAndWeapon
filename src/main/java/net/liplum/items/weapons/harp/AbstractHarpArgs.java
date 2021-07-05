package net.liplum.items.weapons.harp;

import net.liplum.api.weapon.MagicToolArgs;

public abstract class AbstractHarpArgs<This extends AbstractHarpArgs> extends MagicToolArgs<This> {
    private double radius = 0;

    public AbstractHarpArgs() {
    }

    public double getRadius() {
        return radius;
    }

    public This setRadius(double radius) {
        this.radius = radius;
        return (This) this;
    }
}
