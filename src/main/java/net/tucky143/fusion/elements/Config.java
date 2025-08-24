package net.tucky143.fusion.elements;

import net.mcreator.blockly.data.BlocklyLoader;
import net.mcreator.blockly.data.BlocklyXML;
import net.mcreator.blockly.java.BlocklyToJava;
import net.mcreator.element.NamespacedGeneratableElement;
import net.mcreator.element.parts.MItemBlock;
import net.mcreator.generator.blockly.BlocklyBlockCodeGenerator;
import net.mcreator.generator.blockly.OutputBlockCodeGenerator;
import net.mcreator.generator.blockly.ProceduralBlockCodeGenerator;
import net.mcreator.generator.template.IAdditionalTemplateDataProvider;
import net.mcreator.workspace.elements.ModElement;
import net.tucky143.fusion.Launcher;

import java.util.List;
import java.util.Locale;

public class Config extends NamespacedGeneratableElement {
	public List<Config.Pool> pools;
	public String file;
	public int configType;
	@BlocklyXML("configs")
	public String config;
	public boolean defining = true;
	public Config(ModElement element) {
		super(element);
	}

	public IAdditionalTemplateDataProvider getAdditionalTemplateData() {
		return (additionalData) -> {
			BlocklyBlockCodeGenerator blocklyBlockCodeGenerator = new BlocklyBlockCodeGenerator(
					BlocklyLoader.INSTANCE.getBlockLoader(Launcher.CONFIG_EDITOR).getDefinedBlocks(),
					getModElement().getGenerator().getGeneratorStats().getBlocklyBlocks(Launcher.CONFIG_EDITOR),
					this.getModElement().getGenerator()
							.getTemplateGeneratorFromName(Launcher.CONFIG_EDITOR.registryName()),
					additionalData).setTemplateExtension(
					this.getModElement().getGeneratorConfiguration().getGeneratorFlavor().getBaseLanguage().name()
							.toLowerCase(Locale.ENGLISH));

			defining = true;
			BlocklyToJava variables = new BlocklyToJava(this.getModElement().getWorkspace(), this.getModElement(), Launcher.CONFIG_EDITOR,
					this.config, this.getModElement().getGenerator().getTemplateGeneratorFromName(Launcher.CONFIG_EDITOR.registryName()),
					new ProceduralBlockCodeGenerator(blocklyBlockCodeGenerator), new OutputBlockCodeGenerator(blocklyBlockCodeGenerator));
			additionalData.put("variables", variables.getGeneratedCode());
			defining = false;
			BlocklyToJava code = new BlocklyToJava(this.getModElement().getWorkspace(), this.getModElement(), Launcher.CONFIG_EDITOR,
					this.config, this.getModElement().getGenerator().getTemplateGeneratorFromName(Launcher.CONFIG_EDITOR.registryName()),
					new ProceduralBlockCodeGenerator(blocklyBlockCodeGenerator), new OutputBlockCodeGenerator(blocklyBlockCodeGenerator));
			additionalData.put("code", code.getGeneratedCode());
		};
	}

