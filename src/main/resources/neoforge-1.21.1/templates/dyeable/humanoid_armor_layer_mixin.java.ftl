package ${package}.mixins;

import org.spongepowered.asm.mixin.Mutable;

@Mixin(HumanoidArmorLayer.class)
public abstract class HumanoidArmorLayerMixin<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> extends RenderLayer<T, M> {
    @Mutable
    @Final
    @Shadow
    private final A innerModel;

    @Mutable
    @Final
    @Shadow
    private final A outerModel;

    @Mutable
    @Final
    @Shadow
    private final TextureAtlas armorTrimAtlas;

    public HumanoidArmorLayerMixin(RenderLayerParent<T, M> renderer, A innerModel, A outerModel, ModelManager modelManager) {
        super(renderer);
        this.innerModel = innerModel;
        this.outerModel = outerModel;
        this.armorTrimAtlas = modelManager.getAtlas(Sheets.ARMOR_TRIMS_SHEET);
    }

    @Inject(method = "renderArmorPiece", at = @At(value = "HEAD"), cancellable = true)
    private void renderArmorPiece(PoseStack poseStack, MultiBufferSource buffer, T entity, EquipmentSlot slot,
                                  int packedLight, A model, CallbackInfo ci) {
        ItemStack itemstack = entity.getItemBySlot(slot);

        if (itemstack.getItem() instanceof ArmorItem armoritem && armoritem.getEquipmentSlot() == slot) {
            getParentModel().copyPropertiesTo(model);
            model.setAllVisible(false);
            switch (slot) {
                case HEAD -> {
                    model.head.visible = true;
                    model.hat.visible = true;
                }
                case CHEST -> {
                    model.body.visible = true;
                    model.rightArm.visible = true;
                    model.leftArm.visible = true;
                }
                case LEGS -> {
                    model.body.visible = true;
                    model.rightLeg.visible = true;
                    model.leftLeg.visible = true;
                }
                case FEET -> {
                    model.rightLeg.visible = true;
                    model.leftLeg.visible = true;
                }
            }

            Model armorModel = ClientHooks.getArmorModel(entity, itemstack, slot, model);
            boolean flag = slot == EquipmentSlot.LEGS;
            ArmorMaterial armormaterial = armoritem.getMaterial().value();

            int i = -1;

            <#list w.getGElementsOfType("dyeable_armor")?filter(e -> e.hasDyeablePart()) as e>
                <#list e.getDyeableParts() as part>
                    <#if e?counter == 1 && part?counter == 1>
            if (itemstack.is(${JavaModName}Items.${e.getModElement().getRegistryNameUpper()}_${part})) {
                i = DyedItemColor.getOrDefault(itemstack, ${e.getDefaultColor(part).getRGB()});
            }
                    <#else>
            else if (itemstack.is(${JavaModName}Items.${e.getModElement().getRegistryNameUpper()}_${part})) {
                i = DyedItemColor.getOrDefault(itemstack, ${e.getDefaultColor(part).getRGB()});
            }
                    </#if>
                </#list>
            </#list>

            if (i == -1 && itemstack.is(ItemTags.DYEABLE)) {
                i = DyedItemColor.getOrDefault(itemstack, -6265536);
            }

            for (ArmorMaterial.Layer layer : armormaterial.layers()) {
                float r = 1.0F, g = 1.0F, b = 1.0F;
                if (layer.dyeable() && i != -1) {
                    r = (float) FastColor.ARGB32.red(i) / 255F;
                    g = (float) FastColor.ARGB32.green(i) / 255F;
                    b = (float) FastColor.ARGB32.blue(i) / 255F;
                }

                ResourceLocation texture = ClientHooks.getArmorTexture(entity, itemstack, layer, flag, slot);
                VertexConsumer vertexconsumer = buffer.getBuffer(RenderType.armorCutoutNoCull(texture));
                int tint = FastColor.ARGB32.color(255, (int)(r * 255), (int)(g * 255), (int)(b * 255));
                armorModel.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY, tint);
            }

            ArmorTrim armortrim = itemstack.get(DataComponents.TRIM);
            if (armortrim != null) {
                TextureAtlasSprite sprite = armorTrimAtlas.getSprite(flag ? armortrim.innerTexture(armoritem.getMaterial()) : armortrim.outerTexture(armoritem.getMaterial()));
                VertexConsumer trimConsumer = sprite.wrap(buffer.getBuffer(Sheets.armorTrimsSheet(armortrim.pattern().value().decal())));
                armorModel.renderToBuffer(poseStack, trimConsumer, packedLight, OverlayTexture.NO_OVERLAY);
            }

            if (itemstack.hasFoil()) {
                armorModel.renderToBuffer(poseStack, buffer.getBuffer(RenderType.armorEntityGlint()), packedLight, OverlayTexture.NO_OVERLAY);
            }
        }

        ci.cancel();
    }
}