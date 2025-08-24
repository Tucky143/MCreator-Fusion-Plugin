(
    new Object() {
        public Entity get(ArrayList<?> list, int index) {
            if (list.get(index) instanceof Entity ent) {
                return ent;
            }
            return null;
        }
    }.get(${input$var}, ${opt.toInt(input$index)})
)