	public String listsToXML() {
		StringBuilder XML = new StringBuilder();

		XML.append("<xml xmlns=\"https://developers.google.com/blockly/xml\">");
		XML.append("<block type=\"config_start\" deletable=\"false\" x=\"40\" y=\"40\"><next>");

		int ctr = 0;

		for (Config.Pool pool : pools) {
			ctr++;

			// Begin category block
			XML.append("<block type=\"config_category\">");
			XML.append("<field name=\"name\">").append(pool.category).append("</field>");
			XML.append("<statement name=\"write\">");

			// Generate config_variable chain
			for (int i = 0; i < pool.entries.size(); i++) {
				Config.Pool.Entry entry = pool.entries.get(i);

				// Start a new config_variable block
				XML.append("<block type=\"config_variable\">");
				XML.append("<field name=\"name\">").append(entry.varname).append("</field>");
				XML.append("<field name=\"comment\">").append(entry.comment).append("</field>");
				XML.append("<value name=\"value\">");

				// Determine the inner block based on silkTouchMode
				switch (entry.silkTouchMode) {
					case 0: // Logic variable
						XML.append("<block type=\"config_logic_variable\">");
						XML.append("<field name=\"name\">").append(entry.vardisplay).append("</field>");
						XML.append("<field name=\"value\">").append(entry.logicField != 0 ? "false" : "true").append("</field>");
						XML.append("</block>");
						break;
					case 1: // Number variable
						XML.append("<block type=\"config_number_variable\">");
						XML.append("<field name=\"name\">").append(entry.vardisplay).append("</field>");
						XML.append("<field name=\"value\">").append(entry.numberField).append("</field>");
						XML.append("</block>");
						break;
					case 2: // Text variable
						XML.append("<block type=\"config_text_variable\">");
						XML.append("<field name=\"name\">").append(entry.vardisplay).append("</field>");
						XML.append("<field name=\"value\">").append(entry.textDefault).append("</field>");
						XML.append("</block>");
						break;
					case 3: // Registry name variable for item
					case 4: // Registry name variable for block
						XML.append("<block type=\"config_registryname_variable\">");
						XML.append("<field name=\"name\">").append(entry.vardisplay).append("</field>");
						XML.append("<field name=\"value\">")
								.append(entry.item != null ? entry.item.getUnmappedValue() : entry.block.getUnmappedValue())
								.append("</field>");
						XML.append("</block>");
						break;
					case 5: // Text list variable
						XML.append("<block type=\"config_textlist_variable\">");
						XML.append("<mutation xmlns=\"http://www.w3.org/1999/xhtml\" inputs=\"")
								.append(entry.stringlist.size())
								.append("\"></mutation>");
						XML.append("<field name=\"name\">").append(entry.vardisplay).append("</field>");
						for (int j = 0; j < entry.stringlist.size(); j++) {
							XML.append("<field name=\"entry").append(j).append("\">")
									.append(entry.stringlist.get(j))
									.append("</field>");
						}
						XML.append("</block>");
						break;
					default:
						break;
				}

				XML.append("</value>"); // Close value
				if (i < pool.entries.size() - 1) {
					XML.append("<next>");
				}
			}

			// Close remaining <next> tags for config_variables
			for (int i = 0; i < pool.entries.size(); i++) {
				XML.append("</block>");
				if (i < pool.entries.size() - 1)
					XML.append("</next>");
			}

			XML.append("</statement>"); // Close category write statement
			if (ctr < pools.size())
				XML.append("<next>");
		}

		// Close remaining <next> tags for config_categories
		for (int i = 0; i < pools.size(); i++) {
			XML.append("</block>");
			if (i < pools.size() - 1)
				XML.append("</next>");
		}

		// Close the root chain and finalize
		XML.append("</next></block></xml>");
		System.out.println(XML.toString());
		return XML.toString();
	}

	public static class Pool {
		public String category;
		public List<Config.Pool.Entry> entries;

		public Pool() {
		}

		public static class Entry {
			public MItemBlock item; // used if silkTouchMode is 3 or 4, use the config_registryname_variable block, get the item's string using item.getUnmappedValue()
			public int silkTouchMode;
			public MItemBlock block; // used if silkTouchMode is 3 or 4, use the config_registryname_variable block, get the item's string using item.getUnmappedValue()
			public int logicField; // used if silkTouchMode is 0, use the config_logic_variable, if logicField is 0 then it's true else it's false
			public double numberField; // used if silkTouchMode is 1, use the config_number_variable
			public String textDefault; // used if silkTouchMode is 2, use the config_text_variable
			public String comment; // comment field of config_variable
			public String vardisplay; // variable display name used in config_*type*_variable
			public String varname; // name field used in config_variable
			public Boolean enablecomment; // irrelevant, dont use
			public List<String> stringlist; // used if silkTouchMode is 5 for config_textlist_variable
			public Entry() {
			}
		}
	}
}