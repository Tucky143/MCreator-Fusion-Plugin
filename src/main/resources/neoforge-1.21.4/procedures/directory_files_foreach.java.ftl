{
	File dir_files_ = new File(${input$directory});
	if (dir_files_.isDirectory())
		for (File file : dir_files_.listFiles()) {
			String stringiterator = file.getPath();
			${statement$foreach}
		}
}