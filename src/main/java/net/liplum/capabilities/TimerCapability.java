package net.liplum.capabilities;

import net.liplum.MetaData;
import net.liplum.api.fight.CoolDown;
import net.liplum.api.fight.IPSkillCoolingTimer;
import net.liplum.api.fight.IPassiveSkill;
import net.liplum.api.fight.PSkillCoolingTimer;
import net.liplum.api.registeies.SkillRegistry;
import net.liplum.networks.CoolingMsg;
import net.liplum.networks.MessageManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = MetaData.MOD_ID)
public class TimerCapability implements INBTSerializable<NBTTagCompound>, IPSkillCoolingTimer {
    @Nonnull
    private Map<IPassiveSkill<?>, CoolDown> coolingPassiveSkills = new HashMap<>();

    private boolean dirty = false;

    public void tick() {
        coolingPassiveSkills.values().forEach(CoolDown::tick);
        boolean removed = coolingPassiveSkills.values().removeIf(CoolDown::isFinished);
        if (removed) {
            markDirty();
        }
    }

    private void markDirty() {
        dirty = true;
    }

    public boolean needSync() {
        boolean needSync = dirty;
        dirty = false;
        return needSync;
    }

    @Override
    public void addNewCoolDown(@Nonnull IPassiveSkill<?> passiveSkill, int coolDownTicks) {
        coolingPassiveSkills.put(passiveSkill, new CoolDown(coolDownTicks));
        markDirty();
    }

    @Nonnull
    public Map<IPassiveSkill<?>, CoolDown> getCoolingPassiveSkills() {
        return coolingPassiveSkills;
    }

    public void setCoolingPassiveSkills(@Nonnull Map<IPassiveSkill<?>, CoolDown> coolingPassiveSkills) {
        this.coolingPassiveSkills = coolingPassiveSkills;
    }

    @Override
    public boolean isInCoolingDown(@Nonnull IPassiveSkill<?> passiveSkill) {
        CoolDown coolDown = coolingPassiveSkills.get(passiveSkill);
        if (coolDown != null) {
            return coolDown.isInCoolingDown();
        }
        return false;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        for (Map.Entry<IPassiveSkill<?>, CoolDown> entry : coolingPassiveSkills.entrySet()) {
            nbt.setInteger(entry.getKey().getRegisterName(), entry.getValue().getRestTicks());
        }
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        for (String key : nbt.getKeySet()) {
            IPassiveSkill<?> name = SkillRegistry.getPassiveSkillsFromName(key);
            coolingPassiveSkills.put(name, new CoolDown(nbt.getInteger(key)));
        }
    }

    @SubscribeEvent
    public static void onPlayerJoin(EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            if (player.isServerWorld() && player instanceof EntityPlayerMP) {
                IPSkillCoolingTimer timer = PSkillCoolingTimer.create(player);
                Map<IPassiveSkill<?>, CoolDown> all = timer.getCoolingPassiveSkills();
                if (all != null) {
                    MessageManager.sendMessageToPlayer(
                            new CoolingMsg(all), (EntityPlayerMP) player
                    );
                }
            }
        }
    }

}
