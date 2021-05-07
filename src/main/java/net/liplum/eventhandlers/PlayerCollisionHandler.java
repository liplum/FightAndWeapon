package net.liplum.eventhandlers;

import net.liplum.events.PlayerCollisionEvent;
import net.liplum.items.weapons.LanceItem;
import net.liplum.lib.tools.ItemTool;
import net.liplum.lib.tools.JavaTool;
import net.liplum.registeies.ItemRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import javax.annotation.Nullable;
import java.util.List;

//@Mod.EventBusSubscriber
public final class PlayerCollisionHandler {
    /*@SubscribeEvent
    public static void onPlayerCollied(PlayerCollisionEvent e){
        EntityPlayer p = e.player;
        World w = p.world;
        for(Entity entity : e.collided){
            if(entity instanceof EntityLiving){
                entity.attackEntityFrom(DamageSource.causePlayerDamage(p),1);
            }
        }
    }*/
/*    @SubscribeEvent
    public static void onLanceSprint(PlayerCollisionEvent e){
        EntityPlayer p = e.player;
        World w = p.world;
        ItemStack mainHeldStack = p.getHeldItemMainhand(),offHeldStack = p.getHeldItemOffhand();
        Item mainHeld = mainHeldStack.getItem(),offHeld = offHeldStack.getItem();
        LanceItem lance = JavaTool.getObjectWhichInstanceOf(LanceItem.class,mainHeld,offHeld);
        if(lance != null){
            ItemStack held = ItemTool.getItemStack(lance,mainHeldStack,offHeldStack);
        }
    }*/
}
