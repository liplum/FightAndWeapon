package net.liplum.lib.json;

import com.google.common.collect.ImmutableMap;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraftforge.client.model.PerspectiveMapWrapper;
import net.minecraftforge.common.model.TRSRTransformation;

import java.lang.reflect.Type;

public class TransformDeserializer
        implements JsonDeserializer<ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation>> {

    public static final TransformDeserializer INSTANCE = new TransformDeserializer();
    public static final Type TYPE = new TypeToken<ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation>>() {
    }.getType();

    public static String tag;

    @Override
    public ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();
        JsonElement texElem = obj.get(tag);

        if (texElem != null && texElem.isJsonObject()) {
            ItemCameraTransforms itemCameraTransforms = context.deserialize(texElem.getAsJsonObject(), ItemCameraTransforms.class);
            return PerspectiveMapWrapper.getTransforms(itemCameraTransforms);
        }

        return ImmutableMap.of();
    }
}
