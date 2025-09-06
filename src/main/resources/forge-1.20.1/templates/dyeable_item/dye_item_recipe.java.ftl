package ${package}.item.crafting;

public class DyeItemRecipe extends CustomRecipe {
    public DyeItemRecipe(ResourceLocation id, CraftingBookCategory category) {
        super(id, category);
    }

    @Override
    public boolean matches(CraftingContainer container, Level level) {
        ItemStack item = ItemStack.EMPTY;
        List<ItemStack> dyes = new ArrayList<>();

        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack item$container = container.getItem(i);

            if (!item$container.isEmpty()) {
                if (item$container.getItem() instanceof IDyeableItem) {
                    if (!item.isEmpty())
                        return false;
                    item = item$container;
                } else {
                    if (!(item$container.getItem() instanceof DyeItem))
                        return false;
                    dyes.add(item$container);
                }
            }
        }

        return !item.isEmpty() && !dyes.isEmpty();
    }

    @Override
    public ItemStack assemble(CraftingContainer container, RegistryAccess access) {
        ItemStack item = ItemStack.EMPTY;
        List<DyeItem> dyes = new ArrayList<>();

        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack item$container = container.getItem(i);

            if (!item$container.isEmpty()) {
                if (item$container.getItem() instanceof IDyeableItem) {
                    if (!item.isEmpty())
                        return ItemStack.EMPTY;
                    item = item$container.copy();
                } else {
                    if (!(item$container.getItem() instanceof DyeItem))
                        return ItemStack.EMPTY;
                    dyes.add((DyeItem) item$container.getItem());
                }
            }
        }
        return !item.isEmpty() && !dyes.isEmpty() ? IDyeableItem.dye(item, dyes) : ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ${JavaModName}RecipeSerializers.DYEABLE_ITEM.get();
    }
}