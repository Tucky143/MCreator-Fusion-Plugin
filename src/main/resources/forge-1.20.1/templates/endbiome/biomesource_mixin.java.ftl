package ${package}.mixins;

import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BiomeSource.class)
public abstract class BiomeSourceMixin {

	@Redirect(method = "possibleBiomes", at = @At(value = "INVOKE", target = "Ljava/util/function/Supplier;get()Ljava/lang/Object;"))
	private Object getBiomes(Supplier<Set<Holder<Biome>>> instance) {
		return modifyBiomeSet(instance.get());
	}

	protected Set<Holder<Biome>> modifyBiomeSet(Set<Holder<Biome>> biomes) {
	    return biomes;
    }

}