package net.liplum.models;// Made with Blockbench 3.9.2
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports


import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

import javax.annotation.Nonnull;

public class GemswordBeamModel extends ModelBase {
    private final ModelRenderer bone;
    private final ModelRenderer cube_r1;
    private final ModelRenderer cube_r2;

    public GemswordBeamModel() {
        textureWidth = 32;
        textureHeight = 32;

        bone = new ModelRenderer(this);
        bone.setRotationPoint(0.0F, 22.0F, 0.0F);
        bone.cubeList.add(new ModelBox(bone, 4, 0, -0.5F, 0.0F, -6.0F, 1, 0, 10, 0.0F, false));

        cube_r1 = new ModelRenderer(this);
        cube_r1.setRotationPoint(0.5623F, 0.0F, -1.0246F);
        bone.addChild(cube_r1);
        setRotationAngle(cube_r1, 0.0F, 0.0873F, 0.0F);
        cube_r1.cubeList.add(new ModelBox(cube_r1, 0, 0, -0.5F, 0.0F, -5.0F, 1, 0, 10, 0.0F, false));

        cube_r2 = new ModelRenderer(this);
        cube_r2.setRotationPoint(-0.5623F, 0.0F, -1.0246F);
        bone.addChild(cube_r2);
        setRotationAngle(cube_r2, 0.0F, -0.0873F, 0.0F);
        cube_r2.cubeList.add(new ModelBox(cube_r2, 2, 0, -0.5F, 0.0F, -5.0F, 1, 0, 10, 0.0F, false));
    }

    @Override
    public void render(@Nonnull Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        bone.render(scale);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}