package net.liplum.items.weapons.battleaxe;

import net.liplum.api.weapon.IModifier;
import net.liplum.events.skill.WeaponSkillPostReleasedEvent;
import net.liplum.events.skill.WeaponSkillPreReleaseEvent;
import net.liplum.lib.cores.battleaxe.BattleAxeArgs;
import net.liplum.lib.cores.battleaxe.IBattleAxeCore;
import net.liplum.lib.items.WeaponBaseItem;
import net.liplum.lib.modifiers.BattleAxeIModifier;
import net.liplum.lib.utils.FawItemUtil;
import net.liplum.lib.utils.GemUtil;
import net.liplum.lib.utils.ItemTool;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

import javax.annotation.Nonnull;

public class BattleAxeItem extends WeaponBaseItem<IBattleAxeCore> {
    private final IBattleAxeCore core;

    public BattleAxeItem(@Nonnull IBattleAxeCore core) {
        super();
        this.core = core;
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World worldIn, EntityPlayer playerIn, @Nonnull EnumHand handIn) {
        EnumActionResult result = EnumActionResult.PASS;
        ItemStack held = playerIn.getHeldItem(handIn);
        ItemStack offHeld = playerIn.getHeldItemOffhand();
        int coolDown = core.getCoolDown();
        //Default is PASS
        //If play were not sneaking, didn't detect.
        if (!playerIn.isSneaking()) {
            IModifier<?> modifier = GemUtil.getModifierFrom(held);
            boolean cancelRelease = MinecraftForge.EVENT_BUS.post(
                    new WeaponSkillPreReleaseEvent(worldIn, playerIn, core, modifier, held, handIn)
            );
            if (!cancelRelease) {
                float sweepRange = core.getSweepRange();
                float dmg = core.getStrength();
                boolean releaseSkilled = false;
                BattleAxeArgs args = new BattleAxeArgs()
                        .setWorld(worldIn)
                        .setPlayer(playerIn)
                        .setItemStack(held)
                        .setHand(handIn);
                if (modifier != null) {
                    BattleAxeIModifier mod = (BattleAxeIModifier) modifier;
                    sweepRange = FawItemUtil.calcuAttribute(sweepRange, mod.getSweepRangeDelta(), mod.getSweepRate());
                    dmg = FawItemUtil.calcuAttribute(dmg, mod.getStrengthDelta(), mod.getStrengthRate());
                    coolDown = FawItemUtil.calcuAttributeInRate(coolDown, mod.getCoolDownRate());
                    args.setStrength(dmg)
                            .setSweepRange(sweepRange)
                            .setModifier(mod);

                    releaseSkilled |= mod.releaseSkill(core, args);
                } else {
                    args.setStrength(dmg)
                            .setSweepRange(sweepRange);
                    releaseSkilled |= core.releaseSkill(args);
                }

                if (releaseSkilled) {
                    if (ItemTool.heatWeaponIfSurvival(playerIn, held.getItem(), coolDown)) {
                        //When you release the skill, it will make your shield hot.
                        //Don't worry about the EMPTY, if that it'll return Items.AIR (no exception).
                        if (offHeld.getItem() == Items.SHIELD) {
                            playerIn.getCooldownTracker().setCooldown(offHeld.getItem(), coolDown / 5 * 2);
                        }
                    }
                    result = EnumActionResult.SUCCESS;
                    MinecraftForge.EVENT_BUS.post(
                            new WeaponSkillPostReleasedEvent(worldIn, playerIn, core, modifier, held, handIn)
                    );
                }
            }
        }
        return ActionResult.newResult(result, held);
    }

    @Nonnull
    @Override
    public EnumAction getItemUseAction(@Nonnull ItemStack stack) {
        return EnumAction.BLOCK;
    }

    @Nonnull
    @Override
    public IBattleAxeCore getCore() {
        return core;
    }
}
