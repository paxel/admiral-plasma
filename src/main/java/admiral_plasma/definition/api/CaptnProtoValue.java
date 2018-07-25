package admiral_plasma.definition.api;

import java.io.IOException;
import java.io.Writer;

public class CaptnProtoValue implements IndentedPrinter {

    private final String name;
    private final String type;
    private final int id;
    private final String defaultValue;
    private final boolean constant;

    public CaptnProtoValue(String name, String type, int id, String defaultValue, boolean constant) {
        this.name = name;
        this.type = type;
        this.id = id;
        this.defaultValue = defaultValue;
        this.constant = constant;
    }

    public String getName() {
        return name;
    }

    public boolean isConstant() {
        return constant;
    }

    public String getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    @Override
    public void print(Writer out, int indent) throws IOException {
        printIndent(out, indent);
        if (constant) {
            out.append("const ");
        }
        out.append(name);
        out.append(" @" + id + " :");
        out.append(type);
        if (defaultValue != null) {
            out.append(" = " + defaultValue);
        }
        out.append(";\n");
    }

}
