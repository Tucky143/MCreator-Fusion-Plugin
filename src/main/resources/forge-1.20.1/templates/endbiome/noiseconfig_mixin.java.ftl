package ${package}.mixins;

@Mixin(RandomState.class)
public class NoiseConfigMixin {
	@Shadow
	@Final
	private Climate.Sampler sampler;

	@Inject(method = "<init>", at = @At("TAIL"))
	private void init(NoiseGeneratorSettings chunkGeneratorSettings, HolderGetter<NormalNoise.NoiseParameters> arg, long seed, CallbackInfo ci) {
		((SamplerHooks) (Object) sampler).setSeed(seed);
	}
}