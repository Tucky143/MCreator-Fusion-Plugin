(
    new Object() {
        public File get(ArrayList<?> list, int index) {
            if (list.get(index) instanceof File filian) {
                return filian;
            }
            return new File("");
        }
    }.get(${input$var}, ${opt.toInt(input$index)})
)