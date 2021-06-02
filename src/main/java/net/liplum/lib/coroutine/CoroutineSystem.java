package net.liplum.lib.coroutine;

import net.liplum.coroutine.Coroutine;
import net.liplum.coroutine.CoroutineManager;
import net.liplum.enumerator.IEnumerable;
import net.minecraft.entity.player.EntityPlayer;

import java.util.HashMap;
import java.util.Map;

public final class CoroutineSystem {
    private static CoroutineSystem instance = new CoroutineSystem();

    private CoroutineSystem() {

    }

    public static CoroutineSystem Instance() {
        return instance;
    }

    private HashMap<EntityPlayer, CoroutineManager> playerCoroutines = new HashMap<>();

    public Coroutine attachCoroutineToPlayer(EntityPlayer player, Coroutine coroutine) {
        CoroutineManager cm;
        if (playerCoroutines.containsKey(player)) {
            cm = playerCoroutines.get(player);
        } else {
            cm = new CoroutineManager();
            playerCoroutines.put(player, cm);
        }
        return cm.startCoroutine(coroutine);
    }

    public Coroutine[] attachCoroutinesToPlayer(EntityPlayer player, Coroutine[] coroutines) {
        CoroutineManager cm;
        if (playerCoroutines.containsKey(player)) {
            cm = playerCoroutines.get(player);
        } else {
            cm = new CoroutineManager();
            playerCoroutines.put(player, cm);
        }
        return cm.startCoroutines(coroutines);
    }

    /**
     * @param player
     * @return true if it stops any coroutine of this player. False if there's no given player.
     */
    public boolean stopAllOfPlayer(EntityPlayer player) {
        if (playerCoroutines.containsKey(player)) {
            playerCoroutines.get(player).stopAll();
            return true;
        }
        return false;
    }

    public boolean clearPlayer(EntityPlayer player) {
        if (playerCoroutines.containsKey(player)) {
            playerCoroutines.get(player).stopAll();
            playerCoroutines.remove(player);
            return true;
        }
        return false;
    }

    public Coroutine attachCoroutineToPlayer(EntityPlayer player, IEnumerable task, int lifeSpan) {
        return attachCoroutineToPlayer(player, new Coroutine(task, lifeSpan));
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
