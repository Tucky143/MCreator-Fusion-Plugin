package ${package}.mixin;

import org.spongepowered.asm.mixin.Unique;

@Mixin(NoiseChunk.class)
public class ChunkNoiseSamplerMixin {
	@Unique
	private long seed;

	@Inject(method = "<init>", at = @At("TAIL"))
	private void init(int horizontalSize, RandomState noiseConfig, int i, int j, NoiseSettings generationShapeConfig, DensityFunctions.BeardifierOrMarker arg, NoiseGeneratorSettings chunkGeneratorSettings, Aquifer.FluidPicker fluidLevelSampler, Blender blender, CallbackInfo ci) {
		seed = ((SamplerHooks) (Object) noiseConfig.sampler()).getSeed();
	}

	@Inject(method = "cachedClimateSampler", at = @At("RETURN"))
	private void createMultiNoiseSampler(NoiseRouter noiseRouter, List<Climate.ParameterPoint> list, CallbackInfoReturnable<Climate.Sampler> cir) {
		((SamplerHooks) (Object) cir.getReturnValue()).setSeed(seed);
	}
}