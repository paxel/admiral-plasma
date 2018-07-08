package admiral_plasma.pojo_general.definition;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class Schema {

    private final List<CaptnStruct> structs;
    private final List<CaptnEnum> enums;

    Schema(List<CaptnStruct> structs, List<CaptnEnum> enums) {
        this.structs = structs == null ? new ArrayList<>() : structs;
        this.enums = enums == null ? new ArrayList<>() : enums;
    }

    void print(Writer out) throws IOException {
        for (CaptnStruct value : structs) {
            value.print(out, 0);
        }
        for (CaptnEnum value : enums) {
            value.print(out, 0);
        }
    }

    public Stream<CaptnStruct> stream() {
        return structs.stream();
    }

    public void forEach(Consumer<? super CaptnStruct> action) {
        structs.forEach(action);
    }

    public int size() {
        return structs.size();
    }

}
