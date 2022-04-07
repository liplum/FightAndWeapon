package net.liplum.api.fight;

import net.liplum.api.annotations.LongSupport;
import net.liplum.capabilities.TimerCapability;
import net.liplum.networks.CoolingMsg;
import net.liplum.networks.MessageManager;
import net.liplum.registries.CapabilityRegistry;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Map;

public class PSkillCoolingTimer implements IPSkillCoolingTimer {
    @NotNull
    public static final IPSkillCoolingTimer Empty = new IPSkillCoolingTimer() {
        @Override
        public void addNewCoolDown(@NotNull IPassiveSkill<?> passiveSkill, int coolDownTicks) {
        }

        @Override
        public boolean isInCoolingDown(@NotNull IPassiveSkill<?> passiveSkill) {
            return false;
        }

        @Override
        public void tick() {

        }

        @Nullable
        @Override
        public Map<IPassiveSkill<?>, CoolDown> getCoolingPassiveSkills() {
            return null;
        }
    };
    @NotNull
    private final EntityPlayer player;
    @NotNull
    private final TimerCapability timerDelegate;

    private PSkillCoolingTimer(@NotNull EntityPlayer player, @NotNull TimerCapability timerCapability) {
        this.player = player;
        this.timerDelegate = timerCapability;
    }

    @NotNull
    @LongSupport
    public static IPSkillCoolingTimer create(@NotNull EntityPlayer player) {
        TimerCapability timer = player.getCapability(CapabilityRegistry.Timer_Capability, null);
        if (timer == null) {
            return Empty;
        }
        return new PSkillCoolingTimer(player, timer);
    }

    @NotNull
    @LongSupport
    public static IPSkillCoolingTimer create(@NotNull EntityLivingBase livingEntity) {
        if (livingEntity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) livingEntity;
            TimerCapability timer = player.getCapability(CapabilityRegistry.Timer_Capability, null);
            if (timer == null) {
                return Empty;
            }
            return new PSkillCoolingTimer(player, timer);
        }
        return Empty;
    }

    @Override
    public void addNewCoolDown(@NotNull IPassiveSkill<?> passiveSkill, int coolDownTicks) {
        timerDelegate.addNewCoolDown(passiveSkill, coolDownTicks);
    }

    @Override
    public boolean isInCoolingDown(@NotNull IPassiveSkill<?> passiveSkill) {
        return timerDelegate.isInCoolingDown(passiveSkill);
    }

    @Override
    public void tick() {
        timerDelegate.tick();
        if (timerDelegate.needSync()) {
            this.sync();
        }
    }

    @NotNull
    @Override
    public Map<IPassiveSkill<?>, CoolDown> getCoolingPassiveSkills() {
        return timerDelegate.getCoolingPassiveSkills();
    }

    private void sync() {
        if (player.isServerWorld() && player instanceof EntityPlayerMP) {
            MessageManager.sendMessageToPlayer(
                    new CoolingMsg(timerDelegate.getCoolingPassiveSkills()), (EntityPlayerMP) player
            );
        }
    }
}
