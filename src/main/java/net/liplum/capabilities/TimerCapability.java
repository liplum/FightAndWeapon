package net.liplum.capabilities;

import net.liplum.api.fight.IPassiveSkill;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class TimerCapability implements INBTSerializable<NBTTagCompound> {
    @Nonnull
    private final Map<IPassiveSkill<?>, CoolDown> coolingPassiveSkills = new HashMap<>();

    public void onTick() {
        coolingPassiveSkills.values().forEach(CoolDown::onTick);
        coolingPassiveSkills.values().removeIf(CoolDown::isFinished);
    }

    public void addNewCoolDown(@Nonnull IPassiveSkill<?> passiveSkill, int coolDownTicks) {
        coolingPassiveSkills.put(passiveSkill, new CoolDown(coolDownTicks));
    }

    public boolean isInCoolingDown(@Nonnull IPassiveSkill<?> passiveSkill) {
        CoolDown coolDown = coolingPassiveSkills.get(passiveSkill);
        if (coolDown != null) {
            return coolDown.isInCoolingDown();
        }
        return false;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return null;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {

    }

    public static class CoolDown {
        private int restTicks;

        public CoolDown(int restTicks) {
            this.restTicks = restTicks;
        }

        public void onTick() {
            restTicks--;
        }

        public boolean isFinished() {
            return restTicks <= 0;
        }

        public boolean isInCoolingDown() {
            return !isFinished();
        }
    }

}
