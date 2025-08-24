(
    new Object() {
        public double get(ArrayList<?> list, int index) {
            if(list.get(index) instanceof Double num) {
                return num;
            }
            return 0;
        }
    }.get(${input$var}, ${opt.toInt(input$index)})
)