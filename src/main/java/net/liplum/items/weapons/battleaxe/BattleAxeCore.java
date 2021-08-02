package net.liplum.items.weapons.battleaxe;

import net.liplum.Attributes;
import net.liplum.WeaponTypes;
import net.liplum.api.weapon.WeaponCore;
import net.liplum.api.weapon.WeaponType;
import net.liplum.attributes.IAttribute;
import net.minecraft.item.EnumAction;

import javax.annotation.Nonnull;
import java.util.List;

import static net.liplum.Attributes.Generic.AttackSpeed;

public abstract class BattleAxeCore extends WeaponCore {

    public BattleAxeCore(@Nonnull String registerName) {
        super(registerName);
    }

    public BattleAxeCore(@Nonnull String registerName, boolean hasWeaponSkill) {
        super(registerName, hasWeaponSkill);
    }

    @Override
    protected void initAllAttributes(List<IAttribute> attributes) {
        super.initAllAttributes(attributes);
        attributes.add(Attributes.BattleAxe.SweepRange);
    }

    @Override
    protected void build(@Nonnull WeaponCoreBuilder builder) {
        super.build(builder);
        builder.set(
                AttackSpeed, 1.2F
        ).set(
                EnumAction.BLOCK
        );
    }

    @Nonnull
    @Override
    public WeaponType getWeaponType() {
        return WeaponTypes.BattleAxe;
    }
}
