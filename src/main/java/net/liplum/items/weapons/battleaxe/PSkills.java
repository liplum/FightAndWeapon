package net.liplum.items.weapons.battleaxe;

import net.liplum.Names;
import net.liplum.api.fight.IPassiveSkill;
import net.liplum.api.fight.PSkillResult;
import net.liplum.api.fight.PassiveSkill;
import net.liplum.events.weapon.WeaponAttackEvent;
import net.liplum.lib.utils.ItemUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import org.jetbrains.annotations.NotNull;

public class PSkills {
    public static final IPassiveSkill<WeaponAttackEvent.Attacked> Combo =
            new PassiveSkill<WeaponAttackEvent.Attacked>(Names.PassiveSkill.Combo, WeaponAttackEvent.Attacked.class) {
                @NotNull
                @Override
                public PSkillResult onTrigger(@NotNull WeaponAttackEvent.Attacked event) {
                    WeaponAttackEvent.Attacked.Args args = event.getArgs();
                    EntityLivingBase attacker = args.attacker();
                    if (attacker instanceof EntityPlayer) {
                        EntityPlayer player = (EntityPlayer) attacker;
                        if (args.isHitSuccessfully() && args.isFullAttack()) {
                            ItemUtil.reduceItemCoolDown(player, args.weapon(), 20);
                            return PSkillResult.Complete;
                        }
                    }
                    return PSkillResult.Fail;
                }
            };
}
