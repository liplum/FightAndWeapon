package net.liplum.networks;

import io.netty.buffer.ByteBuf;
import net.liplum.api.fight.CoolDown;
import net.liplum.api.fight.IPassiveSkill;
import net.liplum.api.registeies.SkillRegistry;
import net.liplum.capabilities.TimerCapability;
import net.liplum.registries.CapabilityRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.Tuple;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

public class CoolingMsg implements IMessage {

    private Collection<Tuple<String, Integer>> data;

    public CoolingMsg() {
    }

    public CoolingMsg(Map<IPassiveSkill<?>, CoolDown> data) {
        this.data = data.entrySet().stream()
                .map(entry -> new Tuple<>(
                        entry.getKey().getRegisterName(), entry.getValue().getRestTicks()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        data = new LinkedList<>();
        while (buf.isReadable()) {
            int strLength = buf.readInt();
            data.add(new Tuple<>(buf.readCharSequence(strLength, StandardCharsets.UTF_8).toString(), buf.readInt()));
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        for (Tuple<String, Integer> entry : data) {
            String registerName = entry.getFirst();
            buf.writeInt(registerName.length());
            buf.writeCharSequence(registerName, StandardCharsets.UTF_8);
            buf.writeInt(entry.getSecond());
        }
    }

    public static class Handler implements IMessageHandler<CoolingMsg, IMessage> {

        @Override
        public IMessage onMessage(CoolingMsg message, MessageContext ctx) {
            Minecraft mc = Minecraft.getMinecraft();
            mc.addScheduledTask(() -> {
                EntityPlayerSP player = mc.player;
                TimerCapability timer = player.getCapability(CapabilityRegistry.Timer_Capability, null);
                if (timer != null) {
                    Map<IPassiveSkill<?>, CoolDown> map = new HashMap<>();
                    for (Tuple<String, Integer> entry : message.data) {
                        IPassiveSkill<?> skill = SkillRegistry.getPassiveSkillsFromName(entry.getFirst());
                        if (skill != null) {
                            map.put(skill, new CoolDown(entry.getSecond()));
                        }
                    }
                    timer.setCoolingPassiveSkills(map);
                }
            });
            return null;
        }
    }
}