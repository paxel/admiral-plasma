
package admiral_plasma.definition.api;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import admiral_plasma.definition.builder.Parents;

public class CaptnProtoEnum implements IndentedPrinter {

	private final List<String> entries;

	private final String name;

	private final Parents parents;

	public CaptnProtoEnum(String name, List<String> entries, Parents parents) {
		this.entries = entries;
		this.name = name;
		this.parents = parents;
	}

	public List<String> getEntries() {
		return entries;
	}

	public String getId() {
		return parents + name;
	}

	public String getName() {
		return name;
	}

	public String getUniqeName() {
		return (parents + name).replace('.', '_');
	}

	@Override
	public void print(Writer out, int indent) throws IOException {
		out.append('\n');
		printIndent(out, indent);
		out.append("enum " + name + " {\n");
		int i = 0;
		for (String entry : entries) {
			printIndent(out, indent + INDENT_SIZE);
			out.append(entry + " @" + (i++) + ";\n");
		}
		printIndent(out, indent);
		out.append("}\n");
	}

}
