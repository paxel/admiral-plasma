package admiral_plasma.definition.api;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import admiral_plasma.definition.builder.Parents;

public class ProtoContainer implements IndentedPrinter {

    private final String name;
    private final ContainerType type;

    private final List<ProtoValue> values;
    private final List<ProtoContainer> structs;
    private final List<ProtoEnum> enums;
    private final Parents parents;

    public ProtoContainer(String name, ContainerType type, List<ProtoValue> values,
            List<ProtoContainer> structs, List<ProtoEnum> enums, Parents parents) {
        this.name = name;
        this.type = type;
        this.values = values;
        this.structs = structs;
        this.enums = enums;
        this.parents = parents;
    }

    public List<ProtoEnum> getEnums() {
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

    public List<ProtoContainer> getStructs() {
        return structs;
    }

    public ContainerType getType() {
        return type;
    }

    public List<ProtoValue> getValues() {
        return values;
    }

    @Override
    public void print(Writer out, int indent) throws IOException {
        out.append('\n');
        printIndent(out, indent);
        out.append(type.getSyntax() + " " + name + " {\n");
        for (ProtoValue value : values) {
            value.print(out, indent + INDENT_SIZE);
        }
        for (ProtoContainer struct : structs) {
            struct.print(out, indent + INDENT_SIZE);
        }
        for (ProtoEnum cEnum : enums) {
            cEnum.print(out, indent + INDENT_SIZE);
        }
        printIndent(out, indent);
        out.append("}\n");
    }

}
