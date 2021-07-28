package net.liplum.events.mastery;

import net.liplum.api.fight.IMastery;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.Event;

import javax.annotation.Nonnull;

public class MasteryUpgradedEvent extends Event {
    @Nonnull
    public final EntityPlayer player;
    @Nonnull
    public final IMastery mastery;
    public final int formerLevel;
    public final int newLevel;
    public final int upgraded;

    public MasteryUpgradedEvent(@Nonnull EntityPlayer player, @Nonnull IMastery mastery, int formerLevel, int upgraded) {
        this.player = player;
        this.mastery = mastery;
        this.formerLevel = formerLevel;
        this.upgraded = upgraded;
        this.newLevel = formerLevel + upgraded;
    }
}
