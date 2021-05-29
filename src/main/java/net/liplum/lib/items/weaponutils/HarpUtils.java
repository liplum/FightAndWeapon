package net.liplum.lib.items.weaponutils;

import net.liplum.Tags;
import net.liplum.lib.FawNbt;
import net.liplum.lib.utils.NbtUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public final class HarpUtils {
    public enum HarpState {
        Normal(0),
        JustReleased(1),
        AfterReleasing(2);

        private final byte state;

        HarpState(int state) {
            this.state = (byte) state;
        }
        public byte getStateValue(){
            return state;
        }
    }

    public static void setState(ItemStack itemStack, HarpState state) {
        NBTTagCompound root = NbtUtil.getOrCreateFrom(itemStack);
        NBTTagCompound fawBase = FawNbt.FawBase.getFawBase(root);
        fawBase.setByte(Tags.Harp.HarpState,state.getStateValue());
    }

    public static HarpState getState(ItemStack itemStack) {
        NBTTagCompound root = NbtUtil.getOrCreateFrom(itemStack);
        NBTTagCompound fawBase = FawNbt.FawBase.getFawBase(root);
        byte state = fawBase.getByte(Tags.Harp.HarpState);
        try {
            return HarpState.values()[state];
        } catch (Exception e) {
            return HarpState.Normal;
        }
    }
}
