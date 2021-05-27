package net.liplum.lib.registeies;

import net.liplum.lib.items.gemstone.Gemstone;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public final class GemstoneRegistry {
    private static GemstoneRegistry instance = new GemstoneRegistry();

    public static GemstoneRegistry Instance() {
        return instance;
    }

    private Map<String, Gemstone> gemstoneMap =new HashMap<>();

    public void register(Gemstone gemstone){
        gemstoneMap.put(gemstone.getRegisterName(),gemstone);
    }

    @Nullable
    public Gemstone getGemstone(String registerName){
        if(gemstoneMap.containsKey(registerName)){
            return gemstoneMap.get(registerName);
        }
        return null;
    }

}
