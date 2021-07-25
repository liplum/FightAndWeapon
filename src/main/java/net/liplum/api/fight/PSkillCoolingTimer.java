package net.liplum.api.fight;

import net.liplum.api.annotations.LongSupport;
import net.liplum.capabilities.TimerCapability;
import net.liplum.networks.CoolingMsg;
import net.liplum.networks.MessageManager;
import net.liplum.registeies.CapabilityRegistry;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

public class PSkillCoolingTimer implements IPSkillCoolingTimer {
    @Nonnull
    private final EntityPlayer player;
    @Nonnull
    private final TimerCapability timerDelegate;

    @Nonnull
    public static final IPSkillCoolingTimer Empty = new IPSkillCoolingTimer() {
        @Override
        public void addNewCoolDown(@Nonnull IPassiveSkill<?> passiveSkill, int coolDownTicks) {
        }

        @Override
        public boolean isInCoolingDown(@Nonnull IPassiveSkill<?> passiveSkill) {
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

    @Override
    public void addNewCoolDown(@Nonnull IPassiveSkill<?> passiveSkill, int coolDownTicks) {
        timerDelegate.addNewCoolDown(passiveSkill, coolDownTicks);
    }

    @Override
    public boolean isInCoolingDown(@Nonnull IPassiveSkill<?> passiveSkill) {
        return timerDelegate.isInCoolingDown(passiveSkill);
    }

    @Override
    public void tick() {
        timerDelegate.tick();
        if (timerDelegate.needSync()) {
            this.sync();
        }
    }

    @Nonnull
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

    private PSkillCoolingTimer(@Nonnull EntityPlayer player, @Nonnull TimerCapability timerCapability) {
        this.player = player;
        this.timerDelegate = timerCapability;
    }

    @Nonnull
    @LongSupport
    public static IPSkillCoolingTimer create(@Nonnull EntityPlayer player) {
        TimerCapability timer = player.getCapability(CapabilityRegistry.Timer_Capability, null);
        if (timer == null) {
            return Empty;
        }
        return new PSkillCoolingTimer(player, timer);
    }

    @Nonnull
    @LongSupport
    public static IPSkillCoolingTimer create(@Nonnull EntityLivingBase livingEntity) {
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
}
