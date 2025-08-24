(
    new Object() {
        public ArrayList<Object> convert(String text, String separator) {
            return new ArrayList<>(Arrays.asList(text.split(separator)));
        }
    }.convert(${input$text}, ${input$separator})
)