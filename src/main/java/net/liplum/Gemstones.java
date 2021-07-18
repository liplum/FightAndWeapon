package net.liplum;

import net.liplum.api.weapon.Gemstone;
import net.liplum.api.weapon.IGemstone;
import net.liplum.modifiers.EnderGemModifier;
import net.liplum.modifiers.FlameGemModifier;
import net.liplum.modifiers.RubyModifier;
import net.liplum.skills.passive.UndeterminedSkills;

import static net.liplum.skills.passive.gemstone.FlamegemSkills.FireProof;
import static net.liplum.skills.passive.gemstone.FlamegemSkills.ScorchingTouch;
import static net.liplum.skills.passive.gemstone.ForestgemSkills.NutrientAbsorption;
import static net.liplum.skills.passive.gemstone.MagicPearlSkills.Magicize;
import static net.liplum.skills.passive.gemstone.RoseQuartzSkills.MagicAttach;
import static net.liplum.skills.passive.gemstone.RubySkills.FireResistance;
import static net.liplum.skills.passive.gemstone.TurquoiseSkills.GentlyLand;
import static net.liplum.skills.passive.gemstone.WindyGemstoneSkills.Feather;
import static net.liplum.skills.passive.gemstone.WindyGemstoneSkills.Levitation;

public final class Gemstones {
    public final static IGemstone Ruby_Gemstone = new Gemstone(Names.Gemstone.Ruby)
            .addModifier(RubyModifier.Light_Lance)
            .addPassiveSkillToAll(FireResistance);
    public final static IGemstone Aquamarine_Gemstone = new Gemstone(Names.Gemstone.Aquamarine);
    public final static IGemstone Citrine_Gemstone = new Gemstone(Names.Gemstone.Citrine);
    public final static IGemstone Jadeite_Gemstone = new Gemstone(Names.Gemstone.Jadeite);
    public final static IGemstone Amethyst_Gemstone = new Gemstone(Names.Gemstone.Amethyst);
    public final static IGemstone Rose_Quartz_Gemstone = new Gemstone(Names.Gemstone.RoseQuartz)
            .addPassiveSkillToAll(MagicAttach);
    public final static IGemstone Turquoise_Gemstone = new Gemstone(Names.Gemstone.Turquoise)
            .addPassiveSkillToAll(GentlyLand);

    public final static IGemstone Flamegem_Gemstone = new Gemstone(Names.Gemstone.Flamegem)
            .addModifier(FlameGemModifier.Light_Lance)
            .addPassiveSkillToAll(FireProof)
            .addPassiveSkillToAll(ScorchingTouch);

    public final static IGemstone Marinegem_Gemstone = new Gemstone(Names.Gemstone.Marinegem)
            .addPassiveSkillToAll(UndeterminedSkills.XpMending);

    public final static IGemstone Earthgem_Gemstone = new Gemstone(Names.Gemstone.Earthgem);

    public final static IGemstone Forestgem_Gemstone = new Gemstone(Names.Gemstone.Forestgem)
            .addPassiveSkillToAll(NutrientAbsorption);

    public final static IGemstone Endergem_Gemstone = new Gemstone(Names.Gemstone.Endergem)
            .addModifier(EnderGemModifier.Light_Lance);

    public final static IGemstone Magic_Pearl_Gemstone = new Gemstone(Names.Gemstone.MagicPearl)
            .addPassiveSkillToAll(Magicize);
    public final static IGemstone Windy_Gemstone = new Gemstone(Names.Gemstone.WindyGemstone)
            .addPassiveSkillToAll(Levitation)
            .addPassiveSkillToAll(Feather);

    //You must call it to load this class and all the static fields.
    public static void load() {
    }
}
