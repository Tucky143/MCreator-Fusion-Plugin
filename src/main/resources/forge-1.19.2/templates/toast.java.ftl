package ${package}.client.toasts;

public class ${name}Toast implements Toast {

    @Override
    public Visibility render(GuiGraphics guiGraphics, ToastComponent component, long lastChanged) {
         guiGraphics.blit(<#if data.background == "Default">TEXTURE<#else>new ResourceLocation("${modid}:textures/screens/${data.background}")</#if>, 0, 0, 0, 32, this.width(), this.height());
         guiGraphics.drawString(component.getMinecraft().font, Component.translatable("toasts.${modid}.${registryname}.title"), 30, 7, -11534256, false);
         guiGraphics.drawString(component.getMinecraft().font, Component.translatable("toasts.${modid}.${registryname}.description"), 30, 18, -16777216, false);

         RenderSystem.enableBlend();
		<#if data.icon != "None">
         guiGraphics.blit(new ResourceLocation("${modid}:textures/screens/${data.icon}"), 6, 6, 296, 120, 20, 20);
		</#if>

		if (lastChanged <= ${data.durationTime})
			return Visibility.SHOW;
		else
			return Visibility.HIDE;
    }
}
