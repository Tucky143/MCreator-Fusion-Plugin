/*@ItemStack*/

(
    new Object() {
        public ItemStack get(ArrayList<?> list, int index) {
            if (list.get(index) instanceof ItemStack stack) {
                return stack;
            }
            return ItemStack.EMPTY;
        }
    }.get(${input$var}, ${opt.toInt(input$index)})
)