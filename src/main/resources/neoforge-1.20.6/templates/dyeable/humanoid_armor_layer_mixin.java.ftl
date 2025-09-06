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

    public HumanoidArmorLayerMixin(RenderLayerParent<T, M> pRenderer, A pInnerModel, A pOuterModel, ModelManager pModelManager) {
        super(pRenderer);

        this.innerModel = pInnerModel;
        this.outerModel = pOuterModel;
        this.armorTrimAtlas = pModelManager.getAtlas(Sheets.ARMOR_TRIMS_SHEET);
    }

    @Inject(method = "renderArmorPiece", at = @At(value = "HEAD"), cancellable = true)
    private void renderArmorPiece(PoseStack poseStack, MultiBufferSource buffer, T entity, EquipmentSlot slot,
                                  int packedLight, A model, CallbackInfo ci) {

        ItemStack itemstack = entity.getItemBySlot(slot);

        if (itemstack.getItem() instanceof ArmorItem armoritem) {
            if (armoritem.getEquipmentSlot() == slot) {
                getParentModel().copyPropertiesTo(model);
                setPartVisibility(model, slot);

                Model armorModel = getArmorModelHook(entity, itemstack, slot, model);
                boolean flag = usesInnerModel(slot);

                ArmorMaterial armormaterial = armoritem.getMaterial().value();

                int i = itemstack.is(ItemTags.DYEABLE) ? DyedItemColor.getOrDefault(itemstack, -6265536) : -1;

                <#assign c = 1 />
                <#list w.getGElementsOfType("dyeable_armor")?filter(e -> e.hasDyeablePart()) as e>
                <#list e.getDyeableParts() as part>
                <#if c == 1>
                if (itemstack.is(${JavaModName}Items.${e.getModElement().getRegistryNameUpper()}_${part.toString()})) {
                    i =DyedItemColor.getOrDefault(itemstack, ${e.getDefaultColor(part).getRGB()});
                }
                <#else>
                else if (itemstack.is(${JavaModName}Items.${e.getModElement().getRegistryNameUpper()}_${part.toString()})) {
                    i =DyedItemColor.getOrDefault(itemstack, ${e.getDefaultColor(part).getRGB()});
                }
                </#if>
                <#assign c += 1 />
                </#list>
                </#list>

                for (ArmorMaterial.Layer armormaterial$layer : armormaterial.layers()) {
                    float f;
                    float f1;
                    float f2;

                    if (armormaterial$layer.dyeable() && i != -1) {
                        f = (float) FastColor.ARGB32.red(i) / 255;
                        f1 = (float) FastColor.ARGB32.green(i) / 255;
                        f2 = (float) FastColor.ARGB32.blue(i) / 255;
                    } else {
                        f = 1.0F;
                        f1 = 1.0F;
                        f2 = 1.0F;
                    }

                    ResourceLocation texture = ClientHooks.getArmorTexture(entity, itemstack, armormaterial$layer, flag, slot);
                    renderModel(poseStack, buffer, packedLight, armorModel, f, f1, f2, texture);
                }

                ArmorTrim armortrim = itemstack.get(DataComponents.TRIM);
                if (armortrim != null) {
                    renderTrim(armoritem.getMaterial(), poseStack, buffer, packedLight, armortrim, armorModel, flag);
                }

                if (itemstack.hasFoil()) {
                    renderGlint(poseStack, buffer, packedLight, armorModel);
                }
            }
        }

        ci.cancel();
    }

    @Shadow
    protected abstract void setPartVisibility(A model, EquipmentSlot slot);

    @Shadow
    protected abstract void renderModel(PoseStack poseStack, MultiBufferSource buffer, int packedLight, Model model,
            float red, float green, float blue, ResourceLocation texture);

    @Shadow
    protected abstract void renderTrim(Holder<ArmorMaterial> material, PoseStack poseStack, MultiBufferSource buffer,
                                       int packedLight, ArmorTrim trim, Model model, boolean innerTexture);

    @Shadow
    protected abstract void renderGlint(PoseStack poseStack, MultiBufferSource buffer, int packedLight, Model model);

    @Shadow
    protected abstract boolean usesInnerModel(EquipmentSlot slot);

    @Shadow
    protected abstract Model getArmorModelHook(T entity, ItemStack itemStack, EquipmentSlot slot, A model);
}