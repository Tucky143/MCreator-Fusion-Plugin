package ${package}.item;

public interface IDyeableItem {
    String TAG_COLOR = "color";

    int getDefaultColor();

    default boolean hasColor(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        return tag.contains(TAG_COLOR, Tag.TAG_INT);
    }

    default int getColor(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        return hasColor(stack) ? tag.getInt(TAG_COLOR) : getDefaultColor();
    }

    default void setColor(ItemStack stack, int color) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.putInt(TAG_COLOR, color);
    }

    default void removeColor(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.remove(TAG_COLOR);
    }

    static boolean isDyeable(ItemStack stack) {
        return stack.getItem() instanceof IDyeableItem;
    }

    static ItemStack dye(ItemStack stack, List<DyeItem> dyes) {
        ItemStack result = ItemStack.EMPTY;
        int[] combinedColors = new int[3];
        int maxColor = 0;
        int colorCount = 0;
        IDyeableItem coloredItem = null;

        if (IDyeableItem.isDyeable(stack)) {
            coloredItem = (IDyeableItem) stack.getItem();
            result = stack.copy();
            result.setCount(1);

            if (coloredItem.hasColor(stack)) {
                int color = coloredItem.getColor(result);

                float r = (float) (color >> 16 & 255) / 255f;
                float g = (float) (color >> 8 & 255) / 255f;
                float b = (float) (color & 255) / 255f;

                maxColor = (int) ((float) maxColor + Math.max(r, Math.max(g, b)) * 255f);

                combinedColors[0] = (int) ((float) combinedColors[0] + r * 255f);
                combinedColors[1] = (int) ((float) combinedColors[1] + g * 255f);
                combinedColors[2] = (int) ((float) combinedColors[2] + b * 255f);

                colorCount++;
            }

            for (DyeItem dye: dyes) {
                float[] colorComponents = dye.getDyeColor().getTextureDiffuseColors();

                int r = (int) (colorComponents[0] * 255f);
                int g = (int) (colorComponents[1] * 255f);
                int b = (int) (colorComponents[2] * 255f);

                maxColor += Math.max(r, Math.max(g, b));

                combinedColors[0] += r;
                combinedColors[1] += g;
                combinedColors[2] += b;

                colorCount++;
            }
        }

        if (coloredItem == null)
            return ItemStack.EMPTY;
        else {
            int r = combinedColors[0] / colorCount;
            int g = combinedColors[1] / colorCount;
            int b = combinedColors[2] / colorCount;

            float avgColor = (float) maxColor / (float) colorCount;
            float maxValue = (float) Math.max(r, Math.max(g, b));

            r = (int) ((float) r * avgColor / maxValue);
            g = (int) ((float) g * avgColor / maxValue);
            b = (int) ((float) b * avgColor / maxValue);

            int finalColor = (r << 8) + g;
            finalColor = (finalColor << 8) + b;

            coloredItem.setColor(result, finalColor);

            return result;
        }
    }
}