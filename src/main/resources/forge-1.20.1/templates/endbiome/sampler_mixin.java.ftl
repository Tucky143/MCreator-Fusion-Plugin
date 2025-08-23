package ${package}.mixins;

import com.google.common.base.Preconditions;

import org.spongepowered.asm.mixin.Unique;

@Mixin(Climate.Sampler.class)
public class ClimateSamplerMixin implements SamplerHooks {
    @Unique
    private Long seed = null;

    @Unique
    private ImprovedNoise endBiomesSampler = null;

	@Override
	public void setSeed(long seed) {
		this.seed = seed;
	}

	@Override
	public long getSeed() {
		return this.seed;
	}

	@Override
	public ImprovedNoise getEndBiomesSampler() {
		if (endBiomesSampler == null) {
			Preconditions.checkState(seed != null, "MultiNoiseSampler doesn't have a seed set, created using different method?");
			endBiomesSampler = new ImprovedNoise(new WorldgenRandom(new LegacyRandomSource(seed)));
		}

		return endBiomesSampler;
	}

}