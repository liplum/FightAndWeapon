package net.liplum.eventhandlers;

import net.liplum.MetaData;
import net.liplum.api.fight.IPSkillCoolingTimer;
import net.liplum.api.fight.PSkillCoolingTimer;
import net.liplum.events.PlayerCollisionEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.List;

@Mod.EventBusSubscriber(modid = MetaData.MOD_ID)
public class PlayerTickHandler {
    @SubscribeEvent
    public static void postPlayerCollisionEvent(TickEvent.PlayerTickEvent e) {
        if (e.phase == TickEvent.Phase.START) {
            EntityPlayer p = e.player;
            World w = p.world;
            if (!w.isRemote) {
                List<Entity> collided = w
                        .getEntitiesWithinAABB(Entity.class, p.getEntityBoundingBox());
                if (collided.size() > 0 && collided.get(0) != p) {
                    MinecraftForge.EVENT_BUS.post(new PlayerCollisionEvent(p, collided.toArray(new Entity[0])));
                }
            }
        }
    }

    @SubscribeEvent
    public static void onTimerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            IPSkillCoolingTimer timer = PSkillCoolingTimer.create(event.player);
            timer.tick();
        }
    }
}
