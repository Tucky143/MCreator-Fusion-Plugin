<#--
 # MCreator (https://mcreator.net/)
 # Copyright (C) 2012-2020, Pylo
 # Copyright (C) 2020-2024, Pylo, opensource contributors
 #
 # This program is free software: you can redistribute it and/or modify
 # it under the terms of the GNU General Public License as published by
 # the Free Software Foundation, either version 3 of the License, or
 # (at your option) any later version.
 #
 # This program is distributed in the hope that it will be useful,
 # but WITHOUT ANY WARRANTY; without even the implied warranty of
 # MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 # GNU General Public License for more details.
 #
 # You should have received a copy of the GNU General Public License
 # along with this program.  If not, see <https://www.gnu.org/licenses/>.
 #
 # Additional permission for code generator templates (*.ftl files)
 #
 # As a special exception, you may create a larger work that contains part or
 # all of the MCreator code generator templates (*.ftl files) and distribute
 # that work under terms of your choice, so long as that work isn't itself a
 # template for code generation. Alternatively, if you modify or redistribute
 # the template itself, you may (at your option) remove this special exception,
 # which will cause the template and the resulting code generator output files
 # to be licensed under the GNU General Public License without this special
 # exception.
-->

<#-- @formatter:off -->
<#include "procedures.java.ftl">

<#assign models = w.hasElementsOfType("particlemodel")?then(w.getGElementsOfType("particlemodel")?filter(model -> model.particle == name), "")>
<#assign model = models?has_content?then(models[0], "")>

package ${package}.client.particle;

import com.mojang.math.Axis;

<#compress>
@OnlyIn(Dist.CLIENT) public class ${name}Particle extends TextureSheetParticle {

	public static ${name}ParticleProvider provider(SpriteSet spriteSet) {
		return new ${name}ParticleProvider(spriteSet);
	}

	public static class ${name}ParticleProvider implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet spriteSet;

		public ${name}ParticleProvider(SpriteSet spriteSet) {
			this.spriteSet = spriteSet;
		}

		public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			${name}Particle particle = new ${name}Particle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
			<#if model != "">
			class ${name}RenderSequence {
				private ClientLevel world;
				public EntityModel model = new ${model.model}(Minecraft.getInstance().getEntityModels().bakeLayer(${model.model}.LAYER_LOCATION));
				private float scale = (float) <#if hasProcedure(model.modelScale)><@procedureOBJToNumberCode model.modelScale/><#else>${model.modelScale.getFixedValue()}</#if>;
				private int rotX = (int) <#if hasProcedure(model.modelRotationX)><@procedureOBJToNumberCode model.modelRotationX/><#else>${model.modelRotationX.getFixedValue()}</#if>;
				private int rotY = (int) <#if hasProcedure(model.modelRotationX)><@procedureOBJToNumberCode model.modelRotationY/><#else>${model.modelRotationX.getFixedValue()}</#if>;
				private int rotZ = (int) <#if hasProcedure(model.modelRotationX)><@procedureOBJToNumberCode model.modelRotationZ/><#else>${model.modelRotationX.getFixedValue()}</#if>;

				@SubscribeEvent
				public void render(RenderLevelStageEvent event) {
					if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_PARTICLES) {
					<#assign rendertype = "">
					<#if model.rendertype == "Cutout">
						<#assign rendertype = "entityCutoutNoCull(new ResourceLocation(\"${modid}:textures/particle/${data.getModElement().getRegistryName()}.png\"))">
					<#elseif model.rendertype == "Translucent">
						<#assign rendertype = "entityTranslucent(new ResourceLocation(\"${modid}:textures/particle/${data.getModElement().getRegistryName()}.png\"))">
					<#elseif model.rendertype == "Glowing">
						<#assign rendertype = "entityTranslucentEmissive(new ResourceLocation(\"${modid}:textures/particle/${data.getModElement().getRegistryName()}.png\"))">
					<#elseif model.rendertype == "End portal">
						<#assign rendertype = "endPortal()">
					</#if>
						VertexConsumer consumer = Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(RenderType.${rendertype});
						Vec3 camPos = event.getCamera().getPosition();
						double x = Mth.lerp(event.getPartialTick(), particle.xo, particle.x) - camPos.x();
						double y = Mth.lerp(event.getPartialTick(), particle.yo, particle.y) - camPos.y();
						double z = Mth.lerp(event.getPartialTick(), particle.zo, particle.z) - camPos.z();
						event.getPoseStack().pushPose();
						event.getPoseStack().translate(x, y, z);
						event.getPoseStack().mulPose(Axis.XP.rotationDegrees(180));
						event.getPoseStack().scale(scale, scale, scale);
						event.getPoseStack().mulPose(Axis.XP.rotationDegrees(rotX));
						event.getPoseStack().mulPose(Axis.YP.rotationDegrees(rotY));
						event.getPoseStack().mulPose(Axis.ZP.rotationDegrees(rotZ));
						model.renderToBuffer(event.getPoseStack(), consumer, particle.getLightColor(event.getPartialTick()), OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
						event.getPoseStack().popPose();
					}
				}

