package net.liplum.masteries;

import net.liplum.api.weapon.WeaponBaseItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class MasterySheet {
    private static final Map<Behavior, IBehaviorHandler> BehaviorHandlers = new HashMap<>();

    public static void onBehaved(@Nonnull Behavior behavior, @Nonnull EntityLivingBase player, @Nonnull WeaponBaseItem weapon, @Nonnull ItemStack itemStack, Object... args) {
        if (!(player instanceof EntityPlayer)) {
            return;
        }
        IBehaviorHandler handler = BehaviorHandlers.get(behavior);
        if (handler != null) {
            handler.handle((EntityPlayer) player, weapon, itemStack, args);
        }
    }

    public static IBehaviorHandler register(@Nonnull IBehaviorHandler handler) {
        BehaviorHandlers.put(handler.getBehavior(), handler);
        return handler;
    }
}
