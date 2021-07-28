package net.liplum.capabilities;

import net.liplum.Tags;
import net.liplum.lib.nbt.NbtUtil;
import net.liplum.masteries.LvExpPair;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class MasteryCapability implements INBTSerializable<NBTTagCompound> {

    private HashMap<String, LvExpPair> allMasteries = new HashMap<>();

    public HashMap<String, LvExpPair> shallowCloneAllMasters() {
        return (HashMap<String, LvExpPair>) allMasteries.clone();
    }

    public void setAllMasteries(HashMap<String, LvExpPair> newMasters) {
        allMasteries = newMasters;
    }

    public boolean hasMasterType(String masterName) {
        return allMasteries.containsKey(masterName);
    }

    /**
     * @param masterName
     * @return the level and exp. If this didn't have the master, it would return a new {@link LvExpPair} object and put it into this.
     */
    @Nonnull
    public LvExpPair getLevelAndExp(String masterName) {
        if (allMasteries.containsKey(masterName)) {
            return allMasteries.get(masterName);
        } else {
            LvExpPair lvExpPair = new LvExpPair();
            allMasteries.put(masterName, lvExpPair);
            return lvExpPair;
        }
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        NBTTagList allMasters = new NBTTagList();
        nbt.setTag(Tags.Mastery.MasteryList, allMasters);
        for (Map.Entry<String, LvExpPair> entry : this.allMasteries.entrySet()) {
            String masterType = entry.getKey();
            LvExpPair lvExpPair = entry.getValue();
            int lv = lvExpPair.getLevel();
            int exp = lvExpPair.getExp();
            NBTTagCompound master = new NBTTagCompound();
            master.setString(Tags.Mastery.MasteryObject.Type, masterType);
            master.setInteger(Tags.Mastery.MasteryObject.Level, lv);
            master.setInteger(Tags.Mastery.MasteryObject.Exp, exp);
            allMasters.appendTag(master);
        }
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        NBTTagList masterList = NbtUtil.getSubListOrCreate(nbt, Tags.Mastery.MasteryList, Tags.Type_NbtCompound);
        for (NBTBase masterObject : masterList) {
            NBTTagCompound master = (NBTTagCompound) masterObject;
            String type = master.getString(Tags.Mastery.MasteryObject.Type);
            int lv = master.getInteger(Tags.Mastery.MasteryObject.Level);
            int exp = master.getInteger(Tags.Mastery.MasteryObject.Exp);
            allMasteries.put(type, new LvExpPair(lv, exp));
        }
    }
}
