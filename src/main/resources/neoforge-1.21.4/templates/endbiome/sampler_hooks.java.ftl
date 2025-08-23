package ${package}.endbiomes;

public interface SamplerHooks {
    ImprovedNoise getEndBiomesSampler();

    void setSeed(long seed);

    long getSeed();
}