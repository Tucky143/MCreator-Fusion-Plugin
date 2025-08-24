(
    new Object() {
        public com.google.gson.JsonArray get(ArrayList<?> list, int index) {
            if (list.get(index) instanceof com.google.gson.JsonArray jsAr) {
                return jsAr;
            }
            return new com.google.gson.JsonArray();
        }
    }.get(${input$var}, ${opt.toInt(input$index)})
)