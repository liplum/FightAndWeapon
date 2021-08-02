package net.liplum;

import net.liplum.api.annotations.LongSupport;
import net.liplum.api.annotations.OnlyWhenInitialization;
import net.liplum.api.weapon.Gemstone;
import net.liplum.api.weapon.IGemstone;
import net.liplum.modifiers.EnderGemModifier;
import net.liplum.modifiers.FlameGemModifier;
import net.liplum.modifiers.RubyModifier;
import net.liplum.skills.UndeterminedSkills;

import static net.liplum.Names.Gemstone.*;
import static net.liplum.WeaponTypes.*;
import static net.liplum.api.weapon.GemQuality.Low;
import static net.liplum.api.weapon.GemQuality.Middle;
import static net.liplum.skills.gemstone.FlamegemSkills.FireProof;
import static net.liplum.skills.gemstone.FlamegemSkills.ScorchingTouch;
import static net.liplum.skills.gemstone.ForestgemSkills.NutrientAbsorption;
import static net.liplum.skills.gemstone.MagicPearlSkills.Magicize;
import static net.liplum.skills.gemstone.RoseQuartzSkills.MagicAttach;
import static net.liplum.skills.gemstone.RubySkills.FireResistance;
import static net.liplum.skills.gemstone.TurquoiseSkills.GentlyLand;
import static net.liplum.skills.gemstone.WindyGemstoneSkills.Feather;
import static net.liplum.skills.gemstone.WindyGemstoneSkills.Levitation;

@LongSupport
public final class Gemstones {
    private static int ID = 0;
    public final static IGemstone Ruby_Gemstone = new Gemstone(Ruby, Low, ID++)
            .addModifier(RubyModifier.Light_Lance)
            .addPassiveSkillsToAll(FireResistance);
    public final static IGemstone Aquamarine_Gemstone = new Gemstone(Aquamarine, Low, ID++);
    public final static IGemstone Citrine_Gemstone = new Gemstone(Citrine, Low, ID++);
    public final static IGemstone Jadeite_Gemstone = new Gemstone(Jadeite, Low, ID++);
    public final static IGemstone Amethyst_Gemstone = new Gemstone(Amethyst, Low, ID++);
    public final static IGemstone Rose_Quartz_Gemstone = new Gemstone(RoseQuartz, Low, ID++)
            .addPassiveSkillsToAll(MagicAttach);
    public final static IGemstone Turquoise_Gemstone = new Gemstone(Turquoise, Low, ID++)
            .addPassiveSkillsToAll(GentlyLand);

    public final static IGemstone Flamegem_Gemstone = new Gemstone(Flamegem, Middle, ID++)
            .addModifier(FlameGemModifier.Light_Lance)
            .addPassiveSkillsToAll(FireProof)
            .addPassiveSkillToWeaponTypes(ScorchingTouch, Lance, BattleAxe, Sword);

    public final static IGemstone Marinegem_Gemstone = new Gemstone(Marinegem, Middle, ID++)
            .addPassiveSkillsToAll(UndeterminedSkills.XpMending);

    public final static IGemstone Earthgem_Gemstone = new Gemstone(Earthgem, Middle, ID++);

    public final static IGemstone Forestgem_Gemstone = new Gemstone(Forestgem, Middle, ID++)
            .addPassiveSkillsToAll(NutrientAbsorption);

    public final static IGemstone Endergem_Gemstone = new Gemstone(Endergem, Middle, ID++)
            .addModifier(EnderGemModifier.Light_Lance);

    public final static IGemstone Magic_Pearl_Gemstone = new Gemstone(MagicPearl, Middle, ID++)
            .addPassiveSkillsToAll(Magicize);
    public final static IGemstone Windy_Gemstone = new Gemstone(WindyGemstone, Middle, ID++)
            .addPassiveSkillsToAll(Levitation)
            .addPassiveSkillsToAll(Feather);

    //You must call it to load this class and all the static fields.
    @OnlyWhenInitialization
    public static void load() {

    }
}
