package net.liplum.eventhandlers;

import net.liplum.MetaData;
import net.liplum.capabilities.MasteryCapability;
import net.liplum.registeies.CapabilityRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = MetaData.MOD_ID)
public final class GenericHandler {
    @SubscribeEvent
    public static void onPlayerJoin(EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();
        World world = event.getWorld();
        if (!world.isRemote && entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            MasteryCapability master = player.getCapability(CapabilityRegistry.Mastery_Capability, null);
            String msg = "You have " + (master != null ? "mastery!" : "nothing...");
            TextComponentString text = new TextComponentString(msg);
            player.sendMessage(text);
        }
    }

    @SubscribeEvent
    public static void onPlayerHurt(LivingHurtEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof EntityPlayer) {
            entity.sendMessage(new TextComponentString("You hurt " + event.getAmount() + "."));
        }
    }

    @SubscribeEvent
    public static void onPlayerHurt(LivingDamageEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof EntityPlayer) {
            entity.sendMessage(new TextComponentString("You damaged " + event.getAmount() + "."));
        }
    }

}
