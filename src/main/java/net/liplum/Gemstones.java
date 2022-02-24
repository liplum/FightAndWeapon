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
    public final static IGemstone Ruby = new Gemstone(RubyN, Low, ID++)
            .addModifier(RubyModifier.Light_Lance)
            .addPassiveSkillsToAll(FireResistance);
    public final static IGemstone Aquamarine = new Gemstone(AquamarineN, Low, ID++);
    public final static IGemstone Citrine = new Gemstone(CitrineN, Low, ID++);
    public final static IGemstone Jadeite = new Gemstone(JadeiteN, Low, ID++);
    public final static IGemstone Amethyst = new Gemstone(AmethystN, Low, ID++);
    public final static IGemstone RoseQuartz = new Gemstone(RoseQuartzN, Low, ID++)
            .addPassiveSkillsToAll(MagicAttach);
    public final static IGemstone Turquoise = new Gemstone(TurquoiseN, Low, ID++)
            .addPassiveSkillsToAll(GentlyLand);

    public final static IGemstone Flamegem = new Gemstone(FlamegemN, Middle, ID++)
            .addModifier(FlameGemModifier.Light_Lance)
            .addPassiveSkillsToAll(FireProof)
            .addPassiveSkillToWeaponTypes(ScorchingTouch, Lance, BattleAxe, Sword);

    public final static IGemstone Marinegem = new Gemstone(MarinegemN, Middle, ID++)
            .addPassiveSkillsToAll(UndeterminedSkills.XpMending);

    public final static IGemstone Earthgem = new Gemstone(EarthgemN, Middle, ID++);

    public final static IGemstone Forestgem = new Gemstone(ForestgemN, Middle, ID++)
            .addPassiveSkillsToAll(NutrientAbsorption);

    public final static IGemstone Endergem = new Gemstone(EndergemN, Middle, ID++)
            .addModifier(EnderGemModifier.Light_Lance);

    public final static IGemstone MagicPearl = new Gemstone(MagicPearlN, Middle, ID++)
            .addPassiveSkillsToAll(Magicize);
    public final static IGemstone Windy = new Gemstone(WindyGemstoneN, Middle, ID++)
            .addPassiveSkillsToAll(Levitation)
            .addPassiveSkillsToAll(Feather);

    //You must call it to load this class and all the static fields.
    @OnlyWhenInitialization
    public static void load() {

    }
}
