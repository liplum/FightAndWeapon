package net.liplum.items.weapons.battleaxe;

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

public class BattleAxeItem extends WeaponBaseItem<IBattleAxeCore> {
    private IBattleAxeCore core;

    public BattleAxeItem(IBattleAxeCore core) {
        super();
        this.core = core;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        //TODO:Transfer them into the battle axe core
        ItemStack held = playerIn.getHeldItem(handIn);
        ItemStack offHeld = playerIn.getHeldItemOffhand();
        //Default is PASS
        EnumActionResult result = EnumActionResult.PASS;
        //If play were not sneaking, didn't detect.
        if (!playerIn.isSneaking()) {
            IModifier modifier = FawGemUtil.getModifierFrom(held);
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
                args.setStrength(dmg)
                        .setSweepRange(sweepRange);
                releaseSkilled |= mod.releaseSkill(core, args);
            } else {
                args.setStrength(dmg)
                        .setSweepRange(sweepRange);
                releaseSkilled |= core.releaseSkill(args);
            }

            if(releaseSkilled){
                int coolDown =  getCoolDown();
                if (ItemTool.heatWeaponIfSurvival(playerIn, held.getItem(),coolDown)) {
                    //When you release the skill, it will make your shield hot.
                    //Don't worry about the EMPTY, if that it'll return Items.AIR (no exception).
                    if (offHeld.getItem() == Items.SHIELD) {
                        playerIn.getCooldownTracker().setCooldown(offHeld.getItem(), coolDown / 5 * 2);
                    }
                }
                result = EnumActionResult.SUCCESS;
            }
        }
        return ActionResult.newResult(result, held);
    }

    @Override
    public IBattleAxeCore getCore() {
        return core;
    }
}
