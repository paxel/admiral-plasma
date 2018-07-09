package admiral_plasma.pojo_general.definition.api;

import java.io.IOException;
import java.io.Writer;
import java.util.List;


public class CaptnProtoContainer implements IndentedPrinter {

    private final String name;
    private final ContainerType type;

    private final List<CaptnProtoValue> values;
    private final List<CaptnProtoContainer> structs;
    private final List<CaptnProtoEnum> enums;

    public CaptnProtoContainer(String name, ContainerType type, List<CaptnProtoValue> values, List<CaptnProtoContainer> structs, List<CaptnProtoEnum> enums) {
        this.name = name;
        this.type = type;
        this.values = values;
        this.structs = structs;
        this.enums = enums;
    }

    @Override
    public void print(Writer out, int indent) throws IOException {
        out.append('\n');
        printIndent(out, indent);
        out.append(type.getSyntax()+ " " + name + " {\n");
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
