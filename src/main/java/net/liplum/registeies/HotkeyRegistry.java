package net.liplum.registeies;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.input.Keyboard;

import java.util.LinkedList;

public final class HotkeyRegistry {

    private static final LinkedList<KeyBinding> allHotkeys = new LinkedList<>();

    public static final KeyBinding Master_Hotkey = with(new KeyBinding(
            "key.faw.master",
            KeyConflictContext.IN_GAME,
            Keyboard.KEY_K,
            "key.category.faw"));

    private static KeyBinding with(KeyBinding keyBinding) {
        allHotkeys.add(keyBinding);
        return keyBinding;
    }

    public static LinkedList<KeyBinding> getAllHotkeys() {
        return allHotkeys;
    }
}
