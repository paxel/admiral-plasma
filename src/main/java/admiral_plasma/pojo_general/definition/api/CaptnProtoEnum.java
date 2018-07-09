
package admiral_plasma.pojo_general.definition.api;

import java.io.IOException;
import java.io.Writer;
import java.util.List;


public class CaptnProtoEnum implements IndentedPrinter {

    private final List<String> entries;

    public CaptnProtoEnum(String name, List<String> entries) {
        this.entries = entries;
        this.name = name;
    }
    private final String name;

    @Override
    public void print(Writer out, int indent) throws IOException {
        out.append('\n');
        printIndent(out, indent);
        out.append("enum " + name + " {\n");
        int i = 0;
        for (String entry : entries) {
            printIndent(out, indent + INDENT_SIZE);
            out.append(entry + " @" + (i++)+";\n");
        }
        printIndent(out, indent);
        out.append("}\n");
    }

}
