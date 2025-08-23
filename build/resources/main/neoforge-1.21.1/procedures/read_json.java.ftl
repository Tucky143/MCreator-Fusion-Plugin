{
	try {
		BufferedReader bufferedReader = new BufferedReader(new FileReader(${field$VAR?replace("local:", "")?replace("global:", "${JavaModName}Variables.")}));
		StringBuilder jsonstringbuilder = new StringBuilder();
		String line;
		while((line = bufferedReader.readLine()) != null) {
			jsonstringbuilder.append(line);
		}
		bufferedReader.close();

		${field$JOBJVAR?replace("local:", "")?replace("global:", "${JavaModName}Variables.")} = new com.google.gson.Gson().fromJson(jsonstringbuilder.toString(), com.google.gson.JsonObject.class);
		${statement$values}
  
	} catch (IOException e) {
		e.printStackTrace();
	}
}