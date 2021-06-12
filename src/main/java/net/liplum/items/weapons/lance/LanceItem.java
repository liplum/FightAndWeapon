package net.liplum.items.weapons.lance;

import net.liplum.I18ns;
import net.liplum.api.weapon.IModifier;
import net.liplum.events.skill.WeaponSkillPostReleasedEvent;
import net.liplum.events.skill.WeaponSkillPreReleaseEvent;
import net.liplum.lib.cores.lance.ILanceCore;
import net.liplum.lib.cores.lance.LanceArgs;
import net.liplum.lib.items.ILongReachWeapon;
import net.liplum.lib.items.WeaponBaseItem;
import net.liplum.lib.modifiers.LanceModifier;
import net.liplum.lib.utils.FawItemUtil;
import net.liplum.lib.utils.GemUtil;
import net.liplum.lib.utils.ItemTool;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

import javax.annotation.Nonnull;
import java.util.List;

public class LanceItem extends WeaponBaseItem<ILanceCore> implements ILongReachWeapon {
    private final ILanceCore core;

    public LanceItem(@Nonnull ILanceCore core) {
        super();
        this.core = core;
    }

    @Override
    public double getReach() {
        return 5;
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        EnumActionResult result = EnumActionResult.PASS;
        ItemStack held = playerIn.getHeldItem(handIn);
        int coolDown = core.getCoolDown();
        //Player can't sprint in the sky.
        if (playerIn.onGround && playerIn.isSneaking()) {
            IModifier<?> modifier = GemUtil.getModifierFrom(held);
            boolean cancelRelease = MinecraftForge.EVENT_BUS.post(
                    new WeaponSkillPreReleaseEvent(worldIn, playerIn, core, modifier, held, handIn)
            );
            if (!cancelRelease) {
                float length = core.getSprintLength();
                float dmg = core.getStrength();
                boolean releasedSuccessfully = false;
                LanceArgs args = new LanceArgs()
                        .setWorld(worldIn)
                        .setPlayer(playerIn)
                        .setItemStack(held)
                        .setHand(handIn);
                if (modifier != null) {
                    LanceModifier mod = (LanceModifier) modifier;
                    length = FawItemUtil.calcuAttribute(length, mod.getSprintLengthDelta(), mod.getSprintLengthRate());
                    dmg = FawItemUtil.calcuAttribute(dmg, mod.getStrengthDelta(), mod.getStrengthRate());
                    coolDown = FawItemUtil.calcuAttributeInRate(coolDown, mod.getCoolDownRate());
                    args.setSprintLength(length)
                            .setStrength(dmg)
                            .setModifier(mod);
                    releasedSuccessfully = mod.releaseSkill(core, args);
                } else {
                    args.setSprintLength(length)
                            .setStrength(dmg);
                    releasedSuccessfully |= core.releaseSkill(args);
                }
                if (releasedSuccessfully) {
                    ItemTool.heatWeaponIfSurvival(playerIn, held.getItem(), coolDown);
                    result = EnumActionResult.SUCCESS;
                    MinecraftForge.EVENT_BUS.post(
                            new WeaponSkillPostReleasedEvent(worldIn, playerIn, core, modifier, held, handIn)
                    );
                }
            }
        }
        return ActionResult.newResult(result, held);
    }

    @Override
    public boolean addAttributesTooltip(@Nonnull ItemStack stack, @Nonnull List<String> tooltip, boolean isAdvanced) {
        boolean shown = super.addAttributesTooltip(stack, tooltip, isAdvanced);
        float sprintLength = core.getSprintLength();
        if (sprintLength > 0) {
            FawItemUtil.addAttributeTooltip(tooltip, I18ns.Attribute.Lance.SprintLength, sprintLength);
            shown = true;
        }
        return shown;
    }

    @Override
    public ILanceCore getCore() {
        return core;
    }
}
