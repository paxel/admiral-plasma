package admiral_plasma.pojo_general.definition.api;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class CaptnProtoSchema {

    private final List<CaptnProtoContainer> structs;
    private final List<CaptnProtoEnum> enums;

    public CaptnProtoSchema(List<CaptnProtoContainer> structs, List<CaptnProtoEnum> enums) {
        this.structs = structs == null ? new ArrayList<>() : structs;
        this.enums = enums == null ? new ArrayList<>() : enums;
    }

    public void print(Writer out) throws IOException {
        for (CaptnProtoContainer value : structs) {
            value.print(out, 0);
        }
        for (CaptnProtoEnum value : enums) {
            value.print(out, 0);
        }
    }

    public Stream<CaptnProtoContainer> stream() {
        return structs.stream();
    }

    public void forEach(Consumer<? super CaptnProtoContainer> action) {
        structs.forEach(action);
    }

    public int size() {
        return structs.size();
    }

}
