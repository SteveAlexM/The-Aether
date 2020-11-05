package com.legacy.aether.client.rendering.entity.layer;

import com.legacy.aether.Aether;
import com.legacy.aether.client.model.SheepuffModel;
import com.legacy.aether.client.model.SheepuffWoolModel;
import com.legacy.aether.client.model.SheepuffedModel;
import com.legacy.aether.entities.passive.EntitySheepuff;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

public class SheepuffCoatLayer extends FeatureRenderer<EntitySheepuff, SheepuffWoolModel> {

    private static final Identifier TEXTURE_FUR = Aether.locate("textures/entity/sheepuff/fur.png");

    private SheepuffModel woolModel;

    private SheepuffedModel puffedModel;

    public SheepuffCoatLayer(FeatureRendererContext<EntitySheepuff, SheepuffWoolModel> context) {
        super(context);

        this.woolModel = new SheepuffModel(0.0f);
        this.puffedModel = new SheepuffedModel(0.0f);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, EntitySheepuff sheepuff, float limbAngle, float limbDistance, float tickDelta, float customAngle, float netHeadYaw, float headPitch) {
        if (!sheepuff.isSheared() && !sheepuff.isInvisible()) {
            GlStateManager.pushMatrix();

            if (sheepuff.hasCustomName() && "jeb_".equals(sheepuff.getName().asString())) {
                int i = sheepuff.age / 25 + sheepuff.getEntityId();
                int j1 = DyeColor.values().length;
                int k = i % j1;
                int l = (i + 1) % j1;
                float f = ((float) (sheepuff.age % 25) + tickDelta) / 25.0F;
                float[] afloat1 = EntitySheepuff.getRgbColor(DyeColor.byId(k));
                float[] afloat2 = EntitySheepuff.getRgbColor(DyeColor.byId(l));
                GlStateManager.color4f(afloat1[0] * (1.0F - f) + afloat2[0] * f, afloat1[1] * (1.0F - f) + afloat2[1] * f, afloat1[2] * (1.0F - f) + afloat2[2] * f, 1.0f);
            } else {
                float[] dye = EntitySheepuff.getRgbColor(sheepuff.getColor());
                GlStateManager.color4f(dye[0], dye[1], dye[2], 1.0f);
            }

            if (sheepuff.isPuffed()) {
                this.puffedModel.copyStateTo(this.getContextModel());
                this.puffedModel.animateModel(sheepuff, limbAngle, limbDistance, tickDelta);
                VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCull(TEXTURE_FUR));
                this.puffedModel.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
            } else {
                this.woolModel.copyStateTo(this.getContextModel());
                this.woolModel.animateModel(sheepuff, limbAngle, limbDistance, tickDelta);
                VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCull(TEXTURE_FUR));
                this.woolModel.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
            }

            GlStateManager.popMatrix();
        }
    }

    // TODO: ???
    /*@Override
    public boolean hasHurtOverlay()
    {
        return false;
    }*/

}
