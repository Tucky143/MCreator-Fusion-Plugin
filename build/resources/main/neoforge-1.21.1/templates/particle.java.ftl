<#-- @formatter:off -->
<#include "procedures.java.ftl">

<#assign models = w.hasElementsOfType("particlemodel")?then(w.getGElementsOfType("particlemodel")?filter(model -> model.particle == name), "")>
<#assign model = models?has_content?then(models[0], "")>

package ${package}.client.particle;

import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.model.EntityModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.phys.Vec3;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.bus.api.SubscribeEvent;

@OnlyIn(Dist.CLIENT)
public class ${name}Particle extends TextureSheetParticle {

    public static ${name}ParticleProvider provider(SpriteSet spriteSet) {
        return new ${name}ParticleProvider(spriteSet);
    }

    public static class ${name}ParticleProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public ${name}ParticleProvider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            ${name}Particle particle = new ${name}Particle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);

            <#if model != "">
            class ${name}RenderSequence {
                private ClientLevel world;
                public EntityModel model = new ${model.model}(Minecraft.getInstance().getEntityModels().bakeLayer(${model.model}.LAYER_LOCATION));
                private final float scale = (float) <#if hasProcedure(model.modelScale)><@procedureOBJToNumberCode model.modelScale/><#else>${model.modelScale.getFixedValue()}</#if>;
                private final int rotX = (int) <#if hasProcedure(model.modelRotationX)><@procedureOBJToNumberCode model.modelRotationX/><#else>${model.modelRotationX.getFixedValue()}</#if>;
                private final int rotY = (int) <#if hasProcedure(model.modelRotationY)><@procedureOBJToNumberCode model.modelRotationY/><#else>${model.modelRotationY.getFixedValue()}</#if>;
                private final int rotZ = (int) <#if hasProcedure(model.modelRotationZ)><@procedureOBJToNumberCode model.modelRotationZ/><#else>${model.modelRotationZ.getFixedValue()}</#if>;

                @SubscribeEvent
                public void render(RenderLevelStageEvent event) {
                    if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_PARTICLES) {
                        float partialTick = event.getPartialTick().getGameTimeDeltaPartialTick(true); // ✅ 1.21.x fix

                        <#assign rendertype = "">
                        <#if model.rendertype == "Cutout">
                            <#assign rendertype = "entityCutoutNoCull(ResourceLocation.fromNamespaceAndPath(\"${modid}\", \"textures/particle/${data.getModElement().getRegistryName()}.png\"))">
                        <#elseif model.rendertype == "Translucent">
                            <#assign rendertype = "entityTranslucent(ResourceLocation.fromNamespaceAndPath(\"${modid}\", \"textures/particle/${data.getModElement().getRegistryName()}.png\"))">
                        <#elseif model.rendertype == "Glowing">
                            <#assign rendertype = "entityTranslucentEmissive(ResourceLocation.fromNamespaceAndPath(\"${modid}\", \"textures/particle/${data.getModElement().getRegistryName()}.png\"))">
                        <#elseif model.rendertype == "End portal">
                            <#assign rendertype = "endPortal()">
                        </#if>

                        VertexConsumer consumer = Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(RenderType.${rendertype});
                        Vec3 camPos = event.getCamera().getPosition();

                        double px = Mth.lerp(partialTick, particle.xo, particle.x) - camPos.x();
                        double py = Mth.lerp(partialTick, particle.yo, particle.y) - camPos.y();
                        double pz = Mth.lerp(partialTick, particle.zo, particle.z) - camPos.z();

                        event.getPoseStack().pushPose();
                        event.getPoseStack().translate(px, py, pz);
                        event.getPoseStack().mulPose(Axis.XP.rotationDegrees(180));
                        event.getPoseStack().scale(scale, scale, scale);
                        event.getPoseStack().mulPose(Axis.XP.rotationDegrees(rotX));
                        event.getPoseStack().mulPose(Axis.YP.rotationDegrees(rotY));
                        event.getPoseStack().mulPose(Axis.ZP.rotationDegrees(rotZ));
                        model.renderToBuffer(event.getPoseStack(),consumer,particle.getLightColor(partialTick),OverlayTexture.NO_OVERLAY);
                        event.getPoseStack().popPose();
                    }
                }

                public void start(ClientLevel world) {
                    NeoForge.EVENT_BUS.register(this);
                    this.world = world;
                }

                @SubscribeEvent
                public void tick(ClientTickEvent.Post event) {
                    if (!particle.isAlive()) end();
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
                    NeoForge.EVENT_BUS.unregister(this);
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
    @Override
    public int getLightColor(float partialTick) {
        return 15728880;
    }
    </#if>

    @Override
    public ParticleRenderType getRenderType() {
        <#if model == "">
        return ParticleRenderType.PARTICLE_SHEET_${data.renderType};
        <#else>
        return ParticleRenderType.NO_RENDER;
        </#if>
    }

    <#if hasProcedure(data.scale)>
    @Override
    public float getQuadSize(float scale) {
        Level world = this.level;
        return super.getQuadSize(scale) * (float) <@procedureOBJToConditionCode data.scale/>;
    }
    </#if>

    @Override
    public void tick() {
        super.tick();

        <#if data.angularVelocity != 0 || data.angularAcceleration != 0>
        this.oRoll = this.roll;
        this.roll += this.angularVelocity;
        this.angularVelocity += this.angularAcceleration;
        </#if>

        <#if data.animate>
        if (!this.removed) {
            <#assign frameCount = data.getTextureTileCount()>
            this.setSprite(this.spriteSet.get((this.age / ${data.frameDuration}) % ${frameCount} + 1, ${frameCount}));
        }
        </#if>

        <#if hasProcedure(data.additionalExpiryCondition)>
        Level world = this.level;
        if (<@procedureOBJToConditionCode data.additionalExpiryCondition/>) this.remove();
        </#if>
    }
}
<#-- @formatter:on -->
