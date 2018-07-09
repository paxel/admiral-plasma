package admiral_plasma.definition.api;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import admiral_plasma.definition.builder.Parents;

public class CaptnProtoContainer implements IndentedPrinter {

	private final String name;
	private final ContainerType type;

	private final List<CaptnProtoValue> values;
	private final List<CaptnProtoContainer> structs;
	private final List<CaptnProtoEnum> enums;
	private final Parents parents;

	public CaptnProtoContainer(String name, ContainerType type, List<CaptnProtoValue> values,
			List<CaptnProtoContainer> structs, List<CaptnProtoEnum> enums, Parents parents) {
		this.name = name;
		this.type = type;
		this.values = values;
		this.structs = structs;
		this.enums = enums;
		this.parents = parents;
	}

	public List<CaptnProtoEnum> getEnums() {
		return enums;
	}

	public String getId() {
		return parents + name;
	}

	public String getName() {
		return name;
	}

	public Parents getParents() {
		return parents;
	}

	public List<CaptnProtoContainer> getStructs() {
		return structs;
	}

	public ContainerType getType() {
		return type;
	}

	public List<CaptnProtoValue> getValues() {
		return values;
	}

	@Override
	public void print(Writer out, int indent) throws IOException {
		out.append('\n');
		printIndent(out, indent);
		out.append(type.getSyntax() + " " + name + " {\n");
		for (CaptnProtoValue value : values) {
			value.print(out, indent + INDENT_SIZE);
		}
		for (CaptnProtoContainer struct : structs) {
			struct.print(out, indent + INDENT_SIZE);
		}
		for (CaptnProtoEnum cEnum : enums) {
			cEnum.print(out, indent + INDENT_SIZE);
		}
		printIndent(out, indent);
		out.append("}\n");
	}

}
