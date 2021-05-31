package net.liplum.lib.modifiers;

import net.liplum.api.weapon.IModifier;
import net.liplum.lib.cores.harp.ContinuousHarpArgs;
import net.liplum.lib.cores.harp.IHarpCore;
import net.liplum.lib.cores.harp.SingleHarpArgs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public abstract class HarpIModifier implements IModifier<IHarpCore> {
    public double getRadiusDelta() {
        return 0;
    }

    public double getRadiusRate() {
        return 0;
    }

    public int getMaxUseDelta(){
        return 0;
    }
    public float getMaxUseRate(){
        return 0;
    }

    public int getFrequencyDelta(){
        return 0;
    }

    public float getFrequencyRate(){
        return 0;
    }

    public boolean continueSkill(IHarpCore core, ContinuousHarpArgs args) {
        return core.continueSkill(args);
    }

    public boolean releaseSkill(IHarpCore core, SingleHarpArgs args) {
        return core.releaseSkill(args);
    }
}