package net.liplum.items.weapons.lance;

import net.liplum.Names;
import net.liplum.api.fight.IPassiveSkill;
import net.liplum.api.fight.PSkillResult;
import net.liplum.api.fight.PassiveSkill;
import net.liplum.api.weapon.WeaponSkillArgs;
import net.liplum.events.skill.LanceSprintEvent;
import net.liplum.registeies.PotionRegistry;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;

import javax.annotation.Nonnull;

public final class PSkills {
    /**
     * It can make the player unstoppable when sprinting with a lance.
     */
    public final static IPassiveSkill<LanceSprintEvent> Unstoppable =
            new PassiveSkill<LanceSprintEvent>(Names.PassiveSkill.Unstoppable, LanceSprintEvent.class) {
                @Nonnull
                @Override
                public PSkillResult onTrigger(@Nonnull LanceSprintEvent event) {
                    WeaponSkillArgs args = event.getArgs();
                    EntityLivingBase player = args.entity();
                    player.addPotionEffect(new PotionEffect(PotionRegistry.Unstoppable_Potion, 15, 0, false, false));
                    return PSkillResult.Complete;
                }
            }.setShownInTooltip(false);
}
