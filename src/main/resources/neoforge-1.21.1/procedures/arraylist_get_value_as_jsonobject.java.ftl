(
    new Object() {
        public com.google.gson.JsonObject get(ArrayList<?> list, int index) {
            if (list.get(index) instanceof com.google.gson.JsonObject jsObj) {
                return jsObj;
            }
            return new com.google.gson.JsonObject();
        }
    }.get(${input$var}, ${opt.toInt(input$index)})
)