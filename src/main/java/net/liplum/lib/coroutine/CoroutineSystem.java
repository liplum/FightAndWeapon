package net.liplum.lib.coroutine;

import net.liplum.coroutine.Coroutine;
import net.liplum.coroutine.CoroutineManager;
import net.liplum.enumerator.IEnumerable;
import net.minecraft.entity.EntityLivingBase;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public final class CoroutineSystem {
    @NotNull
    private static final CoroutineSystem instance = new CoroutineSystem();
    @NotNull
    private final HashMap<EntityLivingBase, CoroutineManager> playerCoroutines = new HashMap<>();

    private CoroutineSystem() {

    }

    public static CoroutineSystem Instance() {
        return instance;
    }

    public Coroutine attachCoroutine(@NotNull EntityLivingBase entity, Coroutine coroutine) {
        CoroutineManager cm;
        if (playerCoroutines.containsKey(entity)) {
            cm = playerCoroutines.get(entity);
        } else {
            cm = new CoroutineManager();
            playerCoroutines.put(entity, cm);
        }
        return cm.startCoroutine(coroutine);
    }

    public Coroutine[] attachCoroutines(@NotNull EntityLivingBase entity, Coroutine[] coroutines) {
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
    public boolean stopAll(@NotNull EntityLivingBase entity) {
        if (playerCoroutines.containsKey(entity)) {
            playerCoroutines.get(entity).stopAll();
            return true;
        }
        return false;
    }

    public boolean clear(@NotNull EntityLivingBase entity) {
        if (playerCoroutines.containsKey(entity)) {
            playerCoroutines.get(entity).stopAll();
            playerCoroutines.remove(entity);
            return true;
        }
        return false;
    }

    @NotNull
    public Coroutine attachCoroutine(@NotNull EntityLivingBase entity, @NotNull IEnumerable<?> task, int lifeSpan) {
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
