/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admiral_plasma.pojo_general.definition;

import java.io.IOException;
import java.io.Writer;

/**
 *
 * @author axel
 */
class CaptnValue implements IndentedPrinter {

    private final String name;
    private final String type;
    private final int id;
    private final String defaultValue;

    public CaptnValue(String name, String type, int id, String defaultValue) {
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
