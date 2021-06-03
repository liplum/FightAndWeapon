package net.liplum.lib;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nullable;

public class CapabilityStorage<CapabilityType extends INBTSerializable<NBTTagCompound>> implements Capability.IStorage<CapabilityType> {

    @Nullable
    @Override
    public NBTBase writeNBT(Capability<CapabilityType> capability, CapabilityType instance, EnumFacing side) {
        return instance.serializeNBT();
    }

    @Override
    public void readNBT(Capability<CapabilityType> capability, CapabilityType instance, EnumFacing side, NBTBase nbt) {
        if (nbt instanceof NBTTagCompound) {
            instance.deserializeNBT((NBTTagCompound) nbt);
        }
    }
}