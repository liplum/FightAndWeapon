package net.liplum.lib.utils;

import net.liplum.api.fight.FawArgsGetter;
import net.liplum.attributes.AttrCalculator;

import javax.annotation.Nonnull;

public class FawUtil {
    public static AttrCalculator toCalculator(@Nonnull FawArgsGetter args) {
        return new AttrCalculator()
                .weapon(args.weapon())
                .modifier(args.modifier())
                .itemStack(args.itemStack())
                .entity(args.entity());
    }
}
