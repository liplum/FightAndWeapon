package net.liplum;

import net.liplum.api.weapon.IGemstone;
import net.liplum.api.weapon.IWeaponCore;
import net.liplum.items.weapons.lance.LanceCoreTypes;
import net.liplum.lib.items.gemstone.Gemstone;
import net.liplum.api.registeies.GemstoneRegistry;
import net.liplum.modifiers.EnderGemModifier;
import net.liplum.modifiers.FlameGemModifier;
import net.liplum.modifiers.RubyModifier;
import net.liplum.skills.passive.FlameGemSkills;

public final class Gemstones {
    public final static IGemstone Ruby_Gemstone = with(new Gemstone(Names.Gemstone.Ruby)
            .addModifier(RubyModifier.Normal_Lance));
    public final static IGemstone Ender_Gemstone = with(new Gemstone(Names.Gemstone.Endergem)
            .addModifier(EnderGemModifier.Normal_Lance));
    public final static IGemstone Flame_Gemstone = with(new Gemstone(Names.Gemstone.Flamegem)
            .addModifier(FlameGemModifier.Normal_Lance))
            .addPassiveSkill(LanceCoreTypes.LightLance,FlameGemSkills.FireProof);

    //You must call it to load this class and all the static fields.
    public static void load() {
    }

    private static IGemstone with(IGemstone gemstone) {
        GemstoneRegistry.Instance().register(gemstone);
        return gemstone;
    }
}
