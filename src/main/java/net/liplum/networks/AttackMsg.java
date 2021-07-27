package net.liplum.networks;

import io.netty.buffer.ByteBuf;
import net.liplum.api.weapon.WeaponBaseItem;
import net.liplum.attributes.AttrCalculator;
import net.liplum.attributes.FinalAttrValue;
import net.liplum.lib.utils.FawItemUtil;
import net.liplum.lib.utils.GemUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import static net.liplum.Attributes.Generic.AttackReach;

public class AttackMsg implements IMessage {

    private int targetEntityID;

    public AttackMsg() {
    }

    public AttackMsg(int targetEntityID) {
        this.targetEntityID = targetEntityID;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        targetEntityID = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(targetEntityID);
    }

    public static class Handler implements IMessageHandler<AttackMsg, IMessage> {

        @Override
        public IMessage onMessage(AttackMsg message, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().player;
            WorldServer serverWorld = player.getServerWorld();
            serverWorld.addScheduledTask(() -> {
                Entity target = serverWorld.getEntityByID(message.targetEntityID);
                if (target != null) {
                    ItemStack itemStack = player.getHeldItem(EnumHand.MAIN_HAND);
                    Item item = itemStack.getItem();
                    if (FawItemUtil.isFawWeapon(itemStack) && !player.isHandActive()) {
                        WeaponBaseItem weapon = (WeaponBaseItem) item;
                        AttrCalculator calculator = new AttrCalculator()
                                .weaponCore(weapon.getCore())
                                .modifier(GemUtil.getModifierFrom(itemStack))
                                .player(player);
                        FinalAttrValue finalAttackReach = calculator.calcu(AttackReach);
                        float reach = finalAttackReach.getFloat();
                        if (player.getDistanceSq(target) < reach * reach) {
                            RayTraceResult rayTrace = serverWorld.rayTraceBlocks(
                                    new Vec3d(player.posX, player.posY + player.getEyeHeight(), player.posZ),
                                    new Vec3d(target.posX, target.posY + target.getEyeHeight(), target.posZ),
                                    false, true, false);

                            if (rayTrace == null || !serverWorld.getBlockState(rayTrace.getBlockPos()).isFullCube())
                                weapon.attackEntity(itemStack, player, target);
                        }
                    }
                }
            });
            return null;
        }
    }
}
