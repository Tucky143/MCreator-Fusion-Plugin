try {
    ${field$VAR?replace("local:", "")?replace("global:", "${JavaModName}Variables.")}.getParentFile().mkdirs();
    ${field$VAR?replace("local:", "")?replace("global:", "${JavaModName}Variables.")}.createNewFile();
} catch (IOException exception) {
    exception.printStackTrace();
}