package net.liplum.registeies;

import net.liplum.capabilities.MasterCapability;
import net.liplum.lib.CapabilityStorage;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public final class CapabilityRegistry {

    @CapabilityInject(MasterCapability.class)
    public static Capability<MasterCapability> Master_Capability;

    public static void load() {
        register(MasterCapability.class, new CapabilityStorage<>());
    }

    private static <T> void register(Class<T> type, CapabilityStorage storage) {
        CapabilityManager.INSTANCE.register(type, storage, () -> type.newInstance());
    }
}