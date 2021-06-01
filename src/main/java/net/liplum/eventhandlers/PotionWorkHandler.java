package net.liplum.eventhandlers;

import net.liplum.MCNames;
import net.liplum.MetaData;
import net.liplum.registeies.PotionRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = MetaData.MOD_ID)
public class PotionWorkHandler {

    @SubscribeEvent
    public static void onUnstoppablePotionWork(LivingAttackEvent e) {
        EntityLivingBase target = e.getEntityLiving();
        DamageSource source = e.getSource();
        String dmgType = source.getDamageType();
        if (target.isPotionActive(PotionRegistry.Unstoppable_Potion) &&
                (dmgType.equals(MCNames.DamageType.Mob) ||
                        dmgType.equals(MCNames.DamageType.Player)
                )) {
            e.setCanceled(true);
        }
    }
/*    @SubscribeEvent
    public static void onUnstoppablePotionWork(LivingSetAttackTargetEvent e){
        EntityLivingBase target = e.getTarget();
        if (target.isPotionActive(PotionRegistry.Unstoppable_Potion)) {
            EntityLivingBase attacker = e.getEntityLiving();
            attacker.setRevengeTarget(EntityLiving);
        }
    }*/
}
