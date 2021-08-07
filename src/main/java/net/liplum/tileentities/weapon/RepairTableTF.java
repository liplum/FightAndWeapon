package net.liplum.tileentities.weapon;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class RepairTableTF extends TileEntity {
    @Nonnull
    private static final String WeaponTagName = "Weapon";
    @Nonnull
    private static final String MaterialTagName = "Material";
    @Nonnull
    private final ItemStackHandler weapon = new RepairTableItemStackHandler(1);
    @Nonnull
    private final ItemStackHandler material = new RepairTableItemStackHandler(1);

    public RepairTableTF() {
    }

    @Override
    public void readFromNBT(@Nonnull NBTTagCompound compound) {
        weapon.deserializeNBT(compound.getCompoundTag(WeaponTagName));
        material.deserializeNBT(compound.getCompoundTag(MaterialTagName));
        super.readFromNBT(compound);
    }

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound compound) {
        compound.setTag(WeaponTagName, weapon.serializeNBT());
        compound.setTag(MaterialTagName, material.serializeNBT());
        return super.writeToNBT(compound);
    }


    private class RepairTableItemStackHandler extends ItemStackHandler {
        public RepairTableItemStackHandler(int size) {
            super(size);
        }

        @Override
        protected void onContentsChanged(int slot) {
            RepairTableTF.this.markDirty();
        }
    }
}
