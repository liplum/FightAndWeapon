package net.liplum.capabilities;

import net.liplum.registries.CapabilityRegistry;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class CastStudyProvider implements ICapabilitySerializable<NBTTagCompound> {
    private final CastStudyCapability instance = new CastStudyCapability();
    private final Capability<CastStudyCapability> capability;

    public CastStudyProvider() {
        capability = CapabilityRegistry.CastStudy_Capability;
    }

    @Override
    public boolean hasCapability(@NotNull Capability<?> capability, @Nullable EnumFacing facing) {
        return this.capability.equals(capability);
    }

    @Nullable
    @Override
    public <T> T getCapability(@NotNull Capability<T> capability, @Nullable EnumFacing facing) {
        return this.capability.equals(capability) ? this.capability.cast(instance) : null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return instance.serializeNBT();
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        instance.deserializeNBT(nbt);
    }
}
