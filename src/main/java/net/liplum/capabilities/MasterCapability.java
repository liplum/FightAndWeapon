package net.liplum.capabilities;

import net.liplum.Tags;
import net.liplum.lib.masters.LvExpPair;
import net.liplum.lib.nbt.NbtUtil;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.HashMap;
import java.util.Map;

public class MasterCapability implements INBTSerializable<NBTTagCompound> {

    private HashMap<String, LvExpPair> allMasters = new HashMap<>();

    public HashMap<String, LvExpPair> cloneAllMasters() {
        return (HashMap<String, LvExpPair>) allMasters.clone();
    }

    public void setAllMasters(HashMap<String, LvExpPair> newMasters) {
        allMasters = newMasters;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        NBTTagList allMasters = new NBTTagList();
        nbt.setTag(Tags.Master.MasterList, allMasters);
        for (Map.Entry<String, LvExpPair> entry : this.allMasters.entrySet()) {
            String masterType = entry.getKey();
            LvExpPair lvExpPair = entry.getValue();
            int lv = lvExpPair.getLevel();
            long exp = lvExpPair.getExp();
            NBTTagCompound master = new NBTTagCompound();
            master.setString(Tags.Master.MasterObject.Type, masterType);
            master.setInteger(Tags.Master.MasterObject.Level, lv);
            master.setLong(Tags.Master.MasterObject.Exp, exp);
            allMasters.appendTag(master);
        }
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        NBTTagList masterList = NbtUtil.getSubListOrCreate(nbt, Tags.Master.MasterList, Tags.Type_NbtCompound);
        for (NBTBase masterObject : masterList) {
            NBTTagCompound master = (NBTTagCompound) masterObject;
            String type = master.getString(Tags.Master.MasterObject.Type);
            int lv = master.getInteger(Tags.Master.MasterObject.Level);
            long exp = master.getLong(Tags.Master.MasterObject.Exp);
            allMasters.put(type, new LvExpPair(lv, exp));
        }
    }
}
