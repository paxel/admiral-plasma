package admiral_plasma.pojo_general.parser;

import admiral_plasma.pojo_general.definition.CaptnStruct;
import admiral_plasma.pojo_general.definition.Schema;
import java.util.Collections;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

class SchemaParser {

    private final Queue<CaptnStruct> stack;

    private SchemaParser(String schema) {
        this.stack = Collections.asLifoQueue(new LinkedBlockingDeque<CaptnStruct>());
    }

    public static Schema from(String schema) {
        return new SchemaParser(schema).parse();
    }

    private Schema parse() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
