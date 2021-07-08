package net.liplum;

import net.liplum.api.weapon.Gemstone;
import net.liplum.api.weapon.IGemstone;
import net.liplum.modifiers.EnderGemModifier;
import net.liplum.modifiers.RubyModifier;
import net.liplum.skills.passive.gemstone.*;

public final class Gemstones {
    public final static IGemstone Ruby_Gemstone = new Gemstone(Names.Gemstone.Ruby)
            .addModifier(RubyModifier.Light_Lance)
            .addPassiveSkillToAll(RubySkills.FireResistance);
    public final static IGemstone Aquamarine_Gemstone = new Gemstone(Names.Gemstone.Aquamarine);
    public final static IGemstone Citrine_Gemstone = new Gemstone(Names.Gemstone.Citrine);
    public final static IGemstone Jadeite_Gemstone = new Gemstone(Names.Gemstone.Jadeite);
    public final static IGemstone Amethyst_Gemstone = new Gemstone(Names.Gemstone.Amethyst);
    public final static IGemstone Rose_Quartz_Gemstone = new Gemstone(Names.Gemstone.RoseQuartz)
            .addPassiveSkillToAll(RoseQuartzSkills.MagicAttach);
    public final static IGemstone Turquoise_Gemstone = new Gemstone(Names.Gemstone.Turquoise)
            .addPassiveSkillToAll(TurquoiseSkills.GentlyLand);

    public final static IGemstone Flamegem_Gemstone = new Gemstone(Names.Gemstone.Flamegem)
            .addPassiveSkillToAll(FlamegemSkills.FireProof)
            .addPassiveSkillToAll(FlamegemSkills.ScorchingTouch);

    public final static IGemstone Marinegem_Gemstone = new Gemstone(Names.Gemstone.Marinegem);

    public final static IGemstone Earthgem_Gemstone = new Gemstone(Names.Gemstone.Earthgem);

    public final static IGemstone Forestgem_Gemstone = new Gemstone(Names.Gemstone.Forestgem)
            .addPassiveSkillToAll(ForestgemSkills.NutrientAbsorption);

    public final static IGemstone Endergem_Gemstone = new Gemstone(Names.Gemstone.Endergem)
            .addModifier(EnderGemModifier.Normal_Lance);

    public final static IGemstone Magic_Pearl_Gemstone = new Gemstone(Names.Gemstone.MagicPearl)
            .addPassiveSkillToAll(MagicPearlSkills.Magicize);
    public final static IGemstone Windy_Gemstone = new Gemstone(Names.Gemstone.WindyGemstone)
            .addPassiveSkillToAll(WindyGemstoneSkills.Levitation)
            .addPassiveSkillToAll(WindyGemstoneSkills.Feather);

    //You must call it to load this class and all the static fields.
    public static void load() {
    }
}
