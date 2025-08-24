/*@BlockState*/

(
    new Object() {
        public BlockState get(ArrayList<?> list, int index) {
            if (list.get(index) instanceof BlockState state) {
                return state;
            }
            return Blocks.AIR.defaultBlockState();
        }
    }.get(${input$var}, ${opt.toInt(input$index)})
)