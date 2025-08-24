(
    new Object() {
        public ArrayList<?> get(ArrayList<?> list, int index) {
            if (list.get(index) instanceof ArrayList<Object> array) {
                return array;
            }
            return new ArrayList<>();
        }
    }.get(${input$var}, ${opt.toInt(input$index)})
)