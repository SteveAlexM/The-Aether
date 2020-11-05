package com.legacy.aether.client.rendering.entity;

import com.legacy.aether.Aether;
import com.legacy.aether.entities.projectile.EntityDart;
import com.legacy.aether.entities.projectile.EntityEnchantedDart;
import com.legacy.aether.entities.projectile.EntityGoldenDart;
import com.legacy.aether.entities.projectile.EntityPoisonNeedle;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.util.Identifier;

public class DartRenderer extends ProjectileEntityRenderer<EntityDart> {

    public DartRenderer(EntityRenderDispatcher renderManager) {
        super(renderManager);
        this.shadowOpacity = 0.0F;
    }

    @Override
    public Identifier getTexture(EntityDart entity) {
        String base = entity instanceof EntityGoldenDart ? "golden" : entity instanceof EntityEnchantedDart ? "enchanted" : "poison";

        return Aether.locate("textures/entity/projectile/dart/" + base + (entity instanceof EntityPoisonNeedle ? "_needle" : "_dart") + ".png");
    }

}
