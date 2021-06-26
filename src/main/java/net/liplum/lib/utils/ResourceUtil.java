package net.liplum.lib.utils;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.liplum.Resources;
import net.liplum.lib.json.TransformDeserializer;
import net.liplum.renders.models.WeaponModels;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.model.TRSRTransformation;
import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Map;

public final class ResourceUtil {
    private static final Gson WeaponModelGson = new GsonBuilder()

            .create();

    public static Reader getReaderFromResource(ResourceLocation location, IResourceManager resourceManager) throws IOException {
        ResourceLocation file = new ResourceLocation(location.getResourceDomain(), location.getResourcePath() + Resources.JSON_Extension);
        IResource resource = resourceManager.getResource(file);
        return new BufferedReader(new InputStreamReader(resource.getInputStream(), Charsets.UTF_8));
    }

    public static Reader getReaderFromResource(ResourceLocation location) throws IOException {
        return getReaderFromResource(location, Minecraft.getMinecraft().getResourceManager());
    }

    public static Map<String, String> loadTexturesFromJson(ResourceLocation location) throws IOException {
        Reader reader = getReaderFromResource(location);
        try {
            return WeaponModelGson.fromJson(reader, WeaponModels.ModelTextureDeserializer.TYPE);
        } finally {
            IOUtils.closeQuietly(reader);
        }
    }

    public static ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> loadTransformFromJson(ResourceLocation location)
            throws IOException {
        return loadTransformFromJson(location, "display");
    }

    public static ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> loadTransformFromJson(ResourceLocation location, String tag)
            throws IOException {
        Reader reader = getReaderFromResource(location);
        try {
            TransformDeserializer.tag = tag;
            ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms = WeaponModelGson.fromJson(reader, TransformDeserializer.TYPE);

            // filter out missing/identity entries
            ImmutableMap.Builder<ItemCameraTransforms.TransformType, TRSRTransformation> builder = ImmutableMap.builder();
            for (Map.Entry<ItemCameraTransforms.TransformType, TRSRTransformation> entry : transforms.entrySet()) {
                if (!entry.getValue().equals(TRSRTransformation.identity())) {
                    builder.put(entry.getKey(), entry.getValue());
                }
            }

            return builder.build();
        } finally {
            IOUtils.closeQuietly(reader);
        }
    }

    public static boolean isExisted(String resourceLocation) {
        return isExisted(new ResourceLocation(resourceLocation));
    }

    public static boolean isExisted(ResourceLocation resourceLocation) {
        List<IResource> resources = null;
        try {
            resourceLocation = new ResourceLocation(resourceLocation.getResourceDomain(),
                    "textures/" + resourceLocation.getResourcePath() + ".png");
            resources = Minecraft.getMinecraft().getResourceManager().getAllResources(resourceLocation);
        } catch (IOException e) {
            return false;
        } finally {
            if (resources != null) {
                for (IResource resource : resources) {
                    IOUtils.closeQuietly(resource);
                }
            }
        }
        return true;
    }
}
