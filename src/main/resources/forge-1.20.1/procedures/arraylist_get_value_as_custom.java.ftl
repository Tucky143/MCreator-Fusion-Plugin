(
    new Object() {
        public ${field$object} get(ArrayList<?> list, int index) {
            if (list.get(index) instanceof ${field$object} obj) {
                return obj;
            }
            return ${field$default};
        }
    }.get(${input$var}, ${opt.toInt(input$index)})
)