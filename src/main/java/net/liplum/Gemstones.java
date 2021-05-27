package net.liplum;

import net.liplum.lib.items.gemstone.Gemstone;
import net.liplum.lib.registeies.GemstoneRegistry;
import net.liplum.modifiers.EnderGemstoneModifier;
import net.liplum.modifiers.RubyModifier;

public final class Gemstones {
    //You must call it to load this class and all the static fields.
    public static void load() {
    }

    public final static Gemstone Ender_Gemstone = with(new Gemstone("Endergem")
            .addModifiers(EnderGemstoneModifier.Normal_Lance)
            .addModifierRegistries(ModifierRegistries.Lance));
    public final static Gemstone Ruby_Gemstone = with(new Gemstone("Ruby")
            .addModifiers(RubyModifier.Normal_Lance)
            .addModifierRegistries(ModifierRegistries.Lance));

    private static Gemstone with(Gemstone gemstone) {
        GemstoneRegistry.Instance().register(gemstone);
        return gemstone;
    }
}
