package net.liplum.lib.coroutine;

import net.liplum.coroutine.Coroutine;
import net.liplum.coroutine.CoroutineManager;
import net.liplum.enumerator.IEnumerable;
import net.minecraft.entity.EntityLivingBase;

import javax.annotation.Nonnull;
import java.util.HashMap;

public final class CoroutineSystem {
    @Nonnull
    private static final CoroutineSystem instance = new CoroutineSystem();
    @Nonnull
    private final HashMap<EntityLivingBase, CoroutineManager> playerCoroutines = new HashMap<>();

    private CoroutineSystem() {

    }

    public static CoroutineSystem Instance() {
        return instance;
    }

    public Coroutine attachCoroutine(@Nonnull EntityLivingBase entity, Coroutine coroutine) {
        CoroutineManager cm;
        if (playerCoroutines.containsKey(entity)) {
            cm = playerCoroutines.get(entity);
        } else {
            cm = new CoroutineManager();
            playerCoroutines.put(entity, cm);
        }
        return cm.startCoroutine(coroutine);
    }

    public Coroutine[] attachCoroutines(@Nonnull EntityLivingBase entity, Coroutine[] coroutines) {
        CoroutineManager cm;
        if (playerCoroutines.containsKey(entity)) {
            cm = playerCoroutines.get(entity);
        } else {
            cm = new CoroutineManager();
            playerCoroutines.put(entity, cm);
        }
        return cm.startCoroutines(coroutines);
    }

    /**
     * @param entity
     * @return true if it stops any coroutine of this entity. False if there's no given entity.
     */
    public boolean stopAll(@Nonnull EntityLivingBase entity) {
        if (playerCoroutines.containsKey(entity)) {
            playerCoroutines.get(entity).stopAll();
            return true;
        }
        return false;
    }

    public boolean clear(@Nonnull EntityLivingBase entity) {
        if (playerCoroutines.containsKey(entity)) {
            playerCoroutines.get(entity).stopAll();
            playerCoroutines.remove(entity);
            return true;
        }
        return false;
    }

    @Nonnull
    public Coroutine attachCoroutine(@Nonnull EntityLivingBase entity, @Nonnull IEnumerable<?> task, int lifeSpan) {
        return attachCoroutine(entity, new Coroutine(task, lifeSpan));
    }

    public boolean onServerTick() {
        if (playerCoroutines.isEmpty()) {
            return false;
        }
        for (CoroutineManager cm : playerCoroutines.values()) {
            cm.OnTick();
        }
        return true;
    }
}
