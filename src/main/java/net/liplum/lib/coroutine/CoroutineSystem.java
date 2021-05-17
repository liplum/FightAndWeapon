package net.liplum.lib.coroutine;

import net.liplum.coroutine.Coroutine;
import net.liplum.coroutine.CoroutineManager;
import net.liplum.enumerator.IEnumerable;
import net.minecraft.entity.player.EntityPlayer;

import java.util.HashMap;

public final class CoroutineSystem {
    private static CoroutineSystem instance;

    private CoroutineSystem(){

    }

    public static CoroutineSystem Instance() {
        if (instance == null) {
            instance = new CoroutineSystem();
        }
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

    public void StopAllOfPlayer(EntityPlayer player) {
        if (playerCoroutines.containsKey(player)) {
            playerCoroutines.get(player).stopAll();
        }
    }

    public Coroutine attachCoroutineToPlayer(EntityPlayer player, IEnumerable task, int lifeSpan) {
        return attachCoroutineToPlayer(player, new Coroutine(task, lifeSpan));
    }

    public boolean onPlayerTick(EntityPlayer player) {
        if (playerCoroutines.containsKey(player)) {
            CoroutineManager cm = playerCoroutines.get(player);
            cm.OnTick();
            return true;
        }
        return false;
    }
}
