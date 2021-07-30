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
import net.liplum.registeies.CapabilityRegistry;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.MinecraftForge;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MasteryDetail implements IMasteryDetail {
    @Nonnull
    private final EntityPlayer player;
    @Nonnull
    private final MasteryCapability masteryCapability;

    private MasteryDetail(@Nonnull EntityPlayer player, @Nonnull MasteryCapability mastery) {
        this.player = player;
        this.masteryCapability = mastery;
    }

    @Nonnull
    public static IMasteryDetail create(@Nullable EntityLivingBase entity) {
        if (entity instanceof EntityPlayer) {
            return create((EntityPlayer) entity);
        }
        return Empty;
    }

    @Nonnull
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

    @Nonnull
    @Override
    public LvExpPair getLvAndExp(@Nonnull IMastery mastery) {
        return masteryCapability.getLevelAndExp(mastery.getRegisterName());
    }

    /**
     * @param amount it must be expanded 10 times
     * @return how many level it upgraded
     */
    @Override
    public int addExp(@Nonnull IMastery mastery, int amount) {
        if (amount <= 0) {
            return 0;
        }
        LvExpPair levelAndExp = masteryCapability.getLevelAndExp(mastery.getRegisterName());
        int formerLevel = levelAndExp.getLevel();
        levelAndExp.addExp(amount);
        sync();
        int upgraded = MasteryUtil.tryUpgrade(levelAndExp);
        if (upgraded > 0) {
            MasteryUpgradedEvent upgradedEvent = new MasteryUpgradedEvent(player, mastery, formerLevel, upgraded);
            MinecraftForge.EVENT_BUS.post(upgradedEvent);
        }
        return upgraded;
    }

    private void sync() {
        if (player.isServerWorld() && player instanceof EntityPlayerMP) {
            MessageManager.sendMessageToPlayer(
                    new MasteryMsg(masteryCapability.getAllMasteries()), (EntityPlayerMP) player
            );
        }
    }

    @Nonnull
    public Map<IAttribute, AttrDelta> getAttrAmp(@Nonnull IMastery mastery) {
        int lv = masteryCapability.getLevelAndExp(mastery.getRegisterName()).getLevel();
        return mastery.getAttributeAmplifier(lv);
    }

    @Nonnull
    public UnlockedPSkillList getUnlockedPSkills(@Nonnull IMastery mastery) {
        int lv = masteryCapability.getLevelAndExp(mastery.getRegisterName()).getLevel();
        return mastery.getUnlockedPassiveSkills(lv);
    }

    @Nonnull
    public List<IPassiveSkill<?>> getPassiveSkills(@Nonnull IMastery mastery) {
        int lv = masteryCapability.getLevelAndExp(mastery.getRegisterName()).getLevel();
        return mastery.getPassiveSkills(lv);
    }

    @Override
    @Nonnull
    public Map<String, LvExpPair> getAllMasteries() {
        return masteryCapability.getAllMasteries();
    }

    @Nonnull
    private static final IMasteryDetail Empty = new IMasteryDetail() {
        @Nonnull
        @Override
        public LvExpPair getLvAndExp(@Nonnull IMastery mastery) {
            return LvExpPair.Empty;
        }

        @Override
        public int addExp(@Nonnull IMastery mastery, int amount) {
            return 0;
        }

        @Nonnull
        @Override
        public Map<IAttribute, AttrDelta> getAttrAmp(@Nonnull IMastery mastery) {
            return Collections.emptyMap();
        }

        @Nonnull
        @Override
        public UnlockedPSkillList getUnlockedPSkills(@Nonnull IMastery mastery) {
            return UnlockedPSkillList.Empty;
        }

        @Nonnull
        @Override
        public List<IPassiveSkill<?>> getPassiveSkills(@Nonnull IMastery mastery) {
            return Collections.emptyList();
        }

        @Override
        @Nullable
        public Map<String, LvExpPair> getAllMasteries() {
            return null;
        }
    };
}
