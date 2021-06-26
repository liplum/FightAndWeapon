package net.liplum.api.registeies;

import net.liplum.api.fight.IMaster;
import net.liplum.lib.items.WeaponBaseItem;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public final class MasterRegistry {
    private static final Map<Class<? extends WeaponBaseItem<?>>, IMaster> MasterMap = new HashMap<>();

    @Nonnull
    public static IMaster register(@Nonnull IMaster master) {
        MasterMap.put(master.getWeaponType(), master);
        return master;
    }

    @Nullable
    public static IMaster getMasterOf(@Nonnull Class<? extends WeaponBaseItem<?>> weaponType) {
        if (MasterMap.containsKey(weaponType)) {
            return MasterMap.get(weaponType);
        }
        return null;
    }
}
