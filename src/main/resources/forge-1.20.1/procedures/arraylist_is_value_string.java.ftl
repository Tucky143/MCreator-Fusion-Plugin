(
    new Object() {
        public boolean is(ArrayList<?> list, inx index) {
            if(list.get(index) instanceof String a) {
                return true;
            }
            return false;
        }
    }.is(${input$list}, ${opt.toInt(input$index)})
)