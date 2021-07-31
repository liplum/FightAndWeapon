package net.liplum;

import net.liplum.masteries.AttrAmp;
import net.liplum.masteries.Mastery;
import net.liplum.masteries.RoutineBuilder;

import static net.liplum.Attributes.Generic.AbilityPower;
import static net.liplum.Attributes.Generic.Strength;
import static net.liplum.Names.PassiveSkill.ManaBarrier;

public final class Masteries {
    public static final Mastery Lance = new Mastery(WeaponTypes.Lance)
            .setRoutine(new RoutineBuilder()
                    .node(RoutineBuilder.Empty)//1
                    .node(n -> n.addAttrAmps(AttrAmp.create(Strength, 1)))//2
                    .node(n -> n.addAttrAmps(AttrAmp.create(Strength, 1)))//3
                    .node(n -> n.addAttrAmps(AttrAmp.create(Strength, 1)))//4
                    .node(n -> n.lockedPSkill(1))//5
                    .toRoutine());

    public static final Mastery Harp = new Mastery(WeaponTypes.Harp);

    public static final Mastery BattleAxe = new Mastery(WeaponTypes.BattleAxe)
            .setRoutine(new RoutineBuilder()
                    .node(RoutineBuilder.Empty)//1
                    .node(n -> n.addAttrAmps(AttrAmp.create(Strength, 1)))//2
                    .node(n -> n.addAttrAmps(AttrAmp.create(Strength, 1)))//3
                    .node(n -> n.lockedPSkill(1))//4
                    .toRoutine());

    public static final Mastery MagickWand = new Mastery(WeaponTypes.MagickWand)
            .setRoutine(new RoutineBuilder()
                    .node(RoutineBuilder.Empty)//1
                    .node(n -> n.addAttrAmps(AttrAmp.create(AbilityPower, 1)))//2
                    .node(n -> n.addAttrAmps(AttrAmp.create(AbilityPower, 1)))//3
                    .node(n -> n.addAttrAmps(AttrAmp.create(AbilityPower, 1)))//4
                    .node(n -> n.lockedPSkill(1))//5
                    .node(n -> n.addAttrAmps(AttrAmp.create(AbilityPower, 1)))//6
                    .node(n -> n.addAttrAmps(AttrAmp.create(AbilityPower, 1)))//7
                    .node(n -> n.lockedPSkill(2))//8
                    .node(n -> n.addAttrAmps(AttrAmp.create(AbilityPower, 1)))//9
                    .node(n -> n.lockedPSkill(3))//10
                    .node(n -> n.addAttrAmps(AttrAmp.create(AbilityPower, 2)))//11
                    .node(n -> n.addAttrAmps(AttrAmp.create(AbilityPower, 2)))//12
                    .node(n -> n.addPassiveSkills(ManaBarrier))//13
                    .node(n -> n.lockedPSkill(4))//14
                    .node(n -> n.addAttrAmps(AttrAmp.create(AbilityPower, 4)))//15
                    .toRoutine());


    //You must call it to load this class and all the static fields.
    public static void load() {

    }
}
