package net.liplum;

import net.liplum.api.registeies.GemstoneRegistry;
import net.liplum.api.weapon.IGemstone;
import net.liplum.lib.items.gemstone.Gemstone;
import net.liplum.modifiers.EnderGemModifier;
import net.liplum.modifiers.RubyModifier;
import net.liplum.skills.passive.gemstone.*;

public final class Gemstones {
    public final static IGemstone Ruby_Gemstone = with(new Gemstone(Names.Gemstone.Ruby)
            .addModifier(RubyModifier.Light_Lance)
            .addPassiveSkillToAll(RubySkills.FireResistance)
    );
    public final static IGemstone Aquamarine_Gemstone = with(new Gemstone(Names.Gemstone.Aquamarine)
    );
    public final static IGemstone Citrine_Gemstone = with(new Gemstone(Names.Gemstone.Citrine)
    );
    public final static IGemstone Jadeite_Gemstone = with(new Gemstone(Names.Gemstone.Jadeite)
    );
    public final static IGemstone Amethyst_Gemstone = with(new Gemstone(Names.Gemstone.Amethyst)
    );
    public final static IGemstone Rose_Quartz_Gemstone = with(new Gemstone(Names.Gemstone.RoseQuartz)
            .addPassiveSkillToAll(RoseQuartzSkills.MagicAttach)
    );
    public final static IGemstone Turquoise_Gemstone = with(new Gemstone(Names.Gemstone.Turquoise)
            .addPassiveSkillToAll(TurquoiseSkills.GentlyLand)
    );

    public final static IGemstone Flamegem_Gemstone = with(new Gemstone(Names.Gemstone.Flamegem)
            .addPassiveSkillToAll(FlamegemSkills.FireProof)
    );


    public final static IGemstone Endergem_Gemstone = with(new Gemstone(Names.Gemstone.Endergem)
            .addModifier(EnderGemModifier.Normal_Lance)
    );

    public final static IGemstone Magic_Pearl_Gemstone = with(new Gemstone(Names.Gemstone.MagicPearl)
            .addPassiveSkillToAll(MagicPearlSkills.Magicize)
    );
    public final static IGemstone Windy_Gemstone = with(new Gemstone(Names.Gemstone.WindyGemstone)
            .addPassiveSkillToAll(WindyGemstoneSkills.Levitation)
            .addPassiveSkillToAll(WindyGemstoneSkills.Feather)
    );

    //You must call it to load this class and all the static fields.
    public static void load() {
    }

    private static IGemstone with(IGemstone gemstone) {
        GemstoneRegistry.register(gemstone);
        return gemstone;
    }
}
