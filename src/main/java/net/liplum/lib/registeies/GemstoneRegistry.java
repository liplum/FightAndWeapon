package net.liplum.lib.registeies;

import net.liplum.api.weapon.IGemstone;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public final class GemstoneRegistry {
    private static GemstoneRegistry instance = new GemstoneRegistry();
    private Map<String, IGemstone> gemstoneMap = new HashMap<>();

    public static GemstoneRegistry Instance() {
        return instance;
    }

    public void register(IGemstone gemstone) {
        gemstoneMap.put(gemstone.getRegisterName(), gemstone);
    }

    @Nullable
    public IGemstone getGemstone(String registerName) {
        if (gemstoneMap.containsKey(registerName)) {
            return gemstoneMap.get(registerName);
        }
        return null;
    }

}
