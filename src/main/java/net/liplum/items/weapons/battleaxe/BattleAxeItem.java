package net.liplum.items.weapons.battleaxe;

import net.liplum.events.WeaponSkillReleasePostEvent;
import net.liplum.events.WeaponSkillReleasePreEvent;
import net.liplum.lib.items.WeaponBaseItem;
import net.liplum.lib.modifiers.BattleAxeIModifier;
import net.liplum.api.weapon.IModifier;
import net.liplum.lib.utils.FawGemUtil;
import net.liplum.lib.utils.FawItemUtil;
import net.liplum.lib.utils.ItemTool;
import net.liplum.lib.cores.battleaxe.BattleAxeArgs;
import net.liplum.lib.cores.battleaxe.IBattleAxeCore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class BattleAxeItem extends WeaponBaseItem<IBattleAxeCore> {
    private IBattleAxeCore core;

    public BattleAxeItem(IBattleAxeCore core) {
        super();
        this.core = core;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        EnumActionResult result = EnumActionResult.PASS;
        ItemStack held = playerIn.getHeldItem(handIn);
        ItemStack offHeld = playerIn.getHeldItemOffhand();
        int coolDown = core.getCoolDown();
        //Default is PASS
        //If play were not sneaking, didn't detect.
        if (!playerIn.isSneaking()) {
            IModifier modifier = FawGemUtil.getModifierFrom(held);
            boolean cancelRelease = MinecraftForge.EVENT_BUS.post(
                    new WeaponSkillReleasePreEvent(worldIn, playerIn, core, modifier, held, handIn)
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
                    coolDown = FawItemUtil.calcuAttributeInRate(coolDown,mod.getCoolDownRate());
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
                            new WeaponSkillReleasePostEvent(worldIn, playerIn, core, modifier, held, handIn)
                    );
                }
            }
        }
        return ActionResult.newResult(result, held);
    }

    @Override
    public IBattleAxeCore getCore() {
        return core;
    }
}
