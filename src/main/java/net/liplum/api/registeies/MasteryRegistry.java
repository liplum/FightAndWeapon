package net.liplum.api.registeies;

import net.liplum.api.annotations.Developing;
import net.liplum.api.fight.IMastery;
import net.liplum.api.weapon.WeaponType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

@Developing
public final class MasteryRegistry {
    private static final Map<WeaponType, IMastery> MasteryMap = new HashMap<>();

    @Nonnull
    public static IMastery register(@Nonnull IMastery master) {
        MasteryMap.put(master.getWeaponType(), master);
        return master;
    }

    @Nullable
    public static IMastery getMasterOf(@Nonnull WeaponType weaponType) {
        return MasteryMap.get(weaponType);
    }
}
