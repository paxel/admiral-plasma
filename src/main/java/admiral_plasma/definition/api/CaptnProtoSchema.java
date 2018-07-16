package admiral_plasma.definition.api;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class CaptnProtoSchema {

    private final List<CaptnProtoContainer> structs;
    private final List<CaptnProtoEnum> enums;

    public CaptnProtoSchema(List<CaptnProtoContainer> structs, List<CaptnProtoEnum> enums) {
        this.structs = structs == null ? new ArrayList<>() : structs;
        this.enums = enums == null ? new ArrayList<>() : enums;
    }

    public List<CaptnProtoEnum> getEnums() {
        return enums;
    }

    public List<CaptnProtoContainer> getStructs() {
        return structs;
    }

    public void print(Writer out) throws IOException {
        for (CaptnProtoContainer value : structs) {
            value.print(out, 0);
        }
        for (CaptnProtoEnum value : enums) {
            value.print(out, 0);
        }
    }

}
