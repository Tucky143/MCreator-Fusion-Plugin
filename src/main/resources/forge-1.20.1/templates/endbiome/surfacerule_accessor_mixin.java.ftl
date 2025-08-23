package ${package}.mixins;

import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.Mutable;

@Mixin (NoiseGeneratorSettings.class)
public interface NoiseGeneratorSettingsAccess {

    @Accessor("surfaceRule") @Mutable
    void addSurfaceRule(SurfaceRules.RuleSource ruleSource);
}