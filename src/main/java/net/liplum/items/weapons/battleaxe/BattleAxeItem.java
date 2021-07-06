package net.liplum.items.weapons.battleaxe;

import net.liplum.WeaponTypes;
import net.liplum.api.weapon.Modifier;
import net.liplum.api.weapon.WeaponType;
import net.liplum.attributes.FinalAttrValue;
import net.liplum.events.skill.WeaponSkillPostReleasedEvent;
import net.liplum.events.skill.WeaponSkillPreReleaseEvent;
import net.liplum.api.weapon.WeaponBaseItem;
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

import static net.liplum.Attributes.BattleAxe.SweepRange;
import static net.liplum.Attributes.Generic.CoolDown;
import static net.liplum.Attributes.Generic.Strength;

public class BattleAxeItem extends WeaponBaseItem<BattleAxeCore> {
    private final BattleAxeCore core;

    public BattleAxeItem(@Nonnull BattleAxeCore core) {
        super();
        this.core = core;
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World worldIn, EntityPlayer playerIn, @Nonnull EnumHand handIn) {
        EnumActionResult result = EnumActionResult.PASS;
        ItemStack held = playerIn.getHeldItem(handIn);
        ItemStack offHeld = playerIn.getHeldItemOffhand();
        //Default is PASS
        //If play were not sneaking, didn't detect.
        if (!playerIn.isSneaking()) {
            Modifier<?> modifier = GemUtil.getModifierFrom(held);
            boolean cancelRelease = MinecraftForge.EVENT_BUS.post(
                    new WeaponSkillPreReleaseEvent(worldIn, playerIn, core, modifier, held, handIn)
            );
            if (!cancelRelease) {
                FinalAttrValue finalCoolDown = FawItemUtil.calcuAttribute(CoolDown, core, modifier);
                FinalAttrValue finalStrength = FawItemUtil.calcuAttribute(Strength, core, modifier, playerIn);
                FinalAttrValue finalSweepRange = FawItemUtil.calcuAttribute(SweepRange, core, modifier, playerIn);
                int coolDown = finalCoolDown.getInt();
                boolean releaseSkilled;
                BattleAxeArgs args = new BattleAxeArgs()
                        .setWorld(worldIn)
                        .setPlayer(playerIn)
                        .setItemStack(held)
                        .setHand(handIn)
                        .setStrength(finalStrength.getFloat())
                        .setSweepRange(finalSweepRange.getFloat())
                        .setModifier(modifier);
                if (modifier != null) {
                    BattleAxeModifier mod = (BattleAxeModifier) modifier;
                    releaseSkilled = mod.releaseSkill(core, args);
                } else {
                    releaseSkilled = core.releaseSkill(args);
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
    public BattleAxeCore getCore() {
        return core;
    }

    @Nonnull
    @Override
    public WeaponType getWeaponType() {
        return WeaponTypes.BattleAxe;
    }
}
