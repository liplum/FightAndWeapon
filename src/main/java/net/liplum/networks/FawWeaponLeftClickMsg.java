package net.liplum.networks;

import io.netty.buffer.ByteBuf;
import net.liplum.api.weapon.Modifier;
import net.liplum.api.weapon.WeaponBaseItem;
import net.liplum.events.weapon.FawWeaponLeftClickEvent;
import net.liplum.lib.utils.GemUtil;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class FawWeaponLeftClickMsg  implements IMessage {
    @Override
    public void fromBytes(ByteBuf buf) {

    }

    @Override
    public void toBytes(ByteBuf buf) {

    }

    public static class Handler implements IMessageHandler<FawWeaponLeftClickMsg, IMessage> {
        @Override
        public IMessage onMessage(FawWeaponLeftClickMsg message, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().player;
            WorldServer serverWorld = player.getServerWorld();
            ItemStack mainHeld = player.getHeldItemMainhand();
            Item item = mainHeld.getItem();
            if (item instanceof WeaponBaseItem) {
                WeaponBaseItem weapon = (WeaponBaseItem) item;
                Modifier modifier = GemUtil.getModifierFrom(mainHeld);
                serverWorld.addScheduledTask(() -> MinecraftForge.EVENT_BUS.post(
                        new FawWeaponLeftClickEvent(player, weapon, modifier, mainHeld)
                ));
            }
            return null;
        }
    }
}
