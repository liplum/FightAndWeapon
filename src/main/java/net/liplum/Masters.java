package net.liplum;

import net.liplum.masters.Master;
import net.liplum.masters.Node;
import net.liplum.masters.NodeBuilder;

import static net.liplum.Attributes.Generic.Strength;

public final class Masters {
    public static final Master Lance = new Master(WeaponTypes.Lance)
            .setRoutine(new NodeBuilder()
                    .genNode(() -> new Node().addAttributeAmplifiers(Strength.newAttributeAmplifier(1)))
                    .nextLv()
                    .genNode(() -> new Node().addAttributeAmplifiers())
                    .toRoutine());

    public static final Master Harp = new Master(WeaponTypes.Harp);

    public static final Master BattleAxe = new Master(WeaponTypes.BattleAxe);
}
