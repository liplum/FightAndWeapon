package net.liplum.masteries;

import net.liplum.api.fight.IMastery;
import net.liplum.api.fight.IPassiveSkill;
import net.liplum.api.fight.UnlockedPSkillList;
import net.liplum.attributes.AttrDelta;
import net.liplum.attributes.IAttribute;
import net.liplum.capabilities.MasteryCapability;
import net.liplum.events.mastery.MasteryUpgradedEvent;
import net.liplum.lib.utils.MasteryUtil;
import net.liplum.networks.MasteryMsg;
import net.liplum.networks.MessageManager;
import net.liplum.registries.CapabilityRegistry;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.MinecraftForge;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class MasteryDetail implements IMasteryDetail {
    @NotNull
    private static final IMasteryDetail Empty = new IMasteryDetail() {
        @NotNull
        @Override
        public LvExpPair getLvAndExp(@NotNull IMastery mastery) {
            return LvExpPair.Empty;
        }

        @Override
        public int addExp(@NotNull IMastery mastery, int amount) {
            return 0;
        }

        @NotNull
        @Override
        public Map<IAttribute, AttrDelta> getAttrAmp(@NotNull IMastery mastery) {
            return Collections.emptyMap();
        }

        @NotNull
        @Override
        public UnlockedPSkillList getUnlockedPSkills(@NotNull IMastery mastery) {
            return UnlockedPSkillList.Empty;
        }

        @NotNull
        @Override
        public Set<IPassiveSkill<?>> getPassiveSkills(@NotNull IMastery mastery) {
            return Collections.emptySet();
        }

        @Override
        @Nullable
        public Map<String, LvExpPair> getAllMasteries() {
            return null;
        }

        @Override
        public void resetMastery(@NotNull IMastery mastery) {

        }

        @Override
        public void sync() {

        }
    };
    @NotNull
    private final EntityPlayer player;
    @NotNull
    private final MasteryCapability masteryCapability;

    private MasteryDetail(@NotNull EntityPlayer player, @NotNull MasteryCapability mastery) {
        this.player = player;
        this.masteryCapability = mastery;
    }

    @NotNull
    public static IMasteryDetail create(@Nullable EntityLivingBase entity) {
        if (entity instanceof EntityPlayer) {
            return create((EntityPlayer) entity);
        }
        return Empty;
    }

    @NotNull
    public static IMasteryDetail create(@Nullable EntityPlayer player) {
        if (player == null) {
            return Empty;
        }
        MasteryCapability capability = player.getCapability(CapabilityRegistry.Mastery_Capability, null);
        if (capability != null) {
            return new MasteryDetail(player, capability);
        }
        return Empty;
    }

    @NotNull
    @Override
    public LvExpPair getLvAndExp(@NotNull IMastery mastery) {
        return masteryCapability.getLevelAndExp(mastery.getRegisterName());
    }

    /**
     * @param amount it must be expanded 10 times
     * @return how many level it upgraded
     */
    @Override
    public int addExp(@NotNull IMastery mastery, int amount) {
        if (amount <= 0) {
            return 0;
        }
        LvExpPair levelAndExp = masteryCapability.getLevelAndExp(mastery.getRegisterName());
        int formerLevel = levelAndExp.getLevel();
        levelAndExp.addExp(amount);
        int upgraded = MasteryUtil.tryUpgrade(levelAndExp);
        sync();
        if (upgraded > 0) {
            MasteryUpgradedEvent upgradedEvent = new MasteryUpgradedEvent(player, mastery, formerLevel, upgraded);
            MinecraftForge.EVENT_BUS.post(upgradedEvent);
        }
        return upgraded;
    }

    public void sync() {
        if (player.isServerWorld() && player instanceof EntityPlayerMP) {
            MessageManager.sendMessageToPlayer(
                    new MasteryMsg(masteryCapability.getAllMasteries()), (EntityPlayerMP) player
            );
        }
    }

    @NotNull
    public Map<IAttribute, AttrDelta> getAttrAmp(@NotNull IMastery mastery) {
        int lv = masteryCapability.getLevelAndExp(mastery.getRegisterName()).getLevel();
        return mastery.getAttributeAmplifiers(lv);
    }

    @NotNull
    public UnlockedPSkillList getUnlockedPSkills(@NotNull IMastery mastery) {
        int lv = masteryCapability.getLevelAndExp(mastery.getRegisterName()).getLevel();
        return mastery.getUnlockedPassiveSkills(lv);
    }

    @NotNull
    public Set<IPassiveSkill<?>> getPassiveSkills(@NotNull IMastery mastery) {
        int lv = masteryCapability.getLevelAndExp(mastery.getRegisterName()).getLevel();
        return mastery.getPassiveSkills(lv);
    }

    @Override
    @NotNull
    public Map<String, LvExpPair> getAllMasteries() {
        return masteryCapability.getAllMasteries();
    }

    @Override
    public void resetMastery(@NotNull IMastery mastery) {
        LvExpPair levelAndExp = masteryCapability.getLevelAndExp(mastery.getRegisterName());
        levelAndExp.setLevel(LvExpPair.BaseLevel);
        levelAndExp.setExp(LvExpPair.BaseExp);
    }
}
