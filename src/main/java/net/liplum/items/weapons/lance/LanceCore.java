package net.liplum.items.weapons.lance;

import net.liplum.Attributes;
import net.liplum.WeaponTypes;
import net.liplum.api.weapon.WeaponCore;
import net.liplum.api.weapon.WeaponType;
import net.liplum.attributes.IAttribute;
import net.minecraft.item.EnumAction;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static net.liplum.Attributes.Generic.AttackSpeed;

public abstract class LanceCore extends WeaponCore {

    public LanceCore(@NotNull String registerName) {
        super(registerName);
    }

    public LanceCore(@NotNull String registerName, boolean hasWeaponSkill) {
        super(registerName, hasWeaponSkill);
    }

    @Override
    protected void initAllAttributes(List<IAttribute> attributes) {
        super.initAllAttributes(attributes);
        attributes.add(Attributes.Lance.SprintStrength);
    }

    @Override
    protected void build(@NotNull WeaponCoreBuilder builder) {
        super.build(builder);
        builder.set(
                AttackSpeed, 2F
        ).set(
                EnumAction.BLOCK
        );
    }

    @NotNull
    @Override
    public WeaponType getWeaponType() {
        return WeaponTypes.Lance;
    }
}
