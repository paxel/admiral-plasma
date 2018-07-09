
package admiral_plasma.pojo_general.definition.api;

import java.io.IOException;
import java.io.Writer;


public interface IndentedPrinter {

    static final int INDENT_SIZE = 3;
    static final char INDENT_CHAR = ' ';

    default void printIndent(Writer out, int indent) throws IOException {
        for (int i = 0; i < indent; i++) {
            out.append(INDENT_CHAR);
        }
    }

    void print(Writer out, int indent) throws IOException;
}