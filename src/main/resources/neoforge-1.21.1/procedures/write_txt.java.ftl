try {
  FileWriter ${field$VAR?replace("local:", "")?replace("global:", "${JavaModName}Variables.")}writer = new FileWriter(${field$VAR?replace("local:", "")?replace("global:", "${JavaModName}Variables.")}, ${input$appendMode});
  BufferedWriter ${field$VAR?replace("local:", "")?replace("global:", "${JavaModName}Variables.")}bw = new BufferedWriter(${field$VAR?replace("local:", "")?replace("global:", "${JavaModName}Variables.")}writer);
  
  ${statement$text}

  ${field$VAR?replace("local:", "")?replace("global:", "${JavaModName}Variables.")}bw.close();
  ${field$VAR?replace("local:", "")?replace("global:", "${JavaModName}Variables.")}writer.close();
} catch (IOException exception) {
  exception.printStackTrace();
}