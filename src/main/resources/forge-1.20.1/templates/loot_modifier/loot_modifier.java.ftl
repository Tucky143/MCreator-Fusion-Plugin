package ${package}.init;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = ${JavaModName}.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ${JavaModName}LootModifier {

    public static class ${JavaModName}LootTableModifier extends LootModifier {

        public static final Supplier<Codec<${JavaModName}LootTableModifier>> CODEC = Suppliers.memoize(
                () -> RecordCodecBuilder.create(instance -> codecStart(instance)
                        .and(ResourceLocation.CODEC.fieldOf("lootTable").forGetter(m -> m.lootTable))
                        .apply(instance, ${JavaModName}LootTableModifier::new)
                )
        );

        private final ResourceLocation lootTable;

        public ${JavaModName}LootTableModifier(LootItemCondition[] conditions, ResourceLocation lootTable) {
            super(conditions);
            this.lootTable = lootTable;
        }

        @Override
        protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
            context.getResolver().getLootTable(lootTable).getRandomItemsRaw(context, generatedLoot::add);
            return generatedLoot;
        }

        @Override
        public Codec<? extends IGlobalLootModifier> codec() {
            return CODEC.get();
        }
    }

    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIERS = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, "${modid}");

    public static final RegistryObject<Codec<${JavaModName}LootTableModifier>> LOOT_MODIFIER = LOOT_MODIFIERS.register("${modid}_loot_modifier", ${JavaModName}LootTableModifier.CODEC);

	@SubscribeEvent
	public static void register(FMLConstructModEvent event) {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		event.enqueueWork(() -> {
			LOOT_MODIFIERS.register(bus);
		});
	}

}