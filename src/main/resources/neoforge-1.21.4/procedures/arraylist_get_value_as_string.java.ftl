(
    new Object() {
        public String get(ArrayList<?> list, int index) {
            if (list.get(index) instanceof String text) {
                return text;
            }
            return "";
        }
    }.get(${input$var}, ${opt.toInt(input$index)})
)