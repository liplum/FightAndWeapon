package net.liplum.registries;

import net.liplum.I18ns;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.input.Keyboard;

import java.util.LinkedList;

public final class HotkeyRegistry {

    private static final LinkedList<KeyBinding> AllHotkeys = new LinkedList<>();

    public static final KeyBinding Master_Hotkey = with(new KeyBinding(
            I18ns.Hotkey.Mastery.Mastery,
            KeyConflictContext.IN_GAME,
            Keyboard.KEY_K,
            I18ns.Hotkey.Faw_Category));

    private static KeyBinding with(KeyBinding keyBinding) {
        AllHotkeys.add(keyBinding);
        return keyBinding;
    }

    public static LinkedList<KeyBinding> getAllHotkeys() {
        return AllHotkeys;
    }
}
