/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admiral_plasma.pojo_general.definition;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

/**
 *
 * @author axel
 */
public class CaptnContainer implements IndentedPrinter {

    private final String name;

    private final List<CaptnValue> values;
    private final List<CaptnContainer> structs;
    private final List<CaptnEnum> enums;

    public CaptnContainer(String name, List<CaptnValue> values, List<CaptnContainer> structs, List<CaptnEnum> enums) {
        this.name = name;
        this.values = values;
        this.structs = structs;
        this.enums = enums;
    }

    @Override
    public void print(Writer out, int indent) throws IOException {
        out.append('\n');
        printIndent(out, indent);
        out.append("struct " + name + " {\n");
        for (CaptnValue value : values) {
            value.print(out, indent + INDENT_SIZE);
        }
        for (CaptnContainer struct : structs) {
            struct.print(out, indent + INDENT_SIZE);
        }
        for (CaptnEnum cEnum : enums) {
            cEnum.print(out, indent + INDENT_SIZE);
        }
        printIndent(out, indent);
        out.append("}\n");
    }

}
