package net.liplum.events.mastery;

import net.liplum.api.fight.IMastery;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.Event;
import org.jetbrains.annotations.NotNull;

public class MasteryUpgradedEvent extends Event {
    @NotNull
    public final EntityPlayer player;
    @NotNull
    public final IMastery mastery;
    public final int formerLevel;
    public final int newLevel;
    public final int upgraded;

    public MasteryUpgradedEvent(@NotNull EntityPlayer player, @NotNull IMastery mastery, int formerLevel, int upgraded) {
        this.player = player;
        this.mastery = mastery;
        this.formerLevel = formerLevel;
        this.upgraded = upgraded;
        this.newLevel = formerLevel + upgraded;
    }
}
