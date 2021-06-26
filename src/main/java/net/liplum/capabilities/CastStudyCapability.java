package net.liplum.capabilities;

import net.liplum.FawMod;
import net.liplum.api.registeies.CastRegistry;
import net.liplum.api.weapon.Cast;
import net.liplum.lib.utils.Utils;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.HashSet;
import java.util.Set;

public class CastStudyCapability implements INBTSerializable<NBTTagCompound> {

    private static final String CastStudyTagName = "StudiedCasts";

    private final Set<Cast> studied = new HashSet<>();

    public boolean studyNew(String castName) {
        Cast cast = CastRegistry.getCastOf(castName);
        if (cast != null) {
            return studied.add(cast);
        }
        return false;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound root = new NBTTagCompound();
        int[] allStudiedIDs = Utils.toIntArray(studied, Cast::getID);
        root.setIntArray(CastStudyTagName, allStudiedIDs);
        return root;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        int[] allStudiedIDs = nbt.getIntArray(CastStudyTagName);
        for (int id : allStudiedIDs) {
            Cast cast = CastRegistry.getCastOf(id);
            if (cast != null) {
                studied.add(cast);
            } else {
                FawMod.Logger.error("Cast [ID:" + id + "] Not Found!");
            }
        }
    }
}
