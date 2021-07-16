package net.liplum;

import net.liplum.tooltips.ITooltipMiddleware;
import net.liplum.tooltips.TooltipPart;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public final class TooltipMiddlewares {
    public static final ITooltipMiddleware ShowWeaponType = context -> {
        return new TooltipPart();
    };
}
