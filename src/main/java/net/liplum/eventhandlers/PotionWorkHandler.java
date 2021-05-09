package net.liplum.eventhandlers;

import net.liplum.MetaData;
import net.liplum.registeies.PotionRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

//@Mod.EventBusSubscriber(modid = MetaData.MOD_ID)
public class PotionWorkHandler {

    /*@SubscribeEvent
    public static void onUnstoppablePotionWork(LivingHurtEvent e) {
        EntityLivingBase entity = e.getEntityLiving();
        if (entity.isPotionActive(PotionRegistry.Unstoppable_Potion)) {
            DamageSource d = e.getSource();
            String t = d.getDamageType();
            if (!(t.equals(DamageSource.OUT_OF_WORLD.getDamageType()) ||
                    t.equals(DamageSource.CACTUS.getDamageType()) ||
                    t.equals(DamageSource.LAVA.getDamageType()) ||
                    t.equals(DamageSource.FALL.getDamageType())
            )
            ) {
                //e.setAmount(0);
                e.setCanceled(true);
            }
        }
    }*/
/*    @SubscribeEvent
    public static void onUnstoppablePotionWork(LivingSetAttackTargetEvent e){
        EntityLivingBase target = e.getTarget();
        if (target.isPotionActive(PotionRegistry.Unstoppable_Potion)) {
            EntityLivingBase attacker = e.getEntityLiving();
            attacker.setRevengeTarget(EntityLiving);
        }
    }*/
}
