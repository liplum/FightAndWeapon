package net.liplum;

import net.liplum.lib.items.gemstone.Gemstone;
import net.liplum.lib.registeies.GemstoneRegistry;
import net.liplum.modifiers.EnderGemstoneModifier;

public final class Gemstones {
    public final static Gemstone Ender_Gemstone = with(new Gemstone("EnderGemstone")
            .addModifiers(EnderGemstoneModifier.Normal_Lance)
            .addModifierRegistries(ModifierRegistries.Lance));

    private static Gemstone with(Gemstone gemstone) {
        GemstoneRegistry.Instance().register(gemstone);
        return gemstone;
    }
}
