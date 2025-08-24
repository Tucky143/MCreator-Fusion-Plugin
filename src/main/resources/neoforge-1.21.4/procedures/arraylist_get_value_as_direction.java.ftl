(
    new Object() {
        public Direction get(ArrayList<?> list, int index) {
            if (list.get(index) instanceof Direction direc) {
                return direc;
            }
            return Direction.UP;
        }
    }.get(${input$var}, ${opt.toInt(input$index)})
)