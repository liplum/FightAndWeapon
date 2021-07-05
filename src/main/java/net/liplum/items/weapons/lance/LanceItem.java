package net.liplum.items.weapons.lance;

import net.liplum.Attributes;
import net.liplum.I18ns;
import net.liplum.api.weapon.IModifier;
import net.liplum.events.skill.WeaponSkillPostReleasedEvent;
import net.liplum.events.skill.WeaponSkillPreReleaseEvent;
import net.liplum.lib.TooltipOption;
import net.liplum.lib.items.WeaponBaseItem;
import net.liplum.lib.utils.FawItemUtil;
import net.liplum.lib.utils.GemUtil;
import net.liplum.lib.utils.ItemTool;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class LanceItem extends WeaponBaseItem<LanceCore> {
    private final LanceCore core;

    public LanceItem(@Nonnull LanceCore core) {
        super();
        this.core = core;
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World worldIn, EntityPlayer playerIn, @Nonnull EnumHand handIn) {
        EnumActionResult result = EnumActionResult.PASS;
        ItemStack held = playerIn.getHeldItem(handIn);
        int coolDown = core.getValue(Attributes.Generic.CoolDown).getInt();
        //Player can't sprint in the sky.
        if (playerIn.onGround && playerIn.isSneaking()) {
            IModifier<?> modifier = GemUtil.getModifierFrom(held);
            boolean cancelRelease = MinecraftForge.EVENT_BUS.post(
                    new WeaponSkillPreReleaseEvent(worldIn, playerIn, core, modifier, held, handIn)
            );
            if (!cancelRelease) {
                float length = core.getValue(Attributes.Lance.SprintStrength).getFloat();
                float dmg = core.getValue(Attributes.Generic.Strength).getFloat();
                boolean releasedSuccessfully;
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
                    releasedSuccessfully = core.releaseSkill(args);
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
    @SideOnly(Side.CLIENT)
    public boolean addAttributesTooltip(@Nonnull ItemStack stack, @Nullable IModifier<?> modifier, @Nonnull List<String> attributesTooltip, TooltipOption option) {
        boolean shown = super.addAttributesTooltip(stack, modifier, attributesTooltip, option);
        float sprintLength = core.getValue(Attributes.Lance.SprintStrength).getFloat();
        if (modifier instanceof LanceModifier) {
            LanceModifier lanceModifier = (LanceModifier) modifier;
            sprintLength = FawItemUtil.calcuAttribute(sprintLength, lanceModifier.getSprintLengthDelta(), lanceModifier.getSprintLengthRate());
        }
        if (sprintLength > 0) {
            FawItemUtil.addAttributeTooltip(attributesTooltip, I18ns.Attribute.Lance.SprintLength, sprintLength,
                    "%.1f");
            shown = true;
        }
        return shown;
    }

    @Nonnull
    @Override
    public EnumAction getItemUseAction(@Nonnull ItemStack stack) {
        return EnumAction.BLOCK;
    }

    @Nonnull
    @Override
    public LanceCore getCore() {
        return core;
    }
}