				public void start(ClientLevel world) {
					MinecraftForge.EVENT_BUS.register(this);
					this.world = world;
				}

				@SubscribeEvent
				public void tick(TickEvent.ClientTickEvent event) {
					if (!particle.isAlive())
						end();
				}

				@SubscribeEvent
				public void dimensionChange(PlayerEvent.PlayerChangedDimensionEvent event) {
					end();
				}

				@SubscribeEvent
				public void loggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
					end();
				}

				private void end() {
					MinecraftForge.EVENT_BUS.unregister(this);
				}
			}
			${name}RenderSequence sequence = new ${name}RenderSequence();
			sequence.start(worldIn);
			</#if>
			return particle;
		}
	}

	private final SpriteSet spriteSet;

	<#if data.angularVelocity != 0 || data.angularAcceleration != 0>
	private float angularVelocity;
	private float angularAcceleration;
	</#if>

	protected ${name}Particle(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
		super(world, x, y, z);
		this.spriteSet = spriteSet;

		this.setSize(${data.width}f, ${data.height}f);

		<#if data.scale.getFixedValue() != 1 && !hasProcedure(data.scale)>
		this.quadSize *= ${data.scale.getFixedValue()}f;
		</#if>

		<#if (data.maxAgeDiff > 0)>
		this.lifetime = (int) Math.max(1, ${data.maxAge} + (this.random.nextInt(${data.maxAgeDiff * 2}) - ${data.maxAgeDiff}));
		<#else>
		this.lifetime = ${data.maxAge};
		</#if>

		this.gravity = ${data.gravity}f;
		this.hasPhysics = ${data.canCollide};

		this.xd = vx * ${data.speedFactor};
		this.yd = vy * ${data.speedFactor};
		this.zd = vz * ${data.speedFactor};

		<#if data.angularVelocity != 0 || data.angularAcceleration != 0>
		this.angularVelocity = ${data.angularVelocity}f;
		this.angularAcceleration = ${data.angularAcceleration}f;
		</#if>

		<#if data.animate>
		this.setSpriteFromAge(spriteSet);
		<#else>
		this.pickSprite(spriteSet);
		</#if>
	}

	<#if data.renderType == "LIT">
	@Override public int getLightColor(float partialTick) {
		return 15728880;
	}
	</#if>

	@Override public ParticleRenderType getRenderType() {
		<#if model == "">
		return ParticleRenderType.PARTICLE_SHEET_${data.renderType};
		<#else>
		return ParticleRenderType.NO_RENDER;
		</#if>
	}

	<#if hasProcedure(data.scale)>
	@Override public float getQuadSize(float scale) {
		Level world = this.level;
		return super.getQuadSize(scale) * (float) <@procedureOBJToConditionCode data.scale/>;
	}
	</#if>

	@Override public void tick() {
		super.tick();

		<#if data.angularVelocity != 0 || data.angularAcceleration != 0>
		this.oRoll = this.roll;
		this.roll += this.angularVelocity;
		this.angularVelocity += this.angularAcceleration;
		</#if>

		<#if data.animate>
		if(!this.removed) {
			<#assign frameCount = data.getTextureTileCount()>
			this.setSprite(this.spriteSet.get((this.age / ${data.frameDuration}) % ${frameCount} + 1, ${frameCount}));
		}
		</#if>

		<#if hasProcedure(data.additionalExpiryCondition)>
		Level world = this.level;
		if (<@procedureOBJToConditionCode data.additionalExpiryCondition/>)
			this.remove();
		</#if>
	}

}
</#compress>
<#-- @formatter:on -->