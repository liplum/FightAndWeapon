package net.liplum.renders;

import net.liplum.MetaData;
import net.liplum.api.registeies.CastRegistry;
import net.liplum.lib.FawLocation;
import net.liplum.lib.utils.ResourceUtil;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IResourceManager;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.resource.IResourceType;
import net.minecraftforge.client.resource.ISelectiveResourceReloadListener;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Predicate;

@Mod.EventBusSubscriber(modid = MetaData.MOD_ID)
public class CastTextureCreator implements ISelectiveResourceReloadListener {
    public static final CastTextureCreator instance = new CastTextureCreator();

    public static CastTextureCreator getInstance() {
        return instance;
    }

    @SubscribeEvent
    public static void onTextureStitching(TextureStitchEvent.Pre event) {
        System.out.println("!!!!!!!!!createCastTextures!!!!!!!!!");
        TextureMap map = event.getMap();
        createAllCastsTexture(map);
    }

    private static void createAllCastsTexture(TextureMap map) {
        for (String path : CastRegistry.getAllCastNames()) {
            readCastTexture(map, path);
        }
    }

    @Nullable
    private static TextureAtlasSprite readCastTexture(TextureMap map, String castPath) {
        FawLocation location = new FawLocation("casts/" + castPath);
        TextureAtlasSprite sprite;
        if (ResourceUtil.isExisted(location)) {
            sprite = map.registerSprite(location);
            return sprite;
        }
        return null;
    }

    @Override
    public void onResourceManagerReload(
            @Nonnull IResourceManager resourceManager,
            @Nonnull Predicate<IResourceType> resourcePredicate) {
        System.out.println("!!!!!!!!onResourceManagerReload!!!!!!!!!!");
    }
}
