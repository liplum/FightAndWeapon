package net.liplum.renders;

import net.liplum.Resources;
import net.liplum.api.weapon.WeaponBaseItem;
import net.liplum.lib.utils.ResourceUtil;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class WeaponModelLoader implements ICustomModelLoader {

    private static final WeaponModelLoader instance = new WeaponModelLoader();
    private static final Map<ResourceLocation, WeaponBaseItem> weaponModelMap = new HashMap<>();

    protected WeaponModelLoader() {

    }

    public static WeaponModelLoader getInstance() {
        return instance;
    }

    private static void addWeaponMapping(ResourceLocation resourceLocation, WeaponBaseItem weapon) {
        weaponModelMap.put(resourceLocation, weapon);
    }

    @Override
    public void onResourceManagerReload(@NotNull IResourceManager resourceManager) {

    }

    @Override
    public boolean accepts(@NotNull ResourceLocation modelLocation) {
        return modelLocation.getResourcePath().endsWith(Resources.Weapon_Extension);
    }

    @NotNull
    @Override
    public IModel loadModel(@NotNull ResourceLocation modelLocation) throws Exception {
        try {
            Map<String, String> textures = ResourceUtil.loadTexturesFromJson(modelLocation);

        } catch (Exception e) {

        }
        return null;
    }
}
