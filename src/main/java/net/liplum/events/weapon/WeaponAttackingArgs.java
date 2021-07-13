package net.liplum.events.weapon;

import net.liplum.api.weapon.DamageArgs;
import net.liplum.api.weapon.WeaponAttackArgs;

import javax.annotation.Nonnull;
import java.util.LinkedList;
import java.util.List;

public class WeaponAttackingArgs extends WeaponAttackArgs<WeaponAttackingArgs> {
    private final List<DamageArgs> allDamages = new LinkedList<>();

    @Nonnull
    public List<DamageArgs> getAllDamages() {
        return allDamages;
    }

    @Nonnull
    @Override
    public WeaponAttackingArgs setInitialDamage(@Nonnull DamageArgs initialDamage) {
        super.setInitialDamage(initialDamage);
        allDamages.clear();
        allDamages.add(initialDamage);
        return this;
    }
}
