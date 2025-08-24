(
    ${input$var}.stream().map(String::valueOf).collect(Collectors.joining(${input$separator}))
)