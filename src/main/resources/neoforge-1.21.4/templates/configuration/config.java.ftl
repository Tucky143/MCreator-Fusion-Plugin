package ${package}.configuration;

public class ${name}Configuration {
	public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
	public static final ModConfigSpec SPEC;

	${variables}

	static {

	    ${code}
		
		SPEC = BUILDER.build();
 	}
  
}