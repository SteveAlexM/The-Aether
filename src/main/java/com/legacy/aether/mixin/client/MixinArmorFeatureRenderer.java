package com.legacy.aether.mixin.client;

import com.legacy.aether.item.armor.ItemAetherArmor;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(ArmorFeatureRenderer.class)
public abstract class MixinArmorFeatureRenderer<T extends LivingEntity, M extends BipedEntityModel<T>, A extends BipedEntityModel<T>> extends FeatureRenderer<T, M> {

    // See renderArmor Notes
	/*@Shadow private float red;

	@Shadow private float green;

	@Shadow private float blue;*/

    @Shadow
    @Final
    private static Map<String, Identifier> ARMOR_TEXTURE_CACHE;

    public MixinArmorFeatureRenderer(FeatureRendererContext<T, M> context) {
        super(context);
    }

    // Ref: May not be needed in 1.15 anymore
	/*@Inject(method = "renderArmor", at = @At("HEAD"))
	private void renderAetherArmor(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, T entity, float float_1, float float_2, float float_3, float float_4, float float_5, float float_6, EquipmentSlot slot, int int_1, CallbackInfo ci)
	{
		ItemStack item = entity.getEquippedStack(slot);

		if (item.getItem() instanceof ItemAetherArmor)
		{
			ItemAetherArmor armor = (ItemAetherArmor) item.getItem();

			this.red = (float) (armor.getType().getColor() >> 16 & 255) / 255.0F;
			this.green = (float) (armor.getType().getColor() >> 8 & 255) / 255.0F;
			this.blue = (float) (armor.getType().getColor() & 255) / 255.0F;
		}
		else
		{
			this.red = this.green = this.blue = 1.0F;
		}
	}*/

    @Inject(method = "getArmorTexture", at = @At("RETURN"), cancellable = true)
    private void getAetherArmorTexture(ArmorItem item, boolean isLeggings, String overlayName, CallbackInfoReturnable<Identifier> ci) {
        if (item instanceof ItemAetherArmor) {
            ItemAetherArmor armor = (ItemAetherArmor) item;

            ci.setReturnValue(ARMOR_TEXTURE_CACHE.computeIfAbsent((armor.getArmorName().equals("iron") ? "" : "aether_legacy:") + "textures/models/armor/" + armor.getArmorName() + "_layer_" + (isLeggings ? 2 : 1) + ".png", Identifier::new));
        }
    }

}