try {
	BufferedReader ${field$VAR?replace("local:", "")?replace("global:", "${JavaModName}Variables.")}Reader = new BufferedReader(new FileReader(${field$VAR?replace("local:", "")?replace("global:", "${JavaModName}Variables.")}));
	String stringiterator = "";
	while((stringiterator = ${field$VAR?replace("local:", "")?replace("global:", "${JavaModName}Variables.")}Reader.readLine()) != null) {
		${statement$foreach}
	}
	${field$VAR?replace("local:", "")?replace("global:", "${JavaModName}Variables.")}Reader.close();
} catch (IOException e) {
    e.printStackTrace();
}