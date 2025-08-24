package ${package}.configuration;

public class ${name}Configuration {
	public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	public static final ForgeConfigSpec SPEC;

	${variables}

	static {

		${code}
		
		SPEC = BUILDER.build();
 	}
  
}