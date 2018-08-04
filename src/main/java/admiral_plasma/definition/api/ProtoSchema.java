package admiral_plasma.definition.api;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ProtoSchema {

    private final List<ProtoContainer> structs;
    private final List<ProtoEnum> enums;

    public ProtoSchema(List<ProtoContainer> structs, List<ProtoEnum> enums) {
        this.structs = structs == null ? new ArrayList<>() : structs;
        this.enums = enums == null ? new ArrayList<>() : enums;
    }

    public List<ProtoEnum> getEnums() {
        return enums;
    }

    public List<ProtoContainer> getStructs() {
        return structs;
    }

    public void print(Writer out) throws IOException {
        for (ProtoContainer value : structs) {
            value.print(out, 0);
        }
        for (ProtoEnum value : enums) {
            value.print(out, 0);
        }
    }

}
