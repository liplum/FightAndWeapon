package net.liplum.tileentities.forge;

import net.liplum.api.annotations.Developing;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

@Developing
public class ForgeTE extends TileEntity {
    @NotNull
    private static final String AdditionTagName = "Addition";
    @NotNull
    private static final String MaterialTagName = "Material";
    @NotNull
    private final ItemStackHandler addition = new ForgeItemStackHandler(1);
    @NotNull
    private final ItemStackHandler material = new ForgeItemStackHandler(1);

    public ForgeTE() {
    }

    @NotNull
    public ItemStackHandler getAdditionHandler() {
        return addition;
    }

    @NotNull
    public ItemStackHandler getMaterialHandler() {
        return material;
    }

    @Override
    public void readFromNBT(@NotNull NBTTagCompound compound) {
        addition.deserializeNBT(compound.getCompoundTag(AdditionTagName));
        material.deserializeNBT(compound.getCompoundTag(MaterialTagName));
        super.readFromNBT(compound);
    }

    @NotNull
    @Override
    public NBTTagCompound writeToNBT(@NotNull NBTTagCompound compound) {
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
