package net.liplum.skills.passive;

import net.liplum.Names;
import net.liplum.api.fight.IPassiveSkill;
import net.liplum.api.fight.PSkillResult;
import net.liplum.api.registeies.SkillRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public final class FlameGemSkills {
    public final static IPassiveSkill<TickEvent.PlayerTickEvent> FireProof = SkillRegistry.registerPassiveSkill(Names.PassiveSkill.FireProof, new IPassiveSkill<TickEvent.PlayerTickEvent>() {
        @Override
        public Class<TickEvent.PlayerTickEvent> getEventType() {
            return TickEvent.PlayerTickEvent.class;
        }

        @Override
        public PSkillResult onTrigger(TickEvent.PlayerTickEvent event) {
            if (event.phase == TickEvent.Phase.START) {
                EntityPlayer player = event.player;
                player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 5, 0, false, false));
            }
            return PSkillResult.Complete;
        }
    });
}