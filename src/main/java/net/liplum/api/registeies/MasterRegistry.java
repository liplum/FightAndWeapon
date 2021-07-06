package net.liplum.api.registeies;

import net.liplum.api.fight.IMaster;
import net.liplum.api.weapon.WeaponBaseItem;
import net.liplum.api.weapon.WeaponType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public final class MasterRegistry {
    private static final Map<WeaponType, IMaster> MasterMap = new HashMap<>();

    @Nonnull
    public static IMaster register(@Nonnull IMaster master) {
        MasterMap.put(master.getWeaponType(), master);
        return master;
    }

    @Nullable
    public static IMaster getMasterOf(@Nonnull WeaponType weaponType) {
        return MasterMap.get(weaponType);
    }
}
