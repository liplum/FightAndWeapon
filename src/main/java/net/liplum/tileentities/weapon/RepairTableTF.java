package net.liplum.tileentities.weapon;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

public class RepairTableTF extends TileEntity {
    @NotNull
    private static final String WeaponTagName = "Weapon";
    @NotNull
    private static final String MaterialTagName = "Material";
    @NotNull
    private final ItemStackHandler weapon = new RepairTableItemStackHandler(1);
    @NotNull
    private final ItemStackHandler material = new RepairTableItemStackHandler(1);

    public RepairTableTF() {
    }

    @Override
    public void readFromNBT(@NotNull NBTTagCompound compound) {
        weapon.deserializeNBT(compound.getCompoundTag(WeaponTagName));
        material.deserializeNBT(compound.getCompoundTag(MaterialTagName));
        super.readFromNBT(compound);
    }

    @NotNull
    @Override
    public NBTTagCompound writeToNBT(@NotNull NBTTagCompound compound) {
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
