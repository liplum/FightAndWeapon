package net.liplum.eventhandlers;

import net.liplum.FawBehaviors;
import net.liplum.MetaData;
import net.liplum.api.fight.IMastery;
import net.liplum.api.weapon.WeaponBaseItem;
import net.liplum.capabilities.MasteryCapability;
import net.liplum.events.mastery.MasteryUpgradedEvent;
import net.liplum.lib.FawDamage;
import net.liplum.registeies.CapabilityRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = MetaData.MOD_ID)
public class MasteryHandler {
    @SubscribeEvent
    public static void onCloneMaster(PlayerEvent.Clone event) {
        MasteryCapability oldOne = event.getOriginal().getCapability(CapabilityRegistry.Mastery_Capability, null);
        MasteryCapability newOne = event.getEntityPlayer().getCapability(CapabilityRegistry.Mastery_Capability, null);
        if (newOne != null && oldOne != null) {
            newOne.setAllMasteries(oldOne.getAllMasteries());
        }
    }

    @SubscribeEvent
    public static void onMasteryUpgraded(MasteryUpgradedEvent event) {
        if (!event.player.world.isRemote) {
            EntityPlayer player = event.player;
            IMastery mastery = event.mastery;
            int newLevel = event.newLevel;
            TextComponentString text = new TextComponentString("Now you are " + newLevel + " in " + mastery.getRegisterName());
            player.sendMessage(text);
        }
    }

    @SubscribeEvent
    public static void onKillEnemy(LivingDeathEvent event) {
        DamageSource damageSource = event.getSource();
        Entity attacker = damageSource.getTrueSource();
        if (attacker instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) attacker;
            if (damageSource instanceof FawDamage) {
                FawDamage fawDamage = (FawDamage) damageSource;
                WeaponBaseItem weapon = fawDamage.weapon();
                ItemStack itemStack = fawDamage.itemStack();
                Entity entity = event.getEntity();
                FawBehaviors.onKillEntityWithWeapon(player, weapon, itemStack, entity);
            }
        }
    }
}
