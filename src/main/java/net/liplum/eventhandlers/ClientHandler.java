package net.liplum.eventhandlers;

import net.liplum.api.weapon.Modifier;
import net.liplum.api.weapon.WeaponCore;
import net.liplum.attributes.FinalAttrValue;
import net.liplum.lib.items.WeaponBaseItem;
import net.liplum.lib.utils.FawItemUtil;
import net.liplum.lib.utils.GemUtil;
import net.liplum.lib.utils.RenderUtil;
import net.liplum.networks.AttackMsg;
import net.liplum.networks.MessageManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static net.liplum.Attributes.Generic.AttackReach;

@SideOnly(Side.CLIENT)
public class ClientHandler {

    @SubscribeEvent(receiveCanceled = true)
    public void onLeftClickToAttack(MouseEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        if (event.isButtonstate() &&
                event.getButton() != -1 &&
                event.getButton() == -100 - mc.gameSettings.keyBindAttack.getKeyCode()) {
            EntityPlayerSP player = mc.player;
            if (player == null || player.isSpectator()) {
                return;
            }
            ItemStack mainHand = player.getHeldItem(EnumHand.MAIN_HAND);
            Item item = mainHand.getItem();
            if (!player.isHandActive()) {
                if (item instanceof WeaponBaseItem<?>) {
                    WeaponBaseItem<?> weapon = (WeaponBaseItem<?>) item;
                    WeaponCore core = weapon.getCore();
                    Modifier<?> modifier = GemUtil.getModifierFrom(mainHand);
                    FinalAttrValue finalAttackReach = FawItemUtil.calcuAttribute(AttackReach, core, modifier, player);
                    if (!AttackReach.isDefaultValue(finalAttackReach)) {
                        RayTraceResult rayTrace = RenderUtil.extendReachRayTrace(finalAttackReach.getFloat());
                        if (rayTrace != null) {
                            MessageManager.sendMessageToServer(
                                    new AttackMsg(rayTrace.entityHit.getEntityId())
                            );
                        }
                    }
                }
            }
        }
    }
}
