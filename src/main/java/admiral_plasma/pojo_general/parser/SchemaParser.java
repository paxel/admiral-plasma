package admiral_plasma.pojo_general.parser;

import admiral_plasma.pojo_general.definition.api.CaptnProtoContainer;
import admiral_plasma.pojo_general.definition.api.CaptnProtoSchema;
import java.util.Collections;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

class SchemaParser {

    private final Queue<CaptnProtoContainer> stack;

    private SchemaParser(String schema) {
        this.stack = Collections.asLifoQueue(new LinkedBlockingDeque<CaptnProtoContainer>());
    }

    public static CaptnProtoSchema from(String schema) {
        return new SchemaParser(schema).parse();
    }

    private CaptnProtoSchema parse() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
