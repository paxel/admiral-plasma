
package admiral_plasma.pojo_general.definition.api;

import java.io.IOException;
import java.io.Writer;


public class CaptnProtoValue implements IndentedPrinter {

    private final String name;
    private final String type;
    private final int id;
    private final String defaultValue;

    public CaptnProtoValue(String name, String type, int id, String defaultValue) {
        this.name = name;
        this.type = type;
        this.id = id;
        this.defaultValue = defaultValue;
    }

    @Override
    public void print(Writer out, int indent) throws IOException {
        printIndent(out, indent);
        out.append(name);
        out.append(" @" + id + " :");
        out.append(type);
        if (defaultValue != null) {
            out.append(" = " + defaultValue);
        }
        out.append(";\n");
    }

}
