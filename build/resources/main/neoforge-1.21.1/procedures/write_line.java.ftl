{
	${field$VAR?replace("local:", "")?replace("global:", "${JavaModName}Variables.")}bw.write(${input$text});
	<#if input$newLine == "true">
	    ${field$VAR?replace("local:", "")?replace("global:", "${JavaModName}Variables.")}bw.newLine();
	</#if>
}