{
	${field$VAR?replace("local:", "")?replace("global:", "${JavaModName}Variables.")}.write(${input$text});
	<#if input$newLine == "true">
	    ${field$VAR?replace("local:", "")?replace("global:", "${JavaModName}Variables.")}.newLine();
	</#if>
}