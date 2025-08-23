package ${package}.client.renderer;

public class ${name}Renderer implements ICurioRenderer {

	private static final ResourceLocation TEXTURE = new ResourceLocation("${modid}", "textures/entities/${data.baubleModelTexture}");

	private final ${data.baubleModel} model;

	public ${name}Renderer() {
	  this.model = new ${data.baubleModel}(
		Minecraft.getInstance().getEntityModels().bakeLayer(${JavaModName}LayerDefinitions.${(registryname)?upper_case}));
	}

  @Override
  public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack,
                                                                        SlotContext slotContext,
                                                                        PoseStack matrixStack,
                                                                        RenderLayerParent<T, M> renderLayerParent,
                                                                        MultiBufferSource renderTypeBuffer,
                                                                        int light, float limbSwing,
                                                                        float limbSwingAmount,
                                                                        float partialTicks,
                                                                        float ageInTicks,
                                                                        float netHeadYaw,
                                                                        float headPitch) {
    LivingEntity entity = slotContext.entity();
    <#if data.translateModel>
    ICurioRenderer.translateIfSneaking(matrixStack, entity);
    </#if>
    <#if data.rotateModel>
    ICurioRenderer.rotateIfSneaking(matrixStack, entity);
    </#if>
    this.model.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTicks);
    this.model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
    VertexConsumer vertexconsumer = ItemRenderer
        .getArmorFoilBuffer(renderTypeBuffer, RenderType.armorCutoutNoCull(TEXTURE), false,
            stack.hasFoil());
    this.model
        .renderToBuffer(matrixStack, vertexconsumer, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F,
            1.0F, 1.0F);
  }

}