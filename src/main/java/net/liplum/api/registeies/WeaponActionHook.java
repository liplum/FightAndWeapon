package net.liplum.api.registeies;

import net.liplum.api.weapon.WeaponBaseItem;
import net.liplum.masteries.Behavior;
import net.liplum.masteries.IBehaviorHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class WeaponActionHook {
    private static final Map<Behavior, List<IBehaviorHandler>> BehaviorHandlers = new HashMap<>();

    public static void onBehaved(@Nonnull Behavior behavior, @Nonnull EntityLivingBase player, @Nonnull WeaponBaseItem weapon, @Nonnull ItemStack itemStack, Object... args) {
        if (!(player instanceof EntityPlayer)) {
            return;
        }
        EntityPlayer truePlayer = (EntityPlayer) player;
        List<IBehaviorHandler> handlers = BehaviorHandlers.get(behavior);
        if (handlers != null) {
            for (IBehaviorHandler handler : handlers) {
                handler.handle(truePlayer, weapon, itemStack, args);
            }
        }
    }

    public static IBehaviorHandler register(@Nonnull IBehaviorHandler handler) {
        List<IBehaviorHandler> handlers = BehaviorHandlers.computeIfAbsent(handler.getBehavior(), k -> new LinkedList<>());
        handlers.add(handler);
        return handler;
    }
}
