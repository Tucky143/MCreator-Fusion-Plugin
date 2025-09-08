if (${input$item}.is(ItemTags.DYEABLE))
    ${input$item}.set(DataComponents.DYED_COLOR, new DyedItemColor(${opt.toInt(input$color)}, false));