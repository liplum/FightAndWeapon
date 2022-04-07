package net.liplum.lib.utils;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;

public class PotionUtil {
    public static void addFireResistance(@NotNull EntityLivingBase entity, int ticks, boolean visible) {
        entity.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, ticks, 0, false, visible));
    }
}
