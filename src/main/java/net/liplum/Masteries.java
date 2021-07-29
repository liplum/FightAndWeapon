package net.liplum;

import net.liplum.masteries.*;

import static net.liplum.Attributes.Generic.AbilityPower;
import static net.liplum.Attributes.Generic.Strength;
import static net.liplum.Names.PassiveSkill.ManaBarrier;

public final class Masteries {
    public static final Mastery Lance = new Mastery(WeaponTypes.Lance)
            .setRoutine(new RoutineBuilder()
                    .node(new Node().addAttrAmps(AttrAmp.create(Strength,1))).nextLv()
                    .repeat(2)
                    .node(new Node().lockedPSkill(1)).nextLv()
                    .toRoutine());

    public static final Mastery Harp = new Mastery(WeaponTypes.Harp);

    public static final Mastery BattleAxe = new Mastery(WeaponTypes.BattleAxe)
            .setRoutine(new RoutineBuilder()
                    .node(new Node().addAttrAmps(AttrAmp.create(Strength,1))).nextLv()
                    .repeat(2)
                    .node(new Node().lockedPSkill(1)).nextLv()
                    .toRoutine());

    public static final Mastery MagickWand = new Mastery(WeaponTypes.MagickWand)
            .setRoutine(new RoutineBuilder()
                    .node(Node.Empty).nextLv()
                    .node(new Node().addAttrAmps(AttrAmp.create(AbilityPower,1))).nextLv()
                    .repeat(2)
                    .node(new Node().lockedPSkill(1)).nextLv()
                    .node(new Node().addAttrAmps(AttrAmp.create(AbilityPower,1))).nextLv()
                    .repeat(1)
                    .node(new Node().lockedPSkill(2)).nextLv()
                    .node(new Node().addAttrAmps(AttrAmp.create(AbilityPower,1))).nextLv()
                    .node(new Node().lockedPSkill(3)).nextLv()
                    .node(new Node().addAttrAmps(AttrAmp.create(AbilityPower,2))).nextLv()
                    .repeat(1)
                    .node(new Node().addPassiveSkills(ManaBarrier)).nextLv()
                    .node(new Node().lockedPSkill(4)).nextLv()
                    .node(new Node().addAttrAmps(AttrAmp.create(AbilityPower,4))).nextLv()
                    .toRoutine());


    //You must call it to load this class and all the static fields.
    public static void init() {

    }
}
