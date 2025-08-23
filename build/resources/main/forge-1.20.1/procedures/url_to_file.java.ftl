try {
     org.apache.commons.io.FileUtils.copyURLToFile(new URL(${input$url}), ${field$VAR?replace("local:", "")?replace("global:", "${JavaModName}Variables.")}, ${opt.toInt(input$connectionTimeout)}, ${opt.toInt(input$readTimeout)});
} catch (IOException e) {
    e.printStackTrace();
}