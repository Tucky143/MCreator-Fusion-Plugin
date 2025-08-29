package ${package}.jei_recipes;

<#compress>

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.NonNullList;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.ArrayList;

public record ${name}Recipe(
    ResourceLocation id,
    <#list data.slotList as slot>
        <#if slot.type == "ITEM_INPUT">
            SizedIngredient ${slot.name}ItemInput,
        <#elseif slot.type == "FLUID_INPUT">
            net.minecraftforge.fluids.FluidStack ${slot.name}FluidInput,
        </#if>
    </#list>
    SizedIngredient output
) implements Recipe<net.minecraft.world.Container> {

    @Override
    public @Nonnull ResourceLocation getId() {
        return id;
    }

    @Override
    public @Nonnull NonNullList<Ingredient> getIngredients() {
        List<SizedIngredient> sizedIngredients = new ArrayList<>();
        <#list data.slotList as slot>
            <#if slot.type == "ITEM_INPUT">
                sizedIngredients.add(${slot.name}ItemInput);
            </#if>
        </#list>
        NonNullList<Ingredient> ingredients = NonNullList.createWithCapacity(sizedIngredients.size());
        for (SizedIngredient si : sizedIngredients) {
            ingredients.add(si.ingredient());
        }
        return ingredients;
    }

    @Override
    public boolean matches(@Nonnull net.minecraft.world.Container container, @Nonnull Level level) {
        return false;
    }

    @Override
    public boolean canCraftInDimensions(int w, int h) {
        return true;
    }

    @Override
    public @Nonnull ItemStack assemble(@Nonnull net.minecraft.world.Container container,
                                       @Nonnull net.minecraft.core.RegistryAccess registryAccess) {
        return ItemStack.EMPTY;
    }

    @Override
    public @Nonnull ItemStack getResultItem(@Nonnull net.minecraft.core.RegistryAccess registryAccess) {
        ItemStack[] stacks = output.getItems();
        return stacks.length > 0 ? stacks[0].copy() : ItemStack.EMPTY;
    }

    public @Nonnull ItemStack getResult(int idx) {
        ItemStack[] stack = output.getItems();
        return stack[0];
    }

    public ItemStack assemble(@Nonnull net.minecraft.world.Container container) {
        return ItemStack.EMPTY;
    }

    @Override
    public @Nonnull RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public @Nonnull RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<${name}Recipe> {
        private Type() {}
        public static final RecipeType<${name}Recipe> INSTANCE = new Type();
    }

    public static class Serializer implements RecipeSerializer<${name}Recipe> {
        public static final Serializer INSTANCE = new Serializer();

        @Override
        public ${name}Recipe fromJson(ResourceLocation id, com.google.gson.JsonObject json) {
            <#list data.slotList as slot>
                <#if slot.type == "ITEM_INPUT">
                    SizedIngredient ${slot.name}ItemInput = SizedIngredient.fromJson(json.get("${slot.name}"));
                <#elseif slot.type == "FLUID_INPUT">
                    net.minecraftforge.fluids.FluidStack ${slot.name}FluidInput =
                        net.minecraftforge.fluids.FluidStack.loadFluidStackFromNBT(
                            net.minecraft.nbt.TagParser.parseTag(
                                com.google.gson.internal.Streams.parse(
                                    new com.google.gson.JsonParser().parse(json.get("${slot.name}").toString())
                                ).toString()
                            )
                        );
                </#if>
            </#list>
            SizedIngredient output = SizedIngredient.fromJson(json.get("output"));
            return new ${name}Recipe(
                id,
                <#list data.slotList as slot>
                    <#if slot.type == "ITEM_INPUT">
                        ${slot.name}ItemInput,
                    <#elseif slot.type == "FLUID_INPUT">
                        ${slot.name}FluidInput,
                    </#if>
                </#list>
                output
            );
        }

        @Override
        public ${name}Recipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            <#list data.slotList as slot>
                <#if slot.type == "ITEM_INPUT">
                    SizedIngredient ${slot.name}ItemInput = SizedIngredient.read(buf);
                <#elseif slot.type == "FLUID_INPUT">
                    net.minecraftforge.fluids.FluidStack ${slot.name}FluidInput = buf.readFluidStack();
                </#if>
            </#list>
            SizedIngredient output = SizedIngredient.read(buf);
            return new ${name}Recipe(
                id,
                <#list data.slotList as slot>
                    <#if slot.type == "ITEM_INPUT">
                        ${slot.name}ItemInput,
                    <#elseif slot.type == "FLUID_INPUT">
                        ${slot.name}FluidInput,
                    </#if>
                </#list>
                output
            );
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, ${name}Recipe recipe) {
            <#list data.slotList as slot>
                <#if slot.type == "ITEM_INPUT">
                    recipe.${slot.name}ItemInput.toNetwork(buf);
                <#elseif slot.type == "FLUID_INPUT">
                    buf.writeFluidStack(recipe.${slot.name}FluidInput);
                </#if>
            </#list>
            recipe.output.toNetwork(buf);
        }
    }
}
</#compress>