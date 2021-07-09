package net.liplum;

import net.liplum.masteries.Mastery;
import net.liplum.masteries.Node;
import net.liplum.masteries.NodeBuilder;

import static net.liplum.Attributes.Generic.Strength;

public final class Masteries {
    public static final Mastery Lance = new Mastery(WeaponTypes.Lance)
            .setRoutine(new NodeBuilder()
                    .genNode(() -> new Node().addAttributeAmplifiers(Strength.newAttributeAmplifier(1)))
                    .nextLv()
                    .genNode(() -> new Node().addAttributeAmplifiers())
                    .toRoutine());

    public static final Mastery Harp = new Mastery(WeaponTypes.Harp);

    public static final Mastery BattleAxe = new Mastery(WeaponTypes.BattleAxe);
}
