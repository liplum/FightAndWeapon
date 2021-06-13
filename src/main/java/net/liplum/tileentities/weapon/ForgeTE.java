package net.liplum.tileentities.weapon;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class ForgeTE extends TileEntity {
    private static final String AdditionTagName = "Addition";
    private static final String MaterialTagName = "Material";
    @Nonnull
    private final ItemStackHandler addition = new ForgeItemStackHandler(1);
    @Nonnull
    private final ItemStackHandler material = new ForgeItemStackHandler(1);

    public ForgeTE() {
    }

    @Nonnull
    public ItemStackHandler getAdditionHandler() {
        return addition;
    }

    @Nonnull
    public ItemStackHandler getMaterialHandler() {
        return material;
    }

    @Override
    public void readFromNBT(@Nonnull NBTTagCompound compound) {
        addition.deserializeNBT(compound.getCompoundTag(AdditionTagName));
        material.deserializeNBT(compound.getCompoundTag(MaterialTagName));
        super.readFromNBT(compound);
    }

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound compound) {
        compound.setTag(AdditionTagName, addition.serializeNBT());
        compound.setTag(MaterialTagName, material.serializeNBT());
        return super.writeToNBT(compound);
    }

    private class ForgeItemStackHandler extends ItemStackHandler {
        public ForgeItemStackHandler(int size) {
            super(size);
        }

        @Override
        protected void onContentsChanged(int slot) {
            ForgeTE.this.markDirty();
        }
    }
}
