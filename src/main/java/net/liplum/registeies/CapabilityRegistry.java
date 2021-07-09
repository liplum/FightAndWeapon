package net.liplum.registeies;

import net.liplum.Names;
import net.liplum.capabilities.CastStudyCapability;
import net.liplum.capabilities.CastStudyProvider;
import net.liplum.capabilities.MasteryCapability;
import net.liplum.capabilities.MasterProvider;
import net.liplum.lib.CapabilityStorage;
import net.liplum.lib.FawLocation;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.LinkedList;
import java.util.List;

public final class CapabilityRegistry {

    private static final LinkedList<CapabilityEntry> PlayerCapabilities = new LinkedList<>();
    private static final LinkedList<CapabilityEntry> EntityCapabilities = new LinkedList<>();
    @CapabilityInject(MasteryCapability.class)
    public static Capability<MasteryCapability> Mastery_Capability;
    @CapabilityInject(CastStudyCapability.class)
    public static Capability<CastStudyCapability> CastStudy_Capability;

    static {
        PlayerCapabilities.add(new CapabilityEntry(new FawLocation(Names.Capability.Mastery), MasterProvider.class));
        PlayerCapabilities.add(new CapabilityEntry(new FawLocation(Names.Capability.CastStudy), CastStudyProvider.class));
    }


    public static void init() {
        register(MasteryCapability.class, new CapabilityStorage<>());
        register(CastStudyCapability.class, new CapabilityStorage<>());
    }

    public static List<CapabilityEntry> getPlayerCapabilities() {
        return PlayerCapabilities;
    }

    public static List<CapabilityEntry> getEntityCapabilities() {
        return EntityCapabilities;
    }

    private static <T extends INBTSerializable<NBTTagCompound>> void register(Class<T> type, CapabilityStorage<T> storage) {
        CapabilityManager.INSTANCE.register(type, storage, type::newInstance);
    }

    public static class CapabilityEntry {
        public final ResourceLocation resourceLocation;
        public final Class<? extends ICapabilityProvider> provider;

        public CapabilityEntry(ResourceLocation resourcePath, Class<? extends ICapabilityProvider> provider) {
            this.resourceLocation = resourcePath;
            this.provider = provider;
        }
    }
}