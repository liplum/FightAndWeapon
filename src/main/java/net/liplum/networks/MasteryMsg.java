package net.liplum.networks;

import io.netty.buffer.ByteBuf;
import net.liplum.api.registeies.MasteryRegistry;
import net.liplum.capabilities.MasteryCapability;
import net.liplum.masteries.LvExpPair;
import net.liplum.registries.CapabilityRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.Tuple;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

public class MasteryMsg implements IMessage {

    private Collection<Tuple<String, LvExpPair>> data;

    public MasteryMsg() {
    }

    public MasteryMsg(Map<String, LvExpPair> data) {
        this.data = data.entrySet().stream()
                .map(entry -> new Tuple<>(
                        entry.getKey(), entry.getValue()
                ))
                .collect(Collectors.toList());
    }


    @Override
    public void fromBytes(ByteBuf buf) {
        data = new LinkedList<>();
        while (buf.isReadable()) {
            String name = ByteBufUtils.readUTF8String(buf);

            int lv = buf.readInt();
            int exp = buf.readInt();
            data.add(new Tuple<>(name, new LvExpPair(lv, exp)));
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        for (Tuple<String, LvExpPair> entry : data) {
            String name = entry.getFirst();
            if (MasteryRegistry.getMasteryOf(name) != null) {
                ByteBufUtils.writeUTF8String(buf, name);

                LvExpPair lvAndExp = entry.getSecond();
                buf.writeInt(lvAndExp.getLevel());
                buf.writeInt(lvAndExp.getExp());
            }
        }
    }

    public static class Handler implements IMessageHandler<MasteryMsg, IMessage> {

        @Override
        public IMessage onMessage(MasteryMsg message, MessageContext ctx) {
            Minecraft mc = Minecraft.getMinecraft();
            mc.addScheduledTask(() -> {
                EntityPlayerSP player = mc.player;
                MasteryCapability mastery = player.getCapability(CapabilityRegistry.Mastery_Capability, null);
                if (mastery != null) {
                    Map<String, LvExpPair> map = new HashMap<>();
                    for (Tuple<String, LvExpPair> entry : message.data) {
                        map.put(entry.getFirst(), entry.getSecond());
                    }
                    mastery.setAllMasteries(map);
                }
            });
            return null;
        }
    }

}
