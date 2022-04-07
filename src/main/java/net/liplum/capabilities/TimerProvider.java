package net.liplum.capabilities;

import net.liplum.api.annotations.LongSupport;
import net.liplum.registries.CapabilityRegistry;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

@LongSupport
public class TimerProvider implements ICapabilitySerializable<NBTTagCompound> {
    private final TimerCapability instance = new TimerCapability();
    private final Capability<TimerCapability> capability;

    public TimerProvider() {
        capability = CapabilityRegistry.Timer_Capability;
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